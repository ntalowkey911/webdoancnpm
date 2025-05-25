package dao;

import entity.Order;
import entity.OrderDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class OrderDao {

    public OrderDao(Connection conn) {
    }

    public OrderDao() {

    }

    public int createOrder(int user, double totalAmount, String shippingMethod,
                           Date deliveryDate, String deliveryTime, String paymentMethod, String orderNote,
                           String receiverName, String receiverPhone, String shippingAddress) {

        String sql = """
                    INSERT INTO orders (UserId, OrderDate, TotalAmount, ShippingMethod, DeliveryDate, DeliveryTime, PaymentMethod, OrderNote, ReceiverName, ReceiverPhone, ShippingAddress, created_at, updated_at)
                    VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())
                    """;
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, user);
            stmt.setDouble(2, totalAmount);
            stmt.setString(3, shippingMethod);
            stmt.setDate(4, deliveryDate);
            stmt.setString(5, deliveryTime);
            stmt.setString(6, paymentMethod);
            stmt.setString(7, orderNote);
            stmt.setString(8, receiverName);
            stmt.setString(9, receiverPhone);
            stmt.setString(10, shippingAddress);

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Order> getOrdersByUserIdWithDetails(int userId) {
        String sql = """
            
                    SELECT o.Id AS orderId, o.UserID, o.OrderDate, o.TotalAmount, o.ShippingMethod,
                   DATE_FORMAT(o.DeliveryDate, '%d/%m/%Y') AS deliveryDate, o.DeliveryTime,
                   o.PaymentMethod, o.OrderNote, o.ReceiverName, o.ReceiverPhone,
                   o.ShippingAddress, o.OrderStatus,
                   p.ProductName, p.ImageURL, od.ProductID, od.Quantity, od.UnitPrice
            FROM Orders o
            JOIN OrderDetails od ON o.Id = od.OrderID
            JOIN Products p ON od.ProductID = p.Id
            WHERE o.UserID = ?
            ORDER BY o.OrderDate DESC
            """;
        Map<Integer, Order> orderMap = new HashMap<>();
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                Order order = orderMap.computeIfAbsent(orderId, id -> {
                    Order newOrder = new Order();
                    try {
                        newOrder.setId(orderId);
                        newOrder.setUserId(rs.getInt("UserID"));
                        newOrder.setOrderDate(rs.getTimestamp("OrderDate"));
                        newOrder.setTotalAmount(rs.getDouble("TotalAmount"));
                        newOrder.setShippingMethod(rs.getString("ShippingMethod"));
                        newOrder.setDeliveryDate(rs.getString("deliveryDate"));
                        newOrder.setDeliveryTime(rs.getString("DeliveryTime"));
                        newOrder.setPaymentMethod(rs.getString("PaymentMethod"));
                        newOrder.setOrderNote(rs.getString("OrderNote"));
                        newOrder.setReceiverName(rs.getString("ReceiverName"));
                        newOrder.setReceiverPhone(rs.getString("ReceiverPhone"));
                        newOrder.setShippingAddress(rs.getString("ShippingAddress"));
                        newOrder.setOrderStatus(rs.getString("OrderStatus"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return newOrder;
                });

                OrderDetails detail = new OrderDetails();
                detail.setProductID(rs.getInt("ProductID"));
                detail.setProductName(rs.getString("ProductName"));
                detail.setImg(rs.getString("ImageURL"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setUnitPrice(rs.getDouble("UnitPrice"));
                order.addProduct(detail);
            }
            return new ArrayList<>(orderMap.values());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean cancelOrder(int orderId) {
        String selectDetails = "SELECT ProductID, Quantity FROM OrderDetails WHERE OrderID = ?";
        String updateWarehouse = "UPDATE Warehouse SET Quantity = Quantity + ? WHERE product_id = ?";
        String insertWarehouse = "INSERT INTO Warehouse (product_id, quantity) VALUES (?, ?)";
        String updateOrder = "UPDATE Orders SET OrderStatus = 'Đã hủy đơn hàng' WHERE Id = ?";

        try (Connection conn = MySQLConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement detailStmt = conn.prepareStatement(selectDetails)) {
                detailStmt.setInt(1, orderId);
                ResultSet rs = detailStmt.executeQuery();

                while (rs.next()) {
                    int productId = rs.getInt("ProductID");
                    int quantity = rs.getInt("Quantity");

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateWarehouse)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setInt(2, productId);
                        int affected = updateStmt.executeUpdate();

                        if (affected == 0) {
                            try (PreparedStatement insertStmt = conn.prepareStatement(insertWarehouse)) {
                                insertStmt.setInt(1, productId);
                                insertStmt.setInt(2, quantity);
                                insertStmt.executeUpdate();
                            }
                        }
                    }
                }
            }

            try (PreparedStatement updateOrderStmt = conn.prepareStatement(updateOrder)) {
                updateOrderStmt.setInt(1, orderId);
                int rows = updateOrderStmt.executeUpdate();
                if (rows == 0) throw new RuntimeException("Không tìm thấy đơn hàng để hủy.");
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE Orders SET OrderStatus = ?, updated_at = NOW() WHERE Id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Order getOrderById(int orderId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id = ?";  // Sửa 'Id' thành 'id' (chữ thường)
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    // Ánh xạ chính xác tên cột từ database
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("UserID"));  // Chú ý: 'UserD' thay vì 'UserID'
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setTotalAmount(rs.getDouble("TotalAmount"));  // Sử dụng BigDecimal cho decimal
                    order.setShippingMethod(rs.getString("ShippingMethod"));
                    order.setDeliveryDate(rs.getString("DeliveryDate"));  // Sử dụng getDate cho kiểu date
                    order.setDeliveryTime(rs.getString("DeliveryTime"));
                    order.setPaymentMethod(rs.getString("PaymentMethod"));
                    order.setOrderNote(rs.getString("OrderNote"));
                    order.setReceiverName(rs.getString("ReceiverName"));  // 'ReceiveName' thay vì 'recipient_name'
                    order.setReceiverPhone(rs.getString("ReceiverPhone")); // 'ReceivePhone' thay vì 'recipient_phone'
                    order.setShippingAddress(rs.getString("ShippingAddress")); // 'ShippingAddress' thay vì 'recipient_address'
                    order.setOrderStatus(rs.getString("OrderStatus"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));

                    return order;
                }
            }
        }
        return null;
    }
}


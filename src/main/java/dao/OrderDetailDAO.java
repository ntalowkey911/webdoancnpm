package dao;

import entity.CartItem;
import entity.Order;
import entity.OrderDetails;
import entity.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private Connection conn;

    public OrderDetailDAO(Connection conn) {
        this.conn = conn;
    }

    public OrderDetailDAO() {

    }


    // Trong OrderDetailDAO.java
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) throws SQLException {
        List<OrderDetails> orderDetails = new ArrayList<>();
        String sql = "SELECT od.*, p.name as product_name FROM orderdetails od " +
                "JOIN product p ON od.ProductId = p.P_ID " +
                "WHERE od.OrderID = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetails detail = new OrderDetails();
                    detail.setId(rs.getInt("Id"));
                    detail.setOrderId(rs.getInt("OrderID"));
                    detail.setProductID(rs.getInt("ProductId"));
                    detail.setQuantity(rs.getInt("Quantity"));
                    detail.setUnitPrice(rs.getDouble("UnitPrice"));

                    // Tạo đối tượng Product đơn giản chỉ chứa tên
                    Products product = new Products();
                    product.setName(rs.getString("product_name"));
                    detail.setProduct(product);

                    orderDetails.add(detail);
                }
            }
        }
        return orderDetails;
    }
    // Phiên bản 1: Nhận thông tin chi tiết từng tham số
    public void addOrderDetail(int orderId, int productId, int quantity, double price) {
        String sql = "INSERT INTO orderdetails (OrderId, ProductId, Quantity, UnitPrice, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
package dao;

import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.MySQLConnection.getConnection;

public class OrderDao {
    public List<Orders> getAllOrders() {
        List<Orders> orderList = new ArrayList<>();
        String query = "SELECT * FROM orders"; // Thêm dấu nháy ngược nếu tên bảng là Order

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Orders order = new Orders(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getBigDecimal("order_amount"),
                        rs.getTimestamp("order_date")
                );
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Bạn có thể in ra thêm e.getMessage() để rõ ràng hơn
        }

        return orderList;
    }

    public int getLastOrderId() {
        String query = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        int orderId = 0;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    private void createOrderItemsFromCart(int orderId, int userId) {
        String query = "INSERT INTO order_item (order_id, product_id, quantity, unit_price) " +
                "SELECT ?, ci.product_id, ci.quantity, p.price " +
                "FROM cart_item ci " +
                "JOIN product p ON ci.product_id = p.product_id " +
                "WHERE ci.cart_id = (SELECT cart_id FROM cart WHERE user_id = ?);";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setInt(2, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearCart(int userId) {
        String query = "DELETE ci FROM cart_item ci " +
                "JOIN cart c ON ci.cart_id = c.cart_id " +
                "WHERE c.user_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrderFromCart(int userId, double orderAmount) {
        String query = "INSERT INTO orders (user_id, order_amount, order_date) VALUES (?, ?, NOW());";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setDouble(2, orderAmount);
            statement.executeUpdate();

            int orderId = getLastOrderId();

            // Gọi phương thức chuyển sản phẩm từ cart sang order_item
            createOrderItemsFromCart(orderId, userId);

            // Xóa giỏ hàng sau khi thanh toán
            clearCart(userId);

            System.out.println("Thanh toán thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

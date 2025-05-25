package dao;

import entity.Payments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDAO {

    public PaymentDAO(Connection conn) {
    }

    public void addPayment(int orderId, String paymentStatus) {
        String sql = "INSERT INTO Payments (OrderID, PaymentStatus, created_at, updated_at) VALUES (?, ?, NOW(), NOW())";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setString(2, paymentStatus);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updatePaymentStatus(int orderId, String status) {
        String sql = "UPDATE Payments SET PaymentStatus = ?, updated_at = NOW() WHERE OrderID = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin thanh toán theo OrderID
    public Payments getPaymentByOrderId(int orderId) {
        String sql = "SELECT * FROM Payments WHERE OrderID = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Payments payment = new Payments(
                        rs.getInt("Id"),
                        rs.getInt("OrderID"),
                        rs.getTimestamp("PaymentDate"),
                        rs.getString("PaymentStatus"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                return payment;
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin thanh toán theo OrderID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Test main method
    public static void main(String[] args) {

    }
}
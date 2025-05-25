package dao;

import entity.Shipping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShippingDAO {
    public ShippingDAO(Connection conn){

    }
    public void addShipping(int orderId, double shippingCost) {
        String sql = "INSERT INTO shipping (OrderId, ShippingCost) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setDouble(2, shippingCost);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Shipping getShippingByOrderId(int orderId) {
        String sql = "SELECT * FROM shipping WHERE OrderId = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Shipping shipping = new Shipping();
                shipping.setId(rs.getInt("ID"));
                shipping.setOrderId(rs.getInt("OrderId"));
                shipping.setShippingCost(rs.getDouble("ShippingCost"));
                return shipping;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteShippingByOrderId(int orderId) {
        String sql = "DELETE FROM shipping WHERE OrderId = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
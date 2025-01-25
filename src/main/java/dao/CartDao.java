package dao;

import entity.Cart;
import entity.CartItem;
import entity.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    public boolean AddToCart(int userId, int productId) {
        String query = "INSERT INTO cart (user_id, product_id, created_at) VALUES (?, ?, NOW())";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);  // Gán user_id
            statement.setInt(2, productId);  // Gán product_id

            int rowsAffected = statement.executeUpdate();  // Thực thi câu lệnh
            return rowsAffected > 0;  // Trả về true nếu thêm thành công

        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi nếu xảy ra ngoại lệ
        }
        return false;  // Trả về false nếu thêm thất bại
    }



    public boolean removeFromCart(int userId, int productId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);  // Gán user_id
            statement.setInt(2, productId);  // Gán product_id

            int rowsAffected = statement.executeUpdate();  // Thực thi lệnh DELETE
            return rowsAffected > 0;  // Trả về true nếu xóa thành công

        } catch (SQLException e) {
            System.err.println("Error while removing from cart: " + e.getMessage());
            e.printStackTrace();  // In lỗi để debug
            return false;  // Trả về false nếu xảy ra lỗi
        }
    }


    public List<CartItem> getCartByUserId(int userId) {
        List<CartItem> cart = new ArrayList<>();
        String query = "SELECT product_id, COUNT(*) AS quantity FROM cart WHERE user_id = ? GROUP BY product_id";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);  // Gán user_id
            ResultSet rs = stmt.executeQuery();

            // Lấy dữ liệu từ bảng cart
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                // Lấy thông tin sản phẩm từ product_id
                Products product = dao.getProductById(productId);
                if (product != null) {
                    CartItem cartItem = new CartItem(product, quantity);  // Tạo đối tượng CartItem
                    cart.add(cartItem);  // Thêm CartItem vào danh sách
                } else {
                    System.out.println("No product found for product_id: " + productId);
                }
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }

        return cart;  // Trả về danh sách CartItem
    }


}

package dao;

import entity.Cart;
import entity.CartItem;
import entity.Products;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    public boolean AddToCart(int userId, int productId, int quantity) {
        try (Connection connection = MySQLConnection.getConnection()) {
            // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
            String checkQuery = "SELECT cart_id FROM cart WHERE user_id = ? AND product_id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, productId);

                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật quantity
                        int cartId = resultSet.getInt("cart_id");

                        String updateQuery = "UPDATE cart_item SET quantity = quantity + ? WHERE cart_id = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, quantity);
                            updateStmt.setInt(2, cartId);
                            int rowsUpdated = updateStmt.executeUpdate();
                            return rowsUpdated > 0;
                        }
                    } else {
                        // Nếu sản phẩm chưa tồn tại, thêm mới vào bảng `cart` và `cart_item`
                        String insertCartQuery = "INSERT INTO cart (user_id, product_id, created_at) VALUES (?, ?, NOW())";
                        try (PreparedStatement insertCartStmt = connection.prepareStatement(insertCartQuery, Statement.RETURN_GENERATED_KEYS)) {
                            insertCartStmt.setInt(1, userId);
                            insertCartStmt.setInt(2, productId);
                            insertCartStmt.executeUpdate();

                            // Lấy cartid vừa được thêm
                            try (ResultSet generatedKeys = insertCartStmt.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    int cartId = generatedKeys.getInt(1);

                                    // Thêm chi tiết sản phẩm vào `cart_item`
                                    String insertCartItemQuery = "INSERT INTO cart_item (cart_id, product, quantity) VALUES (?, ?, ?)";
                                    try (PreparedStatement insertCartItemStmt = connection.prepareStatement(insertCartItemQuery)) {
                                        insertCartItemStmt.setInt(1, cartId);
                                        insertCartItemStmt.setInt(2, productId);
                                        insertCartItemStmt.setInt(3, quantity);
                                        int rowsInserted = insertCartItemStmt.executeUpdate();
                                        return rowsInserted > 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi nếu xảy ra ngoại lệ
        }
        return false;  // Trả về false nếu thêm hoặc cập nhật thất bại
    }




    public boolean removeFromCart(int userId, int productId) {
        try (Connection connection = MySQLConnection.getConnection()) {
            // Kiểm tra xem cart_id tồn tại cho userId và productId
            String checkQuery = "SELECT cart_id FROM cart WHERE user_id = ? AND product_id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, productId);

                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        int cartId = resultSet.getInt("cart_id");

                        // Xóa sản phẩm khỏi bảng cart_item trước
                        String deleteCartItemQuery = "DELETE FROM cart_item WHERE cart_id = ? AND product = ?";
                        try (PreparedStatement deleteCartItemStmt = connection.prepareStatement(deleteCartItemQuery)) {
                            deleteCartItemStmt.setInt(1, cartId);
                            deleteCartItemStmt.setInt(2, productId);
                            deleteCartItemStmt.executeUpdate();
                        }

                        // Kiểm tra xem còn sản phẩm nào trong cart_item không
                        String countQuery = "SELECT COUNT(*) AS count FROM cart_item WHERE cart_id = ?";
                        try (PreparedStatement countStmt = connection.prepareStatement(countQuery)) {
                            countStmt.setInt(1, cartId);

                            try (ResultSet countResult = countStmt.executeQuery()) {
                                if (countResult.next() && countResult.getInt("count") == 0) {
                                    // Nếu không còn sản phẩm nào, xóa cart
                                    String deleteCartQuery = "DELETE FROM cart WHERE cart_id = ?";
                                    try (PreparedStatement deleteCartStmt = connection.prepareStatement(deleteCartQuery)) {
                                        deleteCartStmt.setInt(1, cartId);
                                        deleteCartStmt.executeUpdate();
                                    }
                                }
                            }
                        }
                        return true; // Xóa thành công
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while removing from cart: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Trả về false nếu xóa thất bại
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
    public int getQuantity(int cartId, int productId) throws SQLException {
        String sql = "SELECT quantity FROM cart_item WHERE cart_id = ? AND product = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                } else {
                    return -1;  // Nếu không tìm thấy sản phẩm trong giỏ hàng
                }
            }
        }
    }

    public void updateQuantity(int cartId, int productId, int newQuantity) throws SQLException {
        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND product = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        }
    }
}



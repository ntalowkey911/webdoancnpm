package dao;

import entity.CartItem;
import entity.Products;
import entity.Review;
import entity.Users;
import dao.CartDao;

import java.sql.SQLException;
import java.util.List;

    public class test {
        public static void main(String[] args) throws SQLException {
            CartDao cartDao = new CartDao();


            // Test 1: Thêm sản phẩm vào giỏ hàng
            {
                int userId = 1;  // Thay thế bằng user_id hợp lệ
                int productId = 5;  // Thay thế bằng product_id hợp lệ
                int quantity = 2;
                boolean result = cartDao.AddToCart(userId, productId, quantity);
                System.out.println("Thêm sản phẩm vào giỏ hàng: " + result);
            }

            // Test 2: Xóa sản phẩm khỏi giỏ hàng
            {
                int userId = 1;  // Thay thế bằng user_id hợp lệ
                int productId = 1;  // Thay thế bằng product_id hợp lệ
                boolean result = cartDao.removeFromCart(userId, productId);
                System.out.println("Xóa sản phẩm khỏi giỏ hàng: " + result);
            }

            // Test 3: Lấy tất cả sản phẩm trong giỏ hàng của người dùng
            {
                int userId = 1;  // Thay thế bằng user_id hợp lệ
                List<CartItem> cartItems = cartDao.getCartByUserId(userId);
                System.out.println("Số sản phẩm trong giỏ hàng của người dùng: " + cartItems.size());
                for (CartItem cartItem : cartItems) {
                    System.out.println("CartItem: Sản phẩm ID = " + cartItem.getProductId() + ", Số lượng = " + cartItem.getQuantity());
                }
            }

            // Test 4: Lấy số lượng của một sản phẩm trong giỏ hàng
            try {
                int userId = 1;  // Thay thế bằng user_id hợp lệ
                int cartId = cartDao.getCartIdByUserId(userId);  // Lấy cart_id cho user
                int productId = 1;  // Thay thế bằng product_id hợp lệ
                int quantity = cartDao.getQuantity(cartId, productId);
                System.out.println("Số lượng sản phẩm có trong giỏ hàng: " + quantity);
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy số lượng sản phẩm: " + e.getMessage());
                e.printStackTrace();
            }

            // Test 5: Cập nhật số lượng sản phẩm trong giỏ hàng
            try {
                int userId = 1;  // Thay thế bằng user_id hợp lệ
                int cartId = cartDao.getCartIdByUserId(userId);  // Lấy cart_id cho user
                int productId = 1;  // Thay thế bằng product_id hợp lệ
                int newQuantity = 5;  // Số lượng mới

                // Cập nhật số lượng sản phẩm trong giỏ hàng
                cartDao.updateQuantity(cartId, productId, newQuantity);

                // Kiểm tra lại số lượng sau khi cập nhật
                int updatedQuantity = cartDao.getQuantity(cartId, productId);
                System.out.println("Số lượng cập nhật của sản phẩm trong giỏ hàng: " + updatedQuantity);
            } catch (SQLException e) {
                System.err.println("Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
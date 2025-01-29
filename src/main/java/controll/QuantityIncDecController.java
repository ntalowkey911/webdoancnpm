package controll;

import dao.CartDao;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/quantity-inc-dec")
public class QuantityIncDecController extends HttpServlet {
    private final CartDao cartDao = new CartDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("id"));

        // Kiểm tra session và lấy thông tin user
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        int userId = ((Users) session.getAttribute("user")).getId();

        try {
            // Lấy danh sách CartItem của người dùng
            List<CartItem> cartItems = cartDao.getCartByUserId(userId);
            int cartId = -1;

            // Tìm cart_id của sản phẩm trong giỏ hàng của người dùng
            for (CartItem item : cartItems) {
                if (item.getProduct().getId() == productId) {
                    cartId = item.getCartId();
                    break;  // Nếu tìm thấy, dừng vòng lặp
                }
            }

            if (cartId == -1) {
                response.sendRedirect("/cart?error=Sản phẩm không tồn tại trong giỏ hàng");
                return;
            }

            // Lấy số lượng hiện tại của sản phẩm trong giỏ
            int currentQuantity = cartDao.getQuantity(cartId, productId);

            // Tăng hoặc giảm số lượng sản phẩm trong giỏ
            if ("inc".equals(action)) {
                cartDao.updateQuantity(cartId, productId, currentQuantity + 1);
            } else if ("dec".equals(action) && currentQuantity > 1) {
                cartDao.updateQuantity(cartId, productId, currentQuantity - 1);
            } else if ("dec".equals(action) && currentQuantity == 1) {
                cartDao.removeFromCart(userId, productId);
            }

            // Điều hướng lại trang giỏ hàng
            response.sendRedirect("/cart");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thông tin sản phẩm không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật giỏ hàng.");
        }
    }
}

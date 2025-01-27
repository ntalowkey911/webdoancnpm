package controll;

import entity.CartItem;
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
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if (action == null || (!"inc".equals(action) && !"dec".equals(action))) {
            response.sendRedirect("/cart");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("/cart");
            return;
        }

        HttpSession session = request.getSession();
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

        // Kiểm tra nếu giỏ hàng chưa tồn tại
        if (cartItems == null) {
            response.sendRedirect("/cart");
            return;
        }

        // Xử lý cập nhật số lượng
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                if ("inc".equals(action)) {
                    item.setQuantity(item.getQuantity() + 1); // Tăng số lượng
                } else if ("dec".equals(action) && item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1); // Giảm số lượng (nhưng không dưới 1)
                }
                break;
            }
        }

        // Cập nhật session với giỏ hàng mới
        session.setAttribute("cartItems", cartItems);

        // Tính tổng giá tạm tính
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getQuantity() * item.getProduct().getPrice();
        }

        session.setAttribute("totalPrice", totalPrice);

        // Quay lại trang giỏ hàng
        response.sendRedirect("/cart");
    }
}
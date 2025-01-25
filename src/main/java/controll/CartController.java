package controll;

import dao.CartDao;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartDao cartDao;

    public CartController() {
        super();
        cartDao = new CartDao();  // Khởi tạo CartDao
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        // Lấy thông tin người dùng từ session
        Users user = (Users) session.getAttribute("user");
        int userId = user.getId();  // Lấy ID người dùng từ session

        // Lấy giỏ hàng của người dùng từ CartDao
        List<CartItem> cart = cartDao.getCartByUserId(userId);

        // Cập nhật tổng số lượng và tổng giá trị giỏ hàng
        updateCartSession(session, cart);

        // Hiển thị giỏ hàng trong JSP
        request.setAttribute("cart", cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/cart.jsp");
        dispatcher.forward(request, response);
    }

    private void updateCartSession(HttpSession session, List<CartItem> cart) {
        int totalItems = cart.stream().mapToInt(CartItem::getQuantity).sum();
        double totalPrice = cart.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();

        session.setAttribute("totalItems", totalItems);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cart", cart);  // Cập nhật lại giỏ hàng trong session
    }
}

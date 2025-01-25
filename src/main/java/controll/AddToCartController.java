package controll;

import dao.dao;
import entity.Cart;
import dao.CartDao;
import entity.CartItem;
import entity.Products;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartDao cartDao;
    private dao dao;

    public AddToCartController() {
        super();
        cartDao = new CartDao();  // Khởi tạo CartDao
        dao = new dao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        // Lấy thông tin từ request
        String productIdParam = request.getParameter("id");
        String action = request.getParameter("action");

        // Kiểm tra xem tham số "id" có tồn tại không
        if (productIdParam == null || productIdParam.isEmpty()) {
            redirectToErrorPage(response);
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdParam);
        } catch (NumberFormatException e) {
            redirectToErrorPage(response);
            return;
        }

        Users user = (Users) session.getAttribute("user");
        int userId = user.getId();  // Lấy ID người dùng từ session

        // Kiểm tra hành động, xử lý xóa hoặc thêm sản phẩm vào giỏ hàng
        if ("remove".equals(action)) {
            cartDao.removeFromCart(userId, productId);  // Gọi phương thức xóa
        } else if ("add-cart".equals(action)) {
            cartDao.AddToCart(userId, productId);  // Gọi phương thức thêm vào giỏ
        } else if ("buy-now".equals(action)) {
            cartDao.AddToCart(userId, productId);  // Thêm vào giỏ và chuyển đến trang giỏ hàng
            response.sendRedirect("/cart");
            return;
        } else {
            redirectToErrorPage(response);
            return;
        }

        // Cập nhật giỏ hàng trong session
        List<CartItem> cart = cartDao.getCartByUserId(userId);
        updateCartSession(session, cart);

        // Xử lý gợi ý sản phẩm (nếu cần)
        handleRandomProducts(request);

        // Chuyển hướng hoặc hiển thị thông báo
        handleRedirectOrMessage(action, request, response, productId);
    }

    private void redirectToErrorPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("error.jsp");
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

    private void handleRandomProducts(HttpServletRequest request) {
        List<Products> randomProducts = dao.getRandomProducts();
        request.setAttribute("randomProductList", randomProducts);
    }

    private void handleRedirectOrMessage(String action, HttpServletRequest request, HttpServletResponse response, int productId) throws ServletException, IOException {
        if ("add-cart".equals(action)) {
            // Trả về trang chi tiết sản phẩm sau khi thêm vào giỏ hàng
            request.setAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
            request.setAttribute("productId", productId);  // Đảm bảo thông tin sản phẩm vẫn có sẵn
            RequestDispatcher dispatcher = request.getRequestDispatcher("/detail");
            dispatcher.forward(request, response);  // Chuyển tiếp đến trang chi tiết sản phẩm
        } else if ("remove".equals(action)) {
            // Quay lại trang giỏ hàng sau khi xóa sản phẩm
            response.sendRedirect("/cart");
        } else {
            response.sendRedirect("/cart");
        }
    }
}




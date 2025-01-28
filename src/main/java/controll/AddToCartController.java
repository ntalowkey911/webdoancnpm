package controll;

import dao.dao;
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
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartDao cartDao;
    private final dao dao;

    public AddToCartController() {
        super();
        cartDao = new CartDao();
        dao = new dao();
    }

    @Override
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
        String quantityParam = request.getParameter("quantity");

        // Kiểm tra tham số id hợp lệ
        if (productIdParam == null || productIdParam.isEmpty()) {
            redirectToErrorPage(response);
            return;
        }

        int productId;
        int quantity = 1; // Số lượng mặc định là 1

        try {
            productId = Integer.parseInt(productIdParam);
            if (quantityParam != null && !quantityParam.isEmpty()) {
                quantity = Integer.parseInt(quantityParam);
                if (quantity <= 0) {
                    throw new NumberFormatException("Quantity must be positive.");
                }
            }
        } catch (NumberFormatException e) {
            redirectToErrorPage(response);
            return;
        }

        Users user = (Users) session.getAttribute("user");
        int userId = user.getId(); // Lấy ID người dùng từ session

        // Kiểm tra tồn kho trước khi thêm sản phẩm
        Products product = dao.getProductById(productId);
        if (product == null || product.getStock() < quantity) {
            request.setAttribute("error", "Sản phẩm không tồn tại hoặc số lượng vượt quá tồn kho.");
            redirectToErrorPage(response);
            return;
        }

        // Xử lý hành động
        switch (action) {
            case "remove":
                cartDao.removeFromCart(userId, productId);
                break;

            case "add-cart":
                cartDao.AddToCart(userId, productId, quantity);
                request.setAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
                request.setAttribute("productId", productId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/detail");
                dispatcher.forward(request, response);
                return;

            case "buy-now":
                cartDao.AddToCart(userId, productId, quantity);
                response.sendRedirect("/cart");
                return;

            default:
                redirectToErrorPage(response);
                return;
        }

        // Cập nhật giỏ hàng trong session
        List<CartItem> cart = cartDao.getCartByUserId(userId);
        updateCartSession(session, cart);

        // Xử lý gợi ý sản phẩm
        handleRandomProducts(request);

        // Chuyển hướng đến trang giỏ hàng
        response.sendRedirect("/cart");
    }

    private void redirectToErrorPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("error.jsp");
    }

    private void updateCartSession(HttpSession session, List<CartItem> cart) {
        int totalItems = cart.stream().mapToInt(CartItem::getQuantity).sum();
        double totalPrice = cart.stream()
                .mapToDouble(cartItem -> dao.getProductPriceById(cartItem.getProductId()) * cartItem.getQuantity())
                .sum();

        session.setAttribute("totalItems", totalItems);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cart", cart); // Cập nhật lại giỏ hàng trong session
    }

    private void handleRandomProducts(HttpServletRequest request) {
        List<Products> randomProducts = dao.getRandomProducts();
        request.setAttribute("randomProductList", randomProducts);
    }
}
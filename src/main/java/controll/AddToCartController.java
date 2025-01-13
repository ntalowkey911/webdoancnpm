package controll;

import dao.MySQLConnection;
import dao.cart.CartItem;
import entity.Products;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-to-cart") // Đường dẫn để gọi servlet
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy ID sản phẩm từ tham số truy vấn
        String productId = request.getParameter("id");

        // Kiểm tra nếu ID sản phẩm không hợp lệ
        if (productId == null || productId.isEmpty()) {
            response.sendRedirect("error.jsp"); // Chuyển hướng đến trang lỗi nếu không có ID
            return;
        }

        // Lấy sản phẩm từ cơ sở dữ liệu
        Products product = getProductById(Integer.parseInt(productId));

        // Nếu sản phẩm không tồn tại, chuyển hướng đến trang lỗi
        if (product == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        // Lấy giỏ hàng từ session (nếu có)
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // Nếu giỏ hàng chưa được tạo, khởi tạo giỏ hàng mới
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        boolean productFound = false;
        for (CartItem cartItem : cart) {
            if (cartItem.getProduct().getId() == product.getId()) {
                // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng lên
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                productFound = true;
                break;
            }
        }

        // Nếu sản phẩm chưa có trong giỏ, thêm vào giỏ hàng
        if (!productFound) {
            cart.add(new CartItem(product, 1)); // Mặc định thêm 1 sản phẩm vào giỏ
        }

        // Chuyển hướng người dùng đến trang giỏ hàng
        response.sendRedirect("/cart");
    }

    // Lấy thông tin sản phẩm từ cơ sở dữ liệu theo ID
    private Products getProductById(int productId) {
        Products product = null;
        String query = "SELECT * FROM Products WHERE id = ?";  // Câu lệnh SQL để lấy sản phẩm theo ID

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho PreparedStatement
            statement.setInt(1, productId);

            // Thực thi câu lệnh và lấy kết quả
            try (ResultSet rs = statement.executeQuery()) {
                // Nếu có sản phẩm với ID tương ứng
                if (rs.next()) {
                    product = new Products(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("price"),
                            rs.getInt("stock"),
                            rs.getString("image"),
                            rs.getTimestamp("created_at"), // Lấy giá trị DATETIME
                            rs.getInt("category_Id")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
        return product;  // Trả về sản phẩm nếu tìm thấy, nếu không trả về null
    }


    // Xử lý yêu cầu POST (dùng chung với doGet)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

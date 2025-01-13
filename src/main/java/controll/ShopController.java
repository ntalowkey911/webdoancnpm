package controll;

import dao.dao;
import entity.Categories;
import entity.Products;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/shop") // Đường dẫn để gọi servlet
public class ShopController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private dao productDAO;  // Đổi tên dao thành productDAO để rõ ràng hơn

    @Override
    public void init() throws ServletException {
        productDAO = new dao();  // Khởi tạo đối tượng ProductDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy danh sách sản phẩm từ DAO
        List<Products> p = productDAO.getAllProducts();
        if (p != null && !p.isEmpty()) {
            request.setAttribute("productList", p);
        } else {
            request.setAttribute("message", "Không có sản phẩm nào.");
        }

        List<Categories> c = productDAO.getAllCategories();
        if (c != null && !c.isEmpty()) {
            request.setAttribute("categoriesList", c);
        } else {
            request.setAttribute("message", "Không có sản phẩm nào.");
        }
        Products l=productDAO.getLatestProduct();
        request.setAttribute("lastp", l);

// loc sp
        String categoryId = request.getParameter("categoryId");
        if (categoryId != null) {
            List<Products> filteredProducts = productDAO.getProductsByCategory(Integer.parseInt(categoryId));
            request.setAttribute("productList", filteredProducts);
        } else {
            List<Products> allProducts = productDAO.getAllProducts();
            request.setAttribute("productList", allProducts);
        }


        // Chuyển tiếp yêu cầu tới trang JSP để hiển thị
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/shop.jsp");
        dispatcher.forward(request, response);

    }
}
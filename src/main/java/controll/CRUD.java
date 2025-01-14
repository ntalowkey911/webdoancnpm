package controll;
import entity.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.dao ;
import entity.Products;
import entity.Categories;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet({
        "/AddProduct", "/AddCategory",
        "/UpdateProduct", "/DeleteProduct",
        "/DeleteCategory", "/UpdateStock",
        "/GetTotalCartPrice"
})
public class CRUD extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private dao daoInstance;

    @Override
    public void init() throws ServletException {
        daoInstance = new dao(); // Khởi tạo DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/AddProduct":
                addProduct(request, response);
                break;

            case "/AddCategory":
                addCategory(request, response);
                break;

            case "/UpdateProduct":
                updateProduct(request, response);
                break;

            case "/UpdateStock":
                updateStock(request, response);
                break;

            case "/GetTotalCartPrice":
                getTotalCartPrice(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action không được hỗ trợ");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/DeleteProduct":
                deleteProduct(request, response);
                break;

            case "/DeleteCategory":
                deleteCategory(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action không được hỗ trợ");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String image = request.getParameter("image");
        int category_id = Integer.parseInt(request.getParameter("category_id"));

        daoInstance.addProduct(name, description, price, stock, image, category_id);
        response.getWriter().write("Thêm sản phẩm thành công!");
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        if (name != null && !name.trim().isEmpty()) {
            daoInstance.addCategories(name);
            response.getWriter().write("Thêm danh mục thành công!");
        } else {
            response.getWriter().write("Tên danh mục không được để trống!");
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String image = request.getParameter("image");
        int category_id = Integer.parseInt(request.getParameter("category_id"));

        daoInstance.updateProduct(id, name, description, price, stock, image, category_id);
        response.getWriter().write("Cập nhật sản phẩm thành công!");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        daoInstance.deleteProduct(id);
        response.getWriter().write("Xóa sản phẩm thành công!");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        daoInstance.deleteCategory(id);
        response.getWriter().write("Xóa danh mục thành công!");
    }

    private void updateStock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        int result = daoInstance.updateStock(productId, stock);

        if (result > 0) {
            response.getWriter().write("Cập nhật số lượng thành công!");
        } else {
            response.getWriter().write("Cập nhật thất bại!");
        }
    }

    private void getTotalCartPrice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Chuyển đổi cartList từ request (ví dụ: JSON) thành `ArrayList<CartItem>`
        // Tạm thời giả định cartList được truyền đúng format
        ArrayList<CartItem> cartList = (ArrayList<CartItem>) request.getAttribute("cartList");

        double total = daoInstance.getTotalCartPrice(cartList);
        response.getWriter().write("Tổng giá trị giỏ hàng: " + total);
    }
}

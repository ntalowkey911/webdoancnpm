package controll;
import entity.Cart;
import entity.Categories;
import entity.Products;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.dao ;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.MySQLConnection.getConnection;

@WebServlet({
        "/AddProduct", "/AddCategory",
        "/UpdateProduct", "/DeleteProduct",
        "/DeleteCategory", "/UpdateStock",
        "/GetTotalCartPrice"
})
public class CRUD extends HttpServlet {
    private static final long serialVersionUID = 1L;
    dao daoInstance = new dao();

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

        daoInstance.addProduct(name, price, stock,description, category_id, image);
        response.getWriter().write("Thêm sản phẩm thành công!");
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("categoryName");
        daoInstance.addCategories(name);
        response.getWriter().write("Thêm sản phẩm thành công!");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy các tham số từ request
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        String image = request.getParameter("image");
        String categoryId = request.getParameter("category_id");

        // Chuyển đổi các tham số sang kiểu dữ liệu phù hợp
        int productId = Integer.parseInt(id); // id phải là số nguyên
        double productPrice = Double.parseDouble(price); // price phải là số thực
        int productStock = Integer.parseInt(stock); // stock phải là số nguyên
        int productCategoryId = Integer.parseInt(categoryId); // categoryId phải là số nguyên

        // Cập nhật sản phẩm vào cơ sở dữ liệu
        daoInstance.updateProduct(productId, name, (int) productPrice, productStock, description, productCategoryId, image);

        // Kiểm tra và phản hồi cho người dùng
        response.getWriter().write("Cập nhật sản phẩm thành công!");
        response.sendRedirect("doanweb/html/ad.jsp");
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
        ArrayList<Cart> cartList = (ArrayList<Cart>) request.getAttribute("cartList");

//        double total = daoInstance.getTotalCartPrice(cartList);
//        response.getWriter().write("Tổng giá trị giỏ hàng: " + total);
    }
    public List<Products> getAllProducts() {
        List<Products> l = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("p_id"), // Sử dụng 'p_id' thay vì 'id'
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("img")
                );
                l.add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
    public List<Categories> getAllCategories() {
        List<Categories> l = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Categories product = new Categories(
                        rs.getInt("c_id"),
                        rs.getString("name")
                );
                l.add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
    public void addProduct(String name, double price, int stock, String description, int category_id, String image) {
        String sql = "INSERT INTO Product (name, price, stock, description, category_id, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setString(4, description);
            stmt.setInt(5, category_id);
            stmt.setString(6, image);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCategories(String name) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateProduct(int id, String name, int price, int stock, String description, int category_id, String image) {
        String sql = "UPDATE Product SET name=?, description=?, price=?, stock=?, image=?, category_id=? WHERE p_id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setString(4, description);
            stmt.setInt(5, category_id);
            stmt.setString(6, image);
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

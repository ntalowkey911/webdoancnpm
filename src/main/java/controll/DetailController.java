package controll;
import entity.Products;
import dao.dao;  // Đảm bảo import lớp DAO đúng
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/detail")
public class DetailController extends HttpServlet {
    private dao  d ;  // Khai báo đối tượng ProductDAO

    @Override
    public void init() throws ServletException {
        d  = new dao();  // Khởi tạo dao
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("id");
        Products product = d.getProductById(Integer.parseInt(productId));  // Gọi phương thức của dao
        request.setAttribute("product", product);
        request.getRequestDispatcher("doanweb/html/detail.jsp").forward(request, response);
    }
}

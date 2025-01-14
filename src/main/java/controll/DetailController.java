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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/detail")
public class DetailController extends HttpServlet {
    private dao d;

    @Override
    public void init() throws ServletException {
        d = new dao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách sản phẩm từ dao
        String productId = request.getParameter("id");
        Products product = d.getProductById(Integer.parseInt(productId));
        request.setAttribute("product", product);

        // Lấy toàn bộ sản phẩm và trộn ngẫu nhiên
        List<Products> productList = d.getAllProducts();
        Collections.shuffle(productList);

        // Lấy 4 sản phẩm đầu tiên sau khi trộn
        List<Products> randomProductList = productList.stream().limit(4).collect(Collectors.toList());

        // Đưa danh sách sản phẩm ngẫu nhiên vào request
        request.setAttribute("randomProductList", randomProductList);

        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("doanweb/html/detail.jsp").forward(request, response);
    }

}

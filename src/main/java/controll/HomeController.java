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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách sản phẩm từ DAO
        dao productDao = new dao();
        List<Products> products = productDao.getAllProducts();

        // Đặt danh sách sản phẩm vào request để gửi tới JSP
        request.setAttribute("products", products);

        // Chuyển tiếp yêu cầu đến trang index.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/index.jsp");
        dispatcher.forward(request, response);
    }
}

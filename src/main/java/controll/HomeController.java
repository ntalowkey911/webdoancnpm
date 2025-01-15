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
        List<Products> randomProducts = dao.getRandomProducts();
        System.out.println("Số lượng sản phẩm trả về: " + randomProducts.size());  // Kiểm tra số lượng sản phẩm
        request.setAttribute("randomProductList", randomProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/index.jsp");
        dispatcher.forward(request, response);
    }
}

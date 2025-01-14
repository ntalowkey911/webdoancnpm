package controll;

import dao.dao;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        dao d = new dao();
        Users user = d.login(username, password);

        if (user == null) {
            // Nếu đăng nhập thất bại
            request.setAttribute("mess", "Tài khoản hoặc mật khẩu sai");
            System.out.println("Đăng nhập thất bại.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/Login.jsp");
            dispatcher.forward(request, response);
        } else {
            // Nếu đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Chuyển hướng người dùng đến trang Home
            request.setAttribute("loginSuccess", "Bạn đã đăng nhập thành công!");
            System.out.println("Đăng nhập thành công.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
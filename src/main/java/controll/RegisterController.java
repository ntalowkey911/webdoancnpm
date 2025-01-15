package controll;

import dao.dao;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register") // Đường dẫn để gọi servlet
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form đăng ký
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Kiểm tra nếu mật khẩu và xác nhận mật khẩu không khớp
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/Register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        dao d = new dao();
        Users existingUser = d.checkExist(username);

        // Kiểm tra nếu tài khoản đã tồn tại
        if (existingUser != null) {
            request.setAttribute("error", "Username already exists.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/Register.jsp");
            dispatcher.forward(request, response);
        } else {
            try {
                // Đăng ký người dùng mới
                d.Register(username, email, password, phone, address);
                request.setAttribute("success", "Registration successful!");
                response.sendRedirect("doanweb/html/index.jsp");  // Chuyển hướng đến trang chính sau khi đăng ký
            } catch (Exception e) {
                // Xử lý ngoại lệ nếu có lỗi trong quá trình đăng ký
                request.setAttribute("error", "An error occurred during registration. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/Register.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng về trang đăng ký nếu truy cập GET
        RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/Register.jsp");
        dispatcher.forward(request, response);
    }
}

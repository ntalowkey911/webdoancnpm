package controll;

import dao.UserDao;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao dao;

    @Override
    public void init() {
        dao = new UserDao(); // Khởi tạo đối tượng DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy đối tượng người dùng đã đăng nhập từ session
        HttpSession session = request.getSession();
        Users loggedInUser = (Users) session.getAttribute("user"); // Lấy đối tượng Users từ session

        if (loggedInUser == null) {
            response.sendRedirect("/login");  // Nếu không có người dùng trong session, chuyển hướng đến trang đăng nhập
            return;
        }

        // Lấy username từ đối tượng người dùng trong session
        String username = loggedInUser.getUsername();  // Truy xuất username của người dùng đã đăng nhập

        // Lấy thông tin người dùng từ dao
        Users user = dao.getUserByUsername(username); // Lấy 1 người dùng duy nhất từ DAO

        if (user == null) {
            response.sendRedirect("/home"); // Nếu không tìm thấy người dùng trong cơ sở dữ liệu
            return;
        }

        // Truyền đối tượng người dùng vào request
        request.setAttribute("user", user);

        // Chuyển hướng đến trang profile.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/profile.jsp");
        dispatcher.forward(request, response);
    }

}

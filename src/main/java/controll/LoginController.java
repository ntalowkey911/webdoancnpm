package controll;

import dao.dao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private dao daoInstance;

    // Đường dẫn tới trang đăng nhập và trang chủ
    private static final String LOGIN_PAGE = "/doanweb/html/Login.jsp";
    private static final String HOME_PAGE = "/home";

    // Thông báo lỗi
    private static final String LOGIN_ERROR_MESSAGE = "Tài khoản hoặc mật khẩu không đúng";
    private static final String PROCESS_ERROR_MESSAGE = "Đã xảy ra lỗi trong quá trình xử lý đăng nhập";

    @Override
    public void init() {
        daoInstance = new dao(); // Khởi tạo đối tượng DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng tới trang đăng nhập
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }


        }
    }
}

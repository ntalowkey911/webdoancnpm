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

        Users loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            redirectToLogin(response);
            return;
        }

        Users user = dao.getUserByUsername(loggedInUser.getUsername());

        if (user == null) {
            redirectToHome(response);
            return;
        }

        // Set user info for profile page
        request.setAttribute("user", user);
        forwardToPage(request, response, "doanweb/html/profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Users loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            redirectToLogin(response);
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            redirectToProfile(response);
            return;
        }

        switch (action) {
            case "/showinfo":
                handleViewProfile(request, response, loggedInUser);
                break;

            case "/changeInfo":
                handleEditProfile(request, response, loggedInUser);
                break;

            case "/change-pass":
                handleChangePassword(request, response, loggedInUser);
                break;

            case "/deleteAccount":
                handleDeleteAccount(request, response, loggedInUser);
                break;

            default:
                redirectToProfile(response);
        }
    }

    private Users getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Users) session.getAttribute("user");
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    private void redirectToHome(HttpServletResponse response) throws IOException {
        response.sendRedirect("/home");
    }

    private void redirectToProfile(HttpServletResponse response) throws IOException {
        response.sendRedirect("/profile");
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void handleEditProfile(HttpServletRequest request, HttpServletResponse response, Users loggedInUser)
            throws IOException, ServletException {
        String newEmail = request.getParameter("email");
        String newPhone = request.getParameter("phone");
        String newAddress = request.getParameter("address");

        // Update user info
        loggedInUser.setEmail(newEmail);
        loggedInUser.setPhone(newPhone);
        loggedInUser.setAddress(newAddress);

        // Update database
        boolean isUpdated = dao.editUser(loggedInUser);

        if (isUpdated) {
            // Update session and reload profile
            request.getSession().setAttribute("user", loggedInUser);
            request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            handleViewProfile(request, response, loggedInUser); // Reload profile info
        } else {
            // If update failed, show error message
            request.setAttribute("errorMessage", "Cập nhật thông tin thất bại. Vui lòng thử lại!");
            forwardToPage(request, response, "doanweb/html/edit-profile.jsp");
        }
    }

    private void handleViewProfile(HttpServletRequest request, HttpServletResponse response, Users loggedInUser)
            throws ServletException, IOException {
        Users user = dao.getUserByUsername(loggedInUser.getUsername());
        request.setAttribute("user", user);
        forwardToPage(request, response, "doanweb/html/profile.jsp");
    }

    // Phương thức xử lý thay đổi mật khẩu
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response, Users loggedInUser)
            throws ServletException, IOException {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // Kiểm tra và thay đổi mật khẩu
        boolean isPasswordChanged = dao.changePassword(loggedInUser.getUsername(), oldPassword, newPassword);

        if (isPasswordChanged) {
            request.setAttribute("successMessage", "Mật khẩu đã được thay đổi thành công!");
            forwardToPage(request, response, "doanweb/html/profile.jsp");
        } else {
            request.setAttribute("errorMessage", "Mật khẩu cũ không đúng hoặc có lỗi xảy ra!");
            forwardToPage(request, response, "doanweb/html/change-password.jsp");
        }
    }

    // Phương thức xử lý xóa tài khoản
    private void handleDeleteAccount(HttpServletRequest request, HttpServletResponse response, Users loggedInUser)
            throws ServletException, IOException {
        String password = request.getParameter("password");

        // Kiểm tra và xóa tài khoản
        boolean isAccountDeleted = dao.deleteAccount(loggedInUser.getUsername(), password);

        if (isAccountDeleted) {
            // Xóa tài khoản thành công, yêu cầu người dùng đăng nhập lại
            request.getSession().invalidate();  // Hủy session
            response.sendRedirect("/login");
        } else {
            request.setAttribute("errorMessage", "Mật khẩu không đúng hoặc có lỗi xảy ra!");
            forwardToPage(request, response, "doanweb/html/delete-account.jsp");
        }
    }
}

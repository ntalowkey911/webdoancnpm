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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập mã hóa UTF-8 để xử lý tiếng Việt
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form đăng ký
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Kiểm tra nếu có tham số nào rỗng hoặc null
        if (isEmpty(username) || isEmpty(email) || isEmpty(password) || isEmpty(confirmPassword) || isEmpty(phone) || isEmpty(address)) {
            request.setAttribute("error", "Tất cả các trường là bắt buộc.");
            forwardToRegisterPage(request, response);
            return;
        }

        // Kiểm tra nếu mật khẩu và xác nhận mật khẩu không khớp
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu không khớp.");
            forwardToRegisterPage(request, response);
            return;
        }

        dao d = new dao();
        Users existingUser = d.checkExist(username);

        // Kiểm tra nếu tài khoản đã tồn tại
        if (existingUser != null) {
            request.setAttribute("error", "Tên người dùng đã tồn tại.");
            forwardToRegisterPage(request, response);
        } else {
            try {
                // Tạo muối ngẫu nhiên
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);

                // Mã hóa mật khẩu với PBKDF2WithHmacSHA256
                String hashedPassword = hashPassword(password, salt);

                // Đăng ký người dùng mới
                d.Register(username, email, hashedPassword, phone, address);
                request.setAttribute("success", "Đăng ký thành công!");
                response.sendRedirect("doanweb/html/Login.jsp"); // Chuyển hướng đến trang đăng nhập sau khi đăng ký
            } catch (Exception e) {
                // Ghi log ngoại lệ và thông báo lỗi cho người dùng
                LOGGER.log(Level.SEVERE, "Lỗi trong quá trình đăng ký", e);
                request.setAttribute("error", "Đã xảy ra lỗi trong quá trình đăng ký. Vui lòng thử lại.");
                forwardToRegisterPage(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng về trang đăng ký nếu truy cập GET
        forwardToRegisterPage(request, response);
    }

    private void forwardToRegisterPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/Register.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 65536;
        int keyLength = 256;
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return bytesToHex(salt) + ":" + bytesToHex(hash); // Lưu cả salt và hash
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

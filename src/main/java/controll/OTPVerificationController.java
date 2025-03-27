package controll;

import dao.OTPDao;
import entity.OTP;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/verifyOTP")
public class OTPVerificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enteredOTP = request.getParameter("otp");
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            request.setAttribute("error", "Phiên đăng ký đã hết hạn, vui lòng đăng ký lại.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/Register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        OTPDao otpDao = new OTPDao();
        OTP otpObj = otpDao.getOTPByUserId(userId);

        if (otpObj == null) {
            request.setAttribute("error", "Không tìm thấy OTP. Vui lòng yêu cầu gửi lại OTP.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/OTPVerification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Kiểm tra thời gian hết hạn
        if (new Date().after(otpObj.getExpirationTime())) {
            request.setAttribute("error", "OTP đã hết hạn. Vui lòng gửi lại OTP.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/OTPVerification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // So sánh OTP
        if (enteredOTP.equals(otpObj.getOtp())) {
            otpDao.markOTPAsVerified(otpObj.getId());
            request.getSession().removeAttribute("userId");
            response.sendRedirect("doanweb/html/Login.jsp?message=verify_success");
        } else {
            request.setAttribute("error", "OTP không đúng, vui lòng thử lại.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/doanweb/html/OTPVerification.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

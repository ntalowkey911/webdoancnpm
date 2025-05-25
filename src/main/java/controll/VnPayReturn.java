package controll;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/vnpay-ipn")
public class VnPayReturn extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


            response.sendRedirect(request.getContextPath() + "/home.jsp");


    }
}
package controll;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String page = null;

        if (action == null || action.isEmpty()) {
            page = "/admin/dashboard.jsp";  // Đường dẫn mặc định
        } else {
            switch (action) {
                case "product-management":
                    page = "/admin/product-management.jsp";
                    break;
                case "category-management":
                    page = "/admin/category-management.jsp";
                    break;
                case "order-management":
                    page = "/admin/order-management.jsp";
                    break;
                case "user-management":
                    page = "/admin/user-management.jsp";
                    break;
                case "report-management":
                    page = "/admin/report-management.jsp";
                    break;
                case "promotion-management":
                    page = "/admin/promotion-management.jsp";
                    break;
                default:
                    page = "/admin/404.jsp";  // Trang lỗi trong thư mục admin
                    break;
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}

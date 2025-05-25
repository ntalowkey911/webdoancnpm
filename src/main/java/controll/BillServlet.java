package controll;

import dao.OrderDao;
import dao.OrderDetailDAO;
import entity.Order;
import entity.OrderDetails;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Lấy orderId từ parameter hoặc session
            Integer orderId = getOrderIdFromRequest(request, session);

            if (orderId == null) {
                response.sendRedirect("orders.jsp");
                return;
            }

            // Kiểm tra đơn hàng thuộc về user hiện tại
            OrderDao orderDAO = new OrderDao();
            Order order = orderDAO.getOrderById(orderId);

            if (order == null || order.getUserId() != user.getId()) {
                response.sendRedirect("orders.jsp?error=invalid_order");
                return;
            }

            // Lấy chi tiết đơn hàng
            List<OrderDetails> orderDetails = new OrderDetailDAO().getOrderDetailsByOrderId(orderId);

            // Đặt thông tin vào request
            request.setAttribute("order", order);
            request.setAttribute("orderDetails", orderDetails);
            request.getRequestDispatcher("/doanweb/html/bill.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=" + e.getMessage());
        }
    }

    private Integer getOrderIdFromRequest(HttpServletRequest request, HttpSession session) {
        // Ưu tiên lấy từ parameter trước
        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            try {
                return Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        // Nếu không có trong parameter, thử lấy từ session
        return (Integer) session.getAttribute("currentOrderId");
    }
}
package controll;

import dao.CartDao;
import dao.MySQLConnection;
import dao.OrderDao;
import entity.Order;
import dao.OrderDetailDAO;
import dao.PaymentDAO;
import dao.ShippingDAO;
import dao.dao;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/checkout1")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getId();
        CartDao cartDao = new CartDao();
        dao dao = new dao();

        List<CartItem> cart = cartDao.getCartByUserId(userId);

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp?error=empty_cart");
            return;
        }

        try (Connection conn = MySQLConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Lấy thông tin từ form
            double shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
            String shippingMethod = request.getParameter("shippingMethod");
            String shippingAddress = request.getParameter("recipientAddress");
            String orderNote = request.getParameter("orderNote");
            String paymentMethod = request.getParameter("paymentType");
            Date deliveryDate = Date.valueOf(request.getParameter("deliveryDate"));
            String deliveryTime = request.getParameter("specificDeliveryTime");
            String receiverName = request.getParameter("recipientName");
            String receiverPhone = request.getParameter("recipientPhone");
            String paymentStatus = request.getParameter("paymentStatus");
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));

            // 1. Tạo đơn hàng
            OrderDao orderDAO = new OrderDao(conn);
            int orderId = orderDAO.createOrder(userId, totalAmount, shippingMethod, deliveryDate,
                    deliveryTime, paymentMethod, orderNote, receiverName, receiverPhone, shippingAddress);

            // 2. Thêm chi tiết đơn hàng
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO(conn);
            for (CartItem item : cart) {
                int productId = item.getCart().getProduct_id();
                int quantity = item.getQuantity();
                double price = dao.getProductPriceById(productId);
                orderDetailDAO.addOrderDetail(orderId, productId, quantity, price);
            }

            // 3. Thêm thông tin vận chuyển và thanh toán
            new ShippingDAO(conn).addShipping(orderId, shippingFee);
            new PaymentDAO(conn).addPayment(orderId, paymentStatus);

            conn.commit();

            // Lưu thông tin vào session
            session.setAttribute("currentOrderId", orderId);
            session.setAttribute("orderTotalAmount", totalAmount);

            // Xử lý thanh toán VNPay
            if ("VNPAY".equals(paymentMethod)) {
                request.setAttribute("orderId", orderId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/vnpay-payment");
                dispatcher.forward(request, response);
                return;
            }

            // Xóa giỏ hàng và chuyển hướng đến trang hóa đơn
            cartDao.clearCart(userId);
            response.sendRedirect("bill?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=" + e.getMessage());
        }
    }
}
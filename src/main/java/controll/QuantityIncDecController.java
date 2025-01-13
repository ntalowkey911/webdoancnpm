package controll;

import dao.cart.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet("/quantity-inc-dec")
public class QuantityIncDecController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if (action == null || idParam == null || (!"inc".equals(action) && !"dec".equals(action))) {
            response.sendRedirect("/cart");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("/cart");
            return;
        }

        ArrayList<CartItem> cartList = (ArrayList<CartItem>) request.getSession().getAttribute("cart-list");
        if (cartList == null || cartList.isEmpty()) {
            response.sendRedirect("/cart");
            return;
        }

        boolean productFound = false;
        for (CartItem cartItem : cartList) {
            if (cartItem.getProduct().getId() == id) {
                productFound = true;
                if ("inc".equals(action)) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                } else if ("dec".equals(action) && cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }
                break;
            }
        }

        if (!productFound) {
            response.getWriter().println("Product not found in cart.");
            return;
        }

        request.getSession().setAttribute("cart-list", cartList);

        String referer = request.getHeader("Referer");
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("/cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}


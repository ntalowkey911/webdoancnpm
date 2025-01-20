package controll;

import entity.Products;
import dao.dao;  // Đảm bảo import lớp DAO đúng
import entity.Review;
import dao.ReviewDao;
import dao.UserDao;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/detail")
public class DetailController extends HttpServlet {
    private dao d;
    private ReviewDao rd;
    private UserDao ud;

    @Override
    public void init() throws ServletException {
        d = new dao();
        rd = new ReviewDao();
        ud = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách sản phẩm từ dao
        String productId = request.getParameter("id");
        Products product = d.getProductById(Integer.parseInt(productId));
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + product.getName()); // In ra tên sản phẩm
        request.setAttribute("product", product);

        // Lấy danh sách review từ DAO
        List<Review> reviews = rd.getReviewsByProductId(Integer.parseInt(productId));
        System.out.println("Number of reviews: " + reviews.size()); // In ra số lượng review lấy được
        for (Review review : reviews) {
            System.out.println("Review ID: " + review.getReview_id());
            System.out.println("Review Rating: " + review.getRating());
            System.out.println("Review Comment: " + review.getComment());
            System.out.println("Review Date: " + review.getReview_date());

            // Kiểm tra xem user có dữ liệu không
            Users user = ud.getUserByUserId(review.getUser_id());  // Lấy đối tượng user từ review
            if (user != null) {
                review.setUsername(user.getUsername());
                System.out.println("User Username: " + user.getUsername());  // In ra tên người dùng
            } else {
                System.out.println("No user found for this review.");
            }
        }
        request.setAttribute("reviews", reviews);

        // Lấy toàn bộ sản phẩm và trộn ngẫu nhiên
        List<Products> randomProductList = d.getRandomProducts();
        System.out.println("Number of random products: " + randomProductList.size()); // In ra số lượng sản phẩm ngẫu nhiên
        for (Products p : randomProductList) {
            System.out.println("Random Product ID: " + p.getId());
            System.out.println("Random Product Name: " + p.getName());
        }
        request.setAttribute("randomProductList", randomProductList);

        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("doanweb/html/detail.jsp").forward(request, response);
    }


}
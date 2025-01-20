package dao;

import entity.Review;
import entity.Users;

import java.util.List;

public class test {
    public static void main(String[] args) {
        int productId = 1; // Ví dụ: ID sản phẩm
        int rating = 5;// Ví dụ: Rating bạn muốn lọc (có thể thử với rating = 0 để không lọc theo rating)

        // Tạo đối tượng ReviewDao (hoặc lớp có phương thức getReviewByProductIdAndRating)
        ReviewDao reviewDao = new ReviewDao();
        UserDao ud = new UserDao();

        // Lấy danh sách reviews theo productId và rating
        List<Review> reviews = reviewDao.getReviewByProductIdAndRating(productId, rating);

        // In kết quả
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for this product with the given rating.");
        } else {
            System.out.println("Reviews for product ID " + productId + " with rating " + rating + ":");
            for (Review review : reviews) {
                Users user = ud.getUserByUserId(review.getUser_id());
                System.out.println("Review ID: " + review.getReview_id());
                System.out.println("Product ID: " + review.getProduct_id());
                System.out.println("User ID: " + review.getUser_id());
                System.out.println("Username: " + (user != null ? user.getUsername() : "Anonymous")); // Lấy username
                System.out.println("Rating: " + review.getRating());
                System.out.println("Comment: " + review.getComment());
                System.out.println("Review Date: " + review.getReview_date());
                System.out.println("-----------------------------");
            }
        }
    }
}
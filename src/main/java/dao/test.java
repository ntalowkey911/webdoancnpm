package dao;

import entity.Products;
import entity.Review;
import entity.Users;
import dao.WishListDao;
import java.util.List;

    public class test {
    public static void main(String[] args) {
        int userId = 1; // Ví dụ, lấy userId từ session hoặc tham số
        int productId = 1; // Ví dụ, lấy productId từ request hoặc tham số
        WishListDao dao = new WishListDao();
        System.out.println(dao.getWishlistByUserId(userId));

    }
}
package dao;

import entity.Categories;
import entity.Products;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dao {
    // Phương thức lấy danh sách tất cả sản phẩm
    public List<Products> getAllProducts() {
        List<Products> l  = new ArrayList<>();
        String query = "SELECT * FROM Products";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs  = statement.executeQuery()) {

            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getTimestamp("created_at"),// Lấy giá trị DATETIME
                        rs.getInt("category_Id")
                );
                l .add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l ;
    }
    public List<Categories> getAllCategories() {
        List<Categories> l  = new ArrayList<>();
        String query = "SELECT * FROM Categories";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs  = statement.executeQuery()) {
            while (rs.next()) {
                Categories product = new Categories(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                l .add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l ;}

    public static Products  getLatestProduct()  {
        String query = "SELECT * FROM Products ORDER BY id  DESC LIMIT 1";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs  = statement.executeQuery()) {
            while (rs.next()) {
                return new Products(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getTimestamp("created_at"),// Lấy giá trị DATETIME
                        rs.getInt("category_Id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;}

    public int updateProduct(Products product) {
        String query = "UPDATE Products SET name = ?, description = ?, price = ?, stock = ?, image = ?, category_id = ? WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getPrice());
            statement.setInt(4, product.getStock());
            statement.setString(5, product.getImage());
            statement.setInt(6, product.getCategoryId());
            statement.setInt(7, product.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Xóa sản phẩm
    public int deleteProduct(int productId) {
        String query = "DELETE FROM Products WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Cập nhật số lượng tồn kho
    public int updateStock(int productId, int stock) {
        String query = "UPDATE products SET stock = ? WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, stock);
            statement.setInt(2, productId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        dao d = new dao();
        List<Products> l = d.getAllProducts();
        List<Categories> l1  = d.getAllCategories();
        Products latestProduct = dao.getLatestProduct();
        // Kiểm tra kết quả
        if (latestProduct != null) {
            System.out.println("Sản phẩm mới nhất:");
            System.out.println("ID: " + latestProduct.getId());
            System.out.println("Tên: " + latestProduct.getName());
            System.out.println("Mô tả: " + latestProduct.getDescription());
            System.out.println("Giá: " + latestProduct.getPrice());
            System.out.println("Số lượng: " + latestProduct.getStock());
            System.out.println("Hình ảnh: " + latestProduct.getImage());
            System.out.println("Ngày tạo: " + latestProduct.getCreatedAt());
            System.out.println("ID Danh mục: " + latestProduct.getCategoryId());
        } else {
            System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
        }
for(Products p : l) {
    System.out.println(p);
}
for(Categories c : l1) {
    System.out.println(c);
}
    }
}

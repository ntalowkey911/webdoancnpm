package entity;

import java.sql.Timestamp;
import java.util.List;

public class Products {
    private int id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private String image;  // Danh sách ảnh của sản phẩm
    private Timestamp createdAt; // Sử dụng Timestamp cho DATETIME
    private int category_id; // Tham chiếu đến Category
    // Constructor

    public Products(int id, String name, String description, int price, int stock,String image, Timestamp createdAt, int category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.createdAt = createdAt;
        this.category_id = category_id;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }
    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt + // Thêm trường này
                ", category_id=" + category_id +
                '}';
    }

}

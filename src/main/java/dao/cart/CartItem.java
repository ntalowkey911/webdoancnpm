package dao.cart;

import entity.Products;

public class CartItem {
    private Products product; // Đối tượng sản phẩm
    private int quantity; // Số lượng sản phẩm trong giỏ

    // Constructor
    public CartItem(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters và Setters
    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Phương thức tính tổng giá tiền của sản phẩm trong giỏ
    public int getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}

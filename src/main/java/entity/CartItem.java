package entity;

public class CartItem {
    private Products product;
    private int quantity;
    private int totalPrice; // Thêm thuộc tính tổng giá tiền

    // Constructor
    public CartItem(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity; // Khởi tạo tổng giá tiền
    }

    // Getters và Setters
    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
        updateTotalPrice(); // Cập nhật tổng giá tiền khi thay đổi sản phẩm
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice(); // Cập nhật tổng giá tiền khi thay đổi số lượng
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice; // Cho phép cập nhật trực tiếp nếu cần
    }

    // Phương thức cập nhật tổng giá tiền
    private void updateTotalPrice() {
        this.totalPrice = product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

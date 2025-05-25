package entity;

import java.sql.Timestamp;

public class Payments {
    private int id;
    private int orderID;
    private Timestamp paymentDate;
    private String paymentStatus;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Payments() {
    }

    public Payments(int id, int orderID, Timestamp paymentDate, String paymentStatus, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.orderID = orderID;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "id=" + id +
                ", orderID=" + orderID +
                ", paymentDate=" + paymentDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
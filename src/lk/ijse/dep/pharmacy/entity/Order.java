package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class Order implements SuperEntity {
    private String orderId;
    private Date orderDate;
    private String supplierId;

    public Order() {
    }

    public Order(String orderId, Date orderDate, String supplierId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.supplierId = supplierId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}

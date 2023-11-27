package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;

public class OrderDTO2 {
    private String orderId;
    private String supplierId;
    private String supplierName;
    private Date orderDate;
    private double total;

    public OrderDTO2() {
    }

    public OrderDTO2(String orderId, String supplierId, String supplierName, Date orderDate, double total) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.orderDate = orderDate;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDTO2{" +
                "orderId='" + orderId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", orderDate=" + orderDate +
                ", total=" + total +
                '}';
    }
}

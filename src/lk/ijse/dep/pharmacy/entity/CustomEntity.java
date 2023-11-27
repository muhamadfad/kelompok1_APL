package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class CustomEntity implements SuperEntity {
    private String orderId;
    private String supplierId;
    private String supplierName;
    private Date orderDate;
    private double Total;

    public CustomEntity() {
    }

    public CustomEntity(String orderId, String supplierId, String supplierName, Date orderDate) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.orderDate = orderDate;
    }

    public CustomEntity(String orderId, String supplierId, String supplierName, Date orderDate, double total) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.orderDate = orderDate;
        Total = total;
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
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "orderId='" + orderId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", orderDate=" + orderDate +
                ", Total=" + Total +
                '}';
    }
}

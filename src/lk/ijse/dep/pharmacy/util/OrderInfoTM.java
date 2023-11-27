package lk.ijse.dep.pharmacy.util;

public class OrderInfoTM {
    private String orderId;
    private String supplierId;
    private String supplierName;
    private String date;
    private double total;

    public OrderInfoTM(String orderId) {
        this.orderId = orderId;
    }

    public OrderInfoTM(String orderId, String supplierId, String supplierName, String date, double total) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderInfoTM{" +
                "orderId='" + orderId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                '}';
    }
}

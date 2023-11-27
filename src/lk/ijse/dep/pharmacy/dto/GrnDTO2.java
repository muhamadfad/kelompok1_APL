package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;

public class GrnDTO2 {
    private String grnId;
    private String orderId;
    private Date orderDate;
    private String supplierId;

    public GrnDTO2() {
    }

    public GrnDTO2(String grnId, String orderId, Date orderDate, String supplierId) {
        this.grnId = grnId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.supplierId = supplierId;
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
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
        return "GrnDTO2{" +
                "grnId='" + grnId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}

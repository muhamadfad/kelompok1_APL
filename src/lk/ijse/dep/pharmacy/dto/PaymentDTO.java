package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;

public class PaymentDTO {
    private String id;
    private Date date;
    private String type;
    private String presId;
    private String orderId;
    private double total;

    public PaymentDTO(String id, Date date, String type, String presId, String orderId, double total) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.presId = presId;
        this.orderId = orderId;
        this.total = total;
    }

    public PaymentDTO(String id, Date date, String orderId, double total) {
        this.id = id;
        this.date = date;
        this.orderId = orderId;
        this.total = total;
    }


    public PaymentDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPresId() {
        return presId;
    }

    public void setPresId(String presId) {
        this.presId = presId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", presId='" + presId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", total=" + total +
                '}';
    }
}

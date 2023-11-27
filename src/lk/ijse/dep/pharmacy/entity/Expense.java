package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class Expense {
    private String paymentId;
    private Date paymentDate;
    private String orderId;
    private double totalAmount;


    public Expense() {
    }

    public Expense(String paymentId, Date paymentDate, String orderId, double totalAmount) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "paymentId='" + paymentId + '\'' +
                ", paymentDate=" + paymentDate +
                ", orderId='" + orderId + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class Payment implements SuperEntity {
    private String paymentId;
    private Date paymentDate;
    private String paymentType;
    private String prescriptionId;
    private String orderId;
    private double totalAmount;

    public Payment() {
    }

    public Payment(String paymentId, Date paymentDate, String paymentType, String prescriptionId, String orderId, double totalAmount) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
        this.prescriptionId = prescriptionId;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public Payment(String paymentId, Date paymentDate, String orderId, double totalAmount) {
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
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
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", paymentDate=" + paymentDate +
                ", paymentType='" + paymentType + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

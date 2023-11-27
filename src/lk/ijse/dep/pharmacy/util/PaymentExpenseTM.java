package lk.ijse.dep.pharmacy.util;

public class PaymentExpenseTM {
    private String id;
    private String orderId;
    private double total;

    public PaymentExpenseTM() {
    }

    public PaymentExpenseTM(String id, String orderId, double total) {
        this.id = id;
        this.orderId = orderId;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "PaymentExpenseTM{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", total=" + total +
                '}';
    }
}

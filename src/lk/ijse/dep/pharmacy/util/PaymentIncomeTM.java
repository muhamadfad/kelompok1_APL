package lk.ijse.dep.pharmacy.util;

public class PaymentIncomeTM {
    private String id;
    private String presId;
    private double total;

    public PaymentIncomeTM() {
    }

    public PaymentIncomeTM(String id, String presId, double total) {
        this.id = id;
        this.presId = presId;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresId() {
        return presId;
    }

    public void setPresId(String presId) {
        this.presId = presId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PaymentIncomeTM{" +
                "id='" + id + '\'' +
                ", presId='" + presId + '\'' +
                ", total=" + total +
                '}';
    }
}

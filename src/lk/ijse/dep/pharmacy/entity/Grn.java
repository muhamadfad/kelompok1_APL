package lk.ijse.dep.pharmacy.entity;

public class Grn implements SuperEntity{

   private String grnId;
    private String orderId;

    public Grn() {
    }

    public Grn(String grnId, String orderId) {
        this.grnId = grnId;
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    @Override
    public String toString() {
        return "Grn{" +
                "grnId='" + grnId + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}

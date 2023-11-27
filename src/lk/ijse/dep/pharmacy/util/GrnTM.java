package lk.ijse.dep.pharmacy.util;

public class GrnTM {
    private String grnId;
    private String orderId;
    private String oderDate;
    private String supplierId;

    public GrnTM() {
    }

    public GrnTM(String grnId, String orderId, String oderDate, String supplierId) {
        this.grnId = grnId;
        this.orderId = orderId;
        this.oderDate = oderDate;
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

    public String getOderDate() {
        return oderDate;
    }

    public void setOderDate(String oderDate) {
        this.oderDate = oderDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "GrnTM{" +
                "grnId='" + grnId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", oderDate='" + oderDate + '\'' +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}

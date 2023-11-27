package lk.ijse.dep.pharmacy.util;

public class GrnDetailTM {
    private String grnId;
    private String code;
    private String des;
    private int qty;
    private double unitPrice;

    public GrnDetailTM() {
    }

    public GrnDetailTM(String grnId, String code, String des, int qty, double unitPrice) {
        this.grnId = grnId;
        this.code = code;
        this.des = des;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "GrnDetailTM{" +
                "grnId='" + grnId + '\'' +
                ", code='" + code + '\'' +
                ", des='" + des + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

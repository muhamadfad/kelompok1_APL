package lk.ijse.dep.pharmacy.dto;

public class GrnDetailDTO2 {
    private String grnId;
    private String code;
    private String drugDes;
    private int qty;
    private double unitPrice;

    public GrnDetailDTO2() {
    }

    public GrnDetailDTO2(String grnId, String code, String drugDes, int qty, double unitPrice) {
        this.grnId = grnId;
        this.code = code;
        this.drugDes = drugDes;
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

    public String getDrugDes() {
        return drugDes;
    }

    public void setDrugDes(String drugDes) {
        this.drugDes = drugDes;
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
        return "GrnDetailDTO2{" +
                "grnId='" + grnId + '\'' +
                ", code='" + code + '\'' +
                ", drugDes='" + drugDes + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

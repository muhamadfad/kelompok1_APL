package lk.ijse.dep.pharmacy.entity;

public class CustomEntity5 implements SuperEntity {
    private String grnId;
    private String drugCode;
    private String drugDes;
    private int qty;
    private double unitPrice;

    public CustomEntity5(String grnId, String drugCode, String drugDes, int qty, double unitPrice) {
        this.grnId = grnId;
        this.drugCode = drugCode;
        this.drugDes = drugDes;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public CustomEntity5() {
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
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
        return "CustomEntity5{" +
                "grnId='" + grnId + '\'' +
                ", drugCode='" + drugCode + '\'' +
                ", drugDes='" + drugDes + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

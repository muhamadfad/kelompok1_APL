package lk.ijse.dep.pharmacy.entity;

public class CustomEntity3 implements SuperEntity {
    private String batchId;
    private String drugCode;
    private int drugQty;

    public CustomEntity3() {
    }

    public CustomEntity3(String batchId, String drugCode, int drugQty, double drugUnitPrice) {
        this.batchId = batchId;
        this.drugCode = drugCode;
        this.drugQty = drugQty;
        this.drugUnitPrice = drugUnitPrice;
    }

    private double drugUnitPrice;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public int getDrugQty() {
        return drugQty;
    }

    public void setDrugQty(int drugQty) {
        this.drugQty = drugQty;
    }

    public double getDrugUnitPrice() {
        return drugUnitPrice;
    }

    public void setDrugUnitPrice(double drugUnitPrice) {
        this.drugUnitPrice = drugUnitPrice;
    }

    @Override
    public String toString() {
        return "CustomEntity3{" +
                "batchId='" + batchId + '\'' +
                ", drugCode='" + drugCode + '\'' +
                ", drugQty=" + drugQty +
                ", drugUnitPrice=" + drugUnitPrice +
                '}';
    }
}

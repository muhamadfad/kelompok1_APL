package lk.ijse.dep.pharmacy.entity;

public class StockPK {
    private String batchId;
    private String drugCode;

    public StockPK() {
    }

    public StockPK(String batchId, String drugCode) {
        this.batchId = batchId;
        this.drugCode = drugCode;
    }

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

    @Override
    public String toString() {
        return "StockPK{" +
                "batchId='" + batchId + '\'' +
                ", drugCode='" + drugCode + '\'' +
                '}';
    }
}

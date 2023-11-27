package lk.ijse.dep.pharmacy.entity;

public class GrnDetailPK {
    private String grnId;
    private String drugCode;

    public GrnDetailPK() {
    }

    public GrnDetailPK(String grnId, String drugCode) {
        this.grnId = grnId;
        this.drugCode = drugCode;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }


    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    @Override
    public String toString() {
        return "GrnDetailPK{" +
                "grnId='" + grnId + '\'' +
                ", drugCode='" + drugCode + '\'' +
                '}';
    }
}

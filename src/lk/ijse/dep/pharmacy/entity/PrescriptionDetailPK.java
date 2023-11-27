package lk.ijse.dep.pharmacy.entity;

public class PrescriptionDetailPK {
    private String prescriptionId;
    private String drugCode;

    public PrescriptionDetailPK() {
    }

    public PrescriptionDetailPK(String prescriptionId, String drugCode) {
        this.prescriptionId = prescriptionId;
        this.drugCode = drugCode;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    @Override
    public String toString() {
        return "PrescriptionDetailPK{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", drugCode='" + drugCode + '\'' +
                '}';
    }
}

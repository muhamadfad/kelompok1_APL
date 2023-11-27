package lk.ijse.dep.pharmacy.entity;

public class PrescriptionDetail implements SuperEntity {
    private PrescriptionDetailPK prescriptionDetailPK;
    private int quantity;

    public PrescriptionDetail() {
    }

    public PrescriptionDetail(PrescriptionDetailPK prescriptionDetailPK, int quantity, double unitPrice) {
        this.prescriptionDetailPK = prescriptionDetailPK;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public PrescriptionDetail(String prescriptionId, String drugCode, int quantity, double unitPrice) {
        this.prescriptionDetailPK = new PrescriptionDetailPK(prescriptionId,drugCode);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    private double unitPrice;

    public PrescriptionDetailPK getPrescriptionDetailPK() {
        return prescriptionDetailPK;
    }

    public void setPrescriptionDetailPK(PrescriptionDetailPK prescriptionDetailPK) {
        this.prescriptionDetailPK = prescriptionDetailPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "PrescriptionDetail{" +
                "prescriptionDetailPK=" + prescriptionDetailPK +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

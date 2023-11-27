package lk.ijse.dep.pharmacy.entity;

public class GrnDetail implements SuperEntity{
    private GrnDetailPK grnDetailPK;
    private int qty;
    private double unitPrice;

    public GrnDetail() {
    }

    public GrnDetail(GrnDetailPK grnDetailPK, int qty, double unitPrice) {
        this.grnDetailPK = grnDetailPK;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public GrnDetail(String grnId,String drugCode, int qty, double unitPrice) {
        this.grnDetailPK = new GrnDetailPK(grnId,drugCode);
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public GrnDetailPK getGrnDetailPK() {
        return grnDetailPK;
    }

    public void setGrnDetailPK(GrnDetailPK grnDetailPK) {
        this.grnDetailPK = grnDetailPK;
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
        return "GrnDetail{" +
                "grnDetailPK=" + grnDetailPK +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

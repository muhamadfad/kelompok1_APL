package lk.ijse.dep.pharmacy.util;

public class StockDrugItemTM {
    private String code;
    private String des;
    private String manDate;
    private String expDate;
    private int qtyOnHand;
    private double unitPrice;

    public StockDrugItemTM() {
    }

    public StockDrugItemTM(String code, String des, String manDate, String expDate, int qtyOnHand, double unitPrice) {
        this.code = code;
        this.des = des;
        this.manDate = manDate;
        this.expDate = expDate;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
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

    public String getManDate() {
        return manDate;
    }

    public void setManDate(String manDate) {
        this.manDate = manDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "StockDrugItemTM{" +
                "code='" + code + '\'' +
                ", des='" + des + '\'' +
                ", manDate='" + manDate + '\'' +
                ", expDate='" + expDate + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

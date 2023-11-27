package lk.ijse.dep.pharmacy.util;

public class DrugItemTM {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private String manuDate;
    private String expireDate;
    private String locationCode;

    public DrugItemTM() {
    }

    public DrugItemTM(String code, String description, int qty, double unitPrice, String manuDate, String expireDate, String locationCode) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.manuDate = manuDate;
        this.expireDate = expireDate;
        this.locationCode = locationCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getManuDate() {
        return manuDate;
    }

    public void setManuDate(String manuDate) {
        this.manuDate = manuDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Override
    public String toString() {
        return "DrugItemTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", manuDate='" + manuDate + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", locationCode='" + locationCode + '\'' +
                '}';
    }
}

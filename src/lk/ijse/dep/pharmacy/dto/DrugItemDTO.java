package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;

public class DrugItemDTO {
    private String code;
    private String description;
    private Date manuDate;
    private Date expireDate;
    private int qtyOnHand;
    private double unitPrice;
    private String locationId;

    public DrugItemDTO() {
    }

    public DrugItemDTO(String code, String description, Date manuDate, Date expireDate, int qtyOnHand, double unitPrice, String locationId) {
        this.code = code;
        this.description = description;
        this.manuDate = manuDate;
        this.expireDate = expireDate;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.locationId = locationId;
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

    public Date getManuDate() {
        return manuDate;
    }

    public void setManuDate(Date manuDate) {
        this.manuDate = manuDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "DrugItemDTO{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", manuDate=" + manuDate +
                ", expireDate=" + expireDate +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", locationId='" + locationId + '\'' +
                '}';
    }
}

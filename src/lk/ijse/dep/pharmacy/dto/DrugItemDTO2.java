package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;
import java.util.List;

public class DrugItemDTO2 {
    private String code;
    private String description;
    private Date manuDate;
    private Date expireDate;
    private int qtyOnHand;
    private double unitPrice;
    private String locationId;
    private LocationDTO locationDetails;

    public DrugItemDTO2() {
    }

    public DrugItemDTO2(String code, String description, Date manuDate, Date expireDate, int qtyOnHand, double unitPrice, String locationId, LocationDTO locationDetails) {
        this.code = code;
        this.description = description;
        this.manuDate = manuDate;
        this.expireDate = expireDate;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.locationId = locationId;
        this.locationDetails = locationDetails;
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

    public LocationDTO getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(LocationDTO locationDetails) {
        this.locationDetails = locationDetails;
    }

    @Override
    public String toString() {
        return "DrugItemDTO2{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", manuDate=" + manuDate +
                ", expireDate=" + expireDate +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", locationId='" + locationId + '\'' +
                ", locationDetails=" + locationDetails +
                '}';
    }
}

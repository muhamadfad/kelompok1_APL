package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class DrugItem implements SuperEntity {
    private String drugCode;
    private String drugDes;
    private Date drugManufactureDate;
    private Date drugExpireDate;
    private int drugQtyOnHand;
    private double drugUnitPrice;
    private String locationId;

    public DrugItem() {
    }

    public DrugItem(String drugCode, String drugDes, Date drugManufactureDate, Date drugExpireDate, int drugQtyOnHand, double drugUnitPrice, String locationId) {
        this.drugCode = drugCode;
        this.drugDes = drugDes;
        this.drugManufactureDate = drugManufactureDate;
        this.drugExpireDate = drugExpireDate;
        this.drugQtyOnHand = drugQtyOnHand;
        this.drugUnitPrice = drugUnitPrice;
        this.locationId = locationId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugDes() {
        return drugDes;
    }

    public void setDrugDes(String drugDes) {
        this.drugDes = drugDes;
    }

    public Date getDrugManufactureDate() {
        return drugManufactureDate;
    }

    public void setDrugManufactureDate(Date drugManufactureDate) {
        this.drugManufactureDate = drugManufactureDate;
    }

    public Date getDrugExpireDate() {
        return drugExpireDate;
    }

    public void setDrugExpireDate(Date drugExpireDate) {
        this.drugExpireDate = drugExpireDate;
    }

    public int getDrugQtyOnHand() {
        return drugQtyOnHand;
    }

    public void setDrugQtyOnHand(int drugQtyOnHand) {
        this.drugQtyOnHand = drugQtyOnHand;
    }

    public double getDrugUnitPrice() {
        return drugUnitPrice;
    }

    public void setDrugUnitPrice(double drugUnitPrice) {
        this.drugUnitPrice = drugUnitPrice;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "DrugItem{" +
                "drugCode='" + drugCode + '\'' +
                ", drugDes='" + drugDes + '\'' +
                ", drugManufactureDate=" + drugManufactureDate +
                ", drugExpireDate=" + drugExpireDate +
                ", drugQtyOnHand=" + drugQtyOnHand +
                ", drugUnitPrice=" + drugUnitPrice +
                ", locationId='" + locationId + '\'' +
                '}';
    }
}

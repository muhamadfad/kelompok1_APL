package lk.ijse.dep.pharmacy.entity;

public class Supplier implements SuperEntity {
    private String supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierMobile;
    private String supplierLand;
    private String supplierEmail;
    private String supplierVehicleNumber;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String supplierAddress, String supplierMobile, String supplierLand, String supplierEmail, String supplierVehicleNumber) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierMobile = supplierMobile;
        this.supplierLand = supplierLand;
        this.supplierEmail = supplierEmail;
        this.supplierVehicleNumber = supplierVehicleNumber;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierMobile() {
        return supplierMobile;
    }

    public void setSupplierMobile(String supplierMobile) {
        this.supplierMobile = supplierMobile;
    }

    public String getSupplierLand() {
        return supplierLand;
    }

    public void setSupplierLand(String supplierLand) {
        this.supplierLand = supplierLand;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierVehicleNumber() {
        return supplierVehicleNumber;
    }

    public void setSupplierVehicleNumber(String supplierVehicleNumber) {
        this.supplierVehicleNumber = supplierVehicleNumber;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", supplierMobile='" + supplierMobile + '\'' +
                ", supplierLand='" + supplierLand + '\'' +
                ", supplierEmail='" + supplierEmail + '\'' +
                ", supplierVehicleNumber='" + supplierVehicleNumber + '\'' +
                '}';
    }
}

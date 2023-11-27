package lk.ijse.dep.pharmacy.util;

import javafx.scene.control.Button;

public class DrugItemDetailTM2 {
    private String code;
    private String batchId;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btnDelete;

    public DrugItemDetailTM2() {
    }

    public DrugItemDetailTM2(String code, String batchId, String description, int qty, double unitPrice, double total, Button btnDelete) {
        this.code = code;
        this.batchId = batchId;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
        this.btnDelete = btnDelete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "DrugItemDetailTM2{" +
                "code='" + code + '\'' +
                ", batchId='" + batchId + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", btnDelete=" + btnDelete +
                '}';
    }
}

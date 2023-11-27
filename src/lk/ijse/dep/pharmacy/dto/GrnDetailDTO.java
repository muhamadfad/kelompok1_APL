package lk.ijse.dep.pharmacy.dto;

public class GrnDetailDTO {
    private String code;
    private int qty;
    private double unitPrice;

    public GrnDetailDTO() {
    }

    public GrnDetailDTO(String code, int qty, double unitPrice) {
        this.code = code;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return "GrnDetailDTO{" +
                "code='" + code + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

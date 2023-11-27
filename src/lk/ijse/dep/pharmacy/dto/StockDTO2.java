package lk.ijse.dep.pharmacy.dto;

public class StockDTO2 {
    private String id;
    private String code;
    private int qty;
    private double unitPrice;

    public StockDTO2() {
    }

    public StockDTO2(String id, String code, int qty, double unitPrice) {
        this.id = id;
        this.code = code;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "StockDTO2{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

package lk.ijse.dep.pharmacy.entity;

public class OrderDetailPK {
    private String orderId;
    private String drugCode;

    public OrderDetailPK(String orderId, String drugCode) {
        this.orderId = orderId;
        this.drugCode = drugCode;
    }

    public OrderDetailPK() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    @Override
    public String toString() {
        return "OrderDetailPK{" +
                "orderId='" + orderId + '\'' +
                ", drugCode='" + drugCode + '\'' +
                '}';
    }
}

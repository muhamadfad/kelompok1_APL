package lk.ijse.dep.pharmacy.entity;

public class OrderDetail implements SuperEntity {
    private OrderDetailPK orderDetailPK;
    private int quantity;
    private double unitPrice;

    public OrderDetail(OrderDetailPK orderDetailPK, int quantity, double unitPrice) {
        this.orderDetailPK = orderDetailPK;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(String orderId, String drugCode, int quantity, double unitPrice) {
        this.orderDetailPK = new OrderDetailPK(orderId,drugCode);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderDetailPK getOrderDetailPK() {
        return orderDetailPK;
    }

    public void setOrderDetailPK(OrderDetailPK orderDetailPK) {
        this.orderDetailPK = orderDetailPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailPK=" + orderDetailPK +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

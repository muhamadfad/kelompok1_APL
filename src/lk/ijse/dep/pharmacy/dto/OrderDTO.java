package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private String id;
    private String batchId;
    private String grnId;
    private Date date;
    private String supId;
    private List<OrderDetailDTO> orderDetail;
    private List<StockDTO> stock;
    private List<GrnDetailDTO> grnDetail;
    private PaymentDTO paymentDTO;

    public OrderDTO() {
    }

    public OrderDTO(String id, String batchId, String grnId, Date date, String supId, List<OrderDetailDTO> orderDetail, List<StockDTO> stock, List<GrnDetailDTO> grnDetail, PaymentDTO paymentDTO) {
        this.id = id;
        this.batchId = batchId;
        this.grnId = grnId;
        this.date = date;
        this.supId = supId;
        this.orderDetail = orderDetail;
        this.stock = stock;
        this.grnDetail = grnDetail;
        this.paymentDTO = paymentDTO;
    }

    public OrderDTO(String id, Date date, String supId) {
        this.id = id;
        this.date = date;
        this.supId = supId;
    }

    public OrderDTO(String id, Date date, String supId, List<OrderDetailDTO> orderDetail) {
        this.id = id;
        this.date = date;
        this.supId = supId;
        this.orderDetail = orderDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<StockDTO> getStock() {
        return stock;
    }

    public void setStock(List<StockDTO> stock) {
        this.stock = stock;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }

    public List<GrnDetailDTO> getGrnDetail() {
        return grnDetail;
    }

    public void setGrnDetail(List<GrnDetailDTO> grnDetail) {
        this.grnDetail = grnDetail;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", batchId='" + batchId + '\'' +
                ", grnId='" + grnId + '\'' +
                ", date=" + date +
                ", supId='" + supId + '\'' +
                ", orderDetail=" + orderDetail +
                ", stock=" + stock +
                ", grnDetail=" + grnDetail +
                ", paymentDTO=" + paymentDTO +
                '}';
    }
}

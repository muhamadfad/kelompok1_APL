package lk.ijse.dep.pharmacy.dto;

import java.util.List;

public class GrnDTO {
    private String id;
    private String orderId;
    private List<GrnDetailDTO> grnDetail;

    public GrnDTO() {
    }

    public GrnDTO(String id, String orderId, List<GrnDetailDTO> grnDetail) {
        this.id = id;
        this.orderId = orderId;
        this.grnDetail = grnDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<GrnDetailDTO> getGrnDetail() {
        return grnDetail;
    }

    public void setGrnDetail(List<GrnDetailDTO> grnDetail) {
        this.grnDetail = grnDetail;
    }

    @Override
    public String toString() {
        return "GrnDTO{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", grnDetail=" + grnDetail +
                '}';
    }
}

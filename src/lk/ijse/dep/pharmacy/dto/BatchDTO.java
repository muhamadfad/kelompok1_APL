package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;

public class BatchDTO {
    private String id;
    private String des;
    private Date expDate;
    private boolean status;

    public BatchDTO() {
    }

    public BatchDTO(String id, String des, Date expDate, boolean status) {
        this.id = id;
        this.des = des;
        this.expDate = expDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BatchDTO{" +
                "id='" + id + '\'' +
                ", des='" + des + '\'' +
                ", expDate=" + expDate +
                ", status=" + status +
                '}';
    }
}

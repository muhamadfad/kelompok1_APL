package lk.ijse.dep.pharmacy.entity;

import java.sql.Date;

public class Batch implements SuperEntity {
    private String batchId;
    private String batchDes;
    private Date batchExpireDate;
    private boolean batchStatus;

    public Batch() {
    }

    public Batch(String batchId, String batchDes, Date batchExpireDate, boolean batchStatus) {
        this.batchId = batchId;
        this.batchDes = batchDes;
        this.batchExpireDate = batchExpireDate;
        this.batchStatus = batchStatus;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchDes() {
        return batchDes;
    }

    public void setBatchDes(String batchDes) {
        this.batchDes = batchDes;
    }

    public Date getBatchExpireDate() {
        return batchExpireDate;
    }

    public void setBatchExpireDate(Date batchExpireDate) {
        this.batchExpireDate = batchExpireDate;
    }

    public boolean isBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(boolean batchStatus) {
        this.batchStatus = batchStatus;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchId='" + batchId + '\'' +
                ", batchDes='" + batchDes + '\'' +
                ", batchExpireDate=" + batchExpireDate +
                ", batchStatus=" + batchStatus +
                '}';
    }
}

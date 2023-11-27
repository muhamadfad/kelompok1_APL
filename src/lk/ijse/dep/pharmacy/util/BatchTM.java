package lk.ijse.dep.pharmacy.util;

import javafx.scene.control.Button;

import java.sql.Date;

public class BatchTM {
    private String id;
    private String des;
    private String date;
    private boolean status;
    private Button btnDelete;

    public BatchTM() {
    }

    public BatchTM(String id, String des, String date, boolean status, Button btnDelete) {
        this.id = id;
        this.des = des;
        this.date = date;
        this.status = status;
        this.btnDelete = btnDelete;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "BatchTM{" +
                "id='" + id + '\'' +
                ", des='" + des + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", btnDelete=" + btnDelete +
                '}';
    }
}

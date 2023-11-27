package lk.ijse.dep.pharmacy.util;

import javafx.scene.control.Button;

public class LocationTM {
    private String id;
    private String des;
    private boolean status;
    private Button btnDelete;

    public LocationTM(String id, String des, boolean status, Button btnDelete) {
        this.id = id;
        this.des = des;
        this.status = status;
        this.btnDelete = btnDelete;
    }

    public LocationTM() {
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
        return "LocationTM{" +
                "id='" + id + '\'' +
                ", des='" + des + '\'' +
                ", status=" + status +
                ", btnDelete=" + btnDelete +
                '}';
    }
}

package lk.ijse.dep.pharmacy.dto;

public class LocationDTO {
    private String id;
    private String des;
    private boolean status;

    public LocationDTO(String id, String des, boolean status) {
        this.id = id;
        this.des = des;
        this.status = status;
    }

    public LocationDTO() {
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

    @Override
    public String toString() {
        return "LocationDTO{" +
                "id='" + id + '\'' +
                ", des='" + des + '\'' +
                ", status=" + status +
                '}';
    }
}

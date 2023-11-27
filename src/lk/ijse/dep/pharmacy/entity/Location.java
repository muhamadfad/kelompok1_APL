package lk.ijse.dep.pharmacy.entity;

public class Location implements SuperEntity {
    private String locationId;
    private String locationDes;
    private boolean locationStatus;

    public Location() {
    }

    public Location(String locationId, String locationDes, boolean locationStatus) {
        this.locationId = locationId;
        this.locationDes = locationDes;
        this.locationStatus = locationStatus;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationDes() {
        return locationDes;
    }

    public void setLocationDes(String locationDes) {
        this.locationDes = locationDes;
    }

    public boolean isLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(boolean locationStatus) {
        this.locationStatus = locationStatus;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId='" + locationId + '\'' +
                ", locationDes='" + locationDes + '\'' +
                ", locationStatus=" + locationStatus +
                '}';
    }
}

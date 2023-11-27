package lk.ijse.dep.pharmacy.entity;

public enum Gender {
    MALE("Male"), FEMALE("Female");

    String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getValue(){
        return this.gender;
    }
}

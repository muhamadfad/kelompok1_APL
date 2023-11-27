package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.Doctor;

public interface DoctorDAO extends CrudDAO<Doctor, String> {
    String getLastDoctorId() throws Exception;

}

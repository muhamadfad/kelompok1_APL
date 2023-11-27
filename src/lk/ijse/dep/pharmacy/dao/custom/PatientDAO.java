package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.Patient;

public interface PatientDAO extends CrudDAO<Patient, String> {
    String getLastPatientId() throws Exception;

}

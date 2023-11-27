package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.Prescription;

public interface PrescriptionDAO extends CrudDAO<Prescription, String> {
    String getLastPrescriptionId() throws Exception;

    boolean existsByPatientId(String patientId) throws Exception;

    boolean existsByDoctorId(String doctorId) throws Exception;
}

package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.PatientDTO;

import java.util.List;

public interface PatientBO extends SuperBO {
    boolean savePatient(PatientDTO patient)throws Exception;

    boolean updatePatient(PatientDTO patient)throws Exception;

    boolean deletePatient(String patientId) throws Exception;

    List<PatientDTO> findAllPatients() throws Exception;

    String getLastPatientId()throws Exception;

    PatientDTO findPatient(String patientId) throws Exception;

    List<String> getAllPatientIDs() throws Exception;

    List<PatientDTO> searchPatient(String query) throws Exception;

}

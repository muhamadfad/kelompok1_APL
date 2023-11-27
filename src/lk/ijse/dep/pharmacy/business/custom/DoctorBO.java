package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.dto.PatientDTO;

import java.util.List;

public interface DoctorBO extends SuperBO {
    boolean saveDoctor(DoctorDTO doctor)throws Exception;

    boolean updateDoctor(DoctorDTO doctor)throws Exception;

    boolean deleteDoctor(String doctorId) throws Exception;

    List<DoctorDTO> findAllDoctors() throws Exception;

    String getLastDoctorId()throws Exception;

    DoctorDTO findDoctor(String doctorId) throws Exception;

    List<String> getAllDoctorIds() throws Exception;

    List<DoctorDTO> searchDoctor(String query) throws Exception;
}

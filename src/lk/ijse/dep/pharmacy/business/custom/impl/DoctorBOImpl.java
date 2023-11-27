package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.DoctorBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.DoctorDAO;
import lk.ijse.dep.pharmacy.dao.custom.PrescriptionDAO;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.entity.Doctor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class DoctorBOImpl implements DoctorBO {

    private DoctorDAO doctorDAO = DAOFactory.getInstance().getDAO(DAOTypes.DOCTOR);
    private PrescriptionDAO prescriptionDAO = DAOFactory.getInstance().getDAO(DAOTypes.PRESCRIPTION);


    @Override
    public boolean saveDoctor(DoctorDTO doctor) throws Exception {
        return doctorDAO.save(new Doctor(doctor.getId(), doctor.getNic(), doctor.getName(), doctor.getAddress(),
                doctor.getMobile(), doctor.getLand(), doctor.getEmail(), doctor.getSpecialization(), doctor.getRegHospital()));
    }

    @Override
    public boolean updateDoctor(DoctorDTO doctor) throws Exception {
        return doctorDAO.update(new Doctor(doctor.getId(), doctor.getNic(), doctor.getName(), doctor.getAddress(),
                doctor.getMobile(), doctor.getLand(), doctor.getEmail(), doctor.getSpecialization(), doctor.getRegHospital()));
    }

    @Override
    public boolean deleteDoctor(String doctorId) throws Exception {
        if (prescriptionDAO.existsByDoctorId(doctorId)) {
            throw new AlreadyExistsInOrderException("Doctor already exists in Prescription, hence unable to delete");
        }
        return doctorDAO.delete(doctorId);
    }

    @Override
    public List<DoctorDTO> findAllDoctors() throws Exception {
        List<Doctor> alDoctor = doctorDAO.findAll();
        List<DoctorDTO> dtos = new ArrayList<>();

        for (Doctor doctor : alDoctor) {
            dtos.add(new DoctorDTO(doctor.getDoctorId(), doctor.getDoctorNic(), doctor.getDoctorName(), doctor.getDoctorAddress(), doctor.getDoctorMobile(),
                    doctor.getDoctorLand(), doctor.getDoctorEmail(), doctor.getDoctorSpecialization(), doctor.getDoctorRegHospital()));
        }
        return dtos;
    }

    @Override
    public String getLastDoctorId() throws Exception {
        return doctorDAO.getLastDoctorId();
    }

    @Override
    public DoctorDTO findDoctor(String doctorId) throws Exception {
        Doctor doctor = doctorDAO.find(doctorId);
        return new DoctorDTO(doctor.getDoctorId(), doctor.getDoctorNic(), doctor.getDoctorName(), doctor.getDoctorAddress(), doctor.getDoctorMobile(),
                doctor.getDoctorLand(), doctor.getDoctorEmail(), doctor.getDoctorSpecialization(), doctor.getDoctorRegHospital());
    }

    @Override
    public List<String> getAllDoctorIds() throws Exception {
        List<Doctor> doctors = doctorDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Doctor doctor : doctors) {
            ids.add(doctor.getDoctorId());
        }
        return ids;
    }

    @Override
    public List<DoctorDTO> searchDoctor(String query) throws Exception {
        List<Doctor> search = doctorDAO.search(query + "%");
        List<DoctorDTO> dtos = new ArrayList<>();

        for (Doctor searchDoctors : search) {
            dtos.add(new DoctorDTO(searchDoctors.getDoctorId(), searchDoctors.getDoctorNic(), searchDoctors.getDoctorName(),
                    searchDoctors.getDoctorAddress(), searchDoctors.getDoctorMobile(), searchDoctors.getDoctorLand(), searchDoctors.getDoctorEmail(),
                    searchDoctors.getDoctorSpecialization(), searchDoctors.getDoctorRegHospital()));
        }
        return dtos;
    }
}

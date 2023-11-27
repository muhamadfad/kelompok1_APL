package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.PatientBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.PatientDAO;
import lk.ijse.dep.pharmacy.dao.custom.PrescriptionDAO;
import lk.ijse.dep.pharmacy.dto.PatientDTO;
import lk.ijse.dep.pharmacy.entity.Patient;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PatientBOImpl implements PatientBO {

    private PatientDAO patientDAO = DAOFactory.getInstance().getDAO(DAOTypes.PATIENT);
    private PrescriptionDAO prescriptionDAO = DAOFactory.getInstance().getDAO(DAOTypes.PRESCRIPTION);

    @Override
    public boolean savePatient(PatientDTO patient) throws Exception {
        return patientDAO.save(new Patient(patient.getId(), patient.getNic(), patient.getName(), patient.getAddress(),
                patient.getMobile(), patient.getLand(), patient.getEmail(), patient.getGender(), patient.getBirthDate(), patient.getAge()));
    }

    @Override
    public boolean updatePatient(PatientDTO patient) throws Exception {
        return patientDAO.update(new Patient(patient.getId(), patient.getNic(), patient.getName(), patient.getAddress(), patient.getMobile(),patient.getLand(),patient.getEmail(),patient.getGender(),patient.getBirthDate(),patient.getAge()));
    }

    @Override
    public boolean deletePatient(String patientId) throws Exception {
        if (prescriptionDAO.existsByPatientId(patientId)){
            throw new AlreadyExistsInOrderException("Patient already exists in an Prescription, hence unable to delete");
        }
        return patientDAO.delete(patientId);
    }

    @Override
    public List<PatientDTO> findAllPatients() throws Exception {
        List<Patient> alPatient = patientDAO.findAll();
        List<PatientDTO> dtos = new ArrayList<>();

        for (Patient patient : alPatient) {
            dtos.add(new PatientDTO(patient.getPatientId(),patient.getPatientNic(),patient.getPatientName(),patient.getPatientAddress(),patient.getPatientMobile(),patient.getPatientLand(),patient.getPatientEmail(),patient.getPatientGender(),patient.getPatientDOB(),patient.getPatientAge()));
        }
        return dtos;
    }

    @Override
    public String getLastPatientId() throws Exception {
        return patientDAO.getLastPatientId();
    }

    @Override
    public PatientDTO findPatient(String patientId) throws Exception {
        Patient patient = patientDAO.find(patientId);
        return new PatientDTO(patient.getPatientId(),
                patient.getPatientNic(), patient.getPatientName(),patient.getPatientAddress(),patient.getPatientMobile(),patient.getPatientLand(),patient.getPatientEmail(),patient.getPatientGender(),patient.getPatientDOB(),patient.getPatientAge());
    }

    @Override
    public List<String> getAllPatientIDs() throws Exception {
        List<Patient> patients = patientDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Patient patient : patients) {
            ids.add(patient.getPatientId());
        }
        return ids;
    }

    @Override
    public List<PatientDTO> searchPatient(String query) throws Exception {
        List<Patient> search = patientDAO.search(query + "%");
        List<PatientDTO> dtos = new ArrayList<>();

        for (Patient searchPatients : search) {
            dtos.add(new PatientDTO(searchPatients.getPatientId(), searchPatients.getPatientNic(), searchPatients.getPatientName(),
                    searchPatients.getPatientAddress(),searchPatients.getPatientMobile(),searchPatients.getPatientLand(),searchPatients.getPatientEmail(),
                    searchPatients.getPatientGender(),searchPatients.getPatientDOB(),searchPatients.getPatientAge()));
        }
        return dtos;
    }
}

package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;
import java.util.List;

public class PrescriptionDTO {
    private String id;
    private String patientId;
    private Date date;
    private String doctorId;
    private List<PrescriptionDetailDTO> prescriptionDetail;
    private PaymentDTO paymentDTO;

    public PrescriptionDTO(String id, String patientId, Date date, String doctorId, List<PrescriptionDetailDTO> prescriptionDetail) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.doctorId = doctorId;
        this.prescriptionDetail = prescriptionDetail;
    }

    public PrescriptionDTO(String id, String patientId, Date date, String doctorId) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.doctorId = doctorId;
    }

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(String id, String patientId, Date date, String doctorId, List<PrescriptionDetailDTO> prescriptionDetail, PaymentDTO paymentDTO) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.doctorId = doctorId;
        this.prescriptionDetail = prescriptionDetail;
        this.paymentDTO = paymentDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<PrescriptionDetailDTO> getPrescriptionDetail() {
        return prescriptionDetail;
    }

    public void setPrescriptionDetail(List<PrescriptionDetailDTO> prescriptionDetail) {
        this.prescriptionDetail = prescriptionDetail;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }
}

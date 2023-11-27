package lk.ijse.dep.pharmacy.dto;

import java.sql.Date;
import java.util.List;

public class PrescriptionDTO3 {
    private String id;
    private String patientId;
    private Date date;
    private String doctorId;
    private List<PrescriptionDetailDTO> prescriptionDetail;
    private List<StockDTO2> stockDetails;
    private PaymentDTO paymentDTO;

    public PrescriptionDTO3() {
    }

    public PrescriptionDTO3(String id, String patientId, Date date, String doctorId, List<PrescriptionDetailDTO> prescriptionDetail, List<StockDTO2> stockDetails, PaymentDTO paymentDTO) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.doctorId = doctorId;
        this.prescriptionDetail = prescriptionDetail;
        this.stockDetails = stockDetails;
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

    public List<StockDTO2> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(List<StockDTO2> stockDetails) {
        this.stockDetails = stockDetails;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }

    @Override
    public String toString() {
        return "PrescriptionDTO3{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", date=" + date +
                ", doctorId='" + doctorId + '\'' +
                ", prescriptionDetail=" + prescriptionDetail +
                ", stockDetails=" + stockDetails +
                ", paymentDTO=" + paymentDTO +
                '}';
    }
}

package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.*;

import java.util.List;

public interface PrescriptionBO extends SuperBO {
    String getLastPrescriptionId() throws Exception;

    boolean placeOrderPrescription(PrescriptionDTO3 prescription) throws Exception;

    List<PrescriptionDTO2> getPrescriptionInfo(String query) throws Exception;

    PrescriptionDTO findPrescription(String presId) throws Exception;

    List<PrescriptionDetailDTO> findPrescriptionDetails(String presId) throws Exception;
}

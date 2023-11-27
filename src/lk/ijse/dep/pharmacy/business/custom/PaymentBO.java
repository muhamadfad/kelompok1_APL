package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO;
import lk.ijse.dep.pharmacy.dto.DrugItemDTO2;
import lk.ijse.dep.pharmacy.dto.PaymentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDTO payment)throws Exception;

    boolean updatePayment(PaymentDTO payment)throws Exception;

    boolean deletePayment(String paymentId) throws Exception;

    List<PaymentDTO> findAllPayment() throws Exception;

    List<PaymentDTO> findAllOrderPayment() throws Exception;

    List<PaymentDTO> findAllPresPayment() throws Exception;

    String getLastPaymentId()throws Exception;

    PaymentDTO findPayment(String paymentId) throws Exception;

    List<String> getAllPaymentIds() throws Exception;

    List<PaymentDTO> searchPayment(String query) throws Exception;
}

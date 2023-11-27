package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.dao.SuperDAO;
import lk.ijse.dep.pharmacy.entity.Doctor;
import lk.ijse.dep.pharmacy.entity.Payment;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment, String> {
    String getLastPaymentId() throws Exception;

    List<Payment> findAllOrderPayments() throws Exception;

    List<Payment> findAllPresPayments() throws Exception;

}

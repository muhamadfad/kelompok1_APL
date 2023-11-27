package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.PaymentBO;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.PaymentDAO;
import lk.ijse.dep.pharmacy.dao.custom.SupplierDAO;
import lk.ijse.dep.pharmacy.dto.PaymentDTO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;
import lk.ijse.dep.pharmacy.entity.Payment;
import lk.ijse.dep.pharmacy.entity.Supplier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PaymentBOImpl implements PaymentBO {

    private PaymentDAO paymentDAO = DAOFactory.getInstance().getDAO(DAOTypes.PAYMENT);


    @Override
    public boolean savePayment(PaymentDTO payment) throws Exception {
        return paymentDAO.save(new Payment(payment.getId(), payment.getDate(), payment.getType(),
                payment.getPresId(), payment.getOrderId(), payment.getTotal()));

    }

    @Override
    public boolean updatePayment(PaymentDTO payment) throws Exception {
        return paymentDAO.update(new Payment(payment.getId(), payment.getDate(), payment.getType(),
                payment.getPresId(), payment.getOrderId(), payment.getTotal()));

    }

    @Override
    public boolean deletePayment(String paymentId) throws Exception {
        //        if (orderDAO.existsByCustomerId(customerId)){
//            throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
//        }
        return paymentDAO.delete(paymentId);
    }

    @Override
    public List<PaymentDTO> findAllPayment() throws Exception {
        List<Payment> alPayment = paymentDAO.findAll();
        List<PaymentDTO> dtos = new ArrayList<>();

        for (Payment payment : alPayment) {
            dtos.add(new PaymentDTO(payment.getPaymentId(),payment.getPaymentDate(),payment.getPaymentType(),payment.getPrescriptionId(),payment.getOrderId(),
                    payment.getTotalAmount()));
        }
        return dtos;
    }

    @Override
    public List<PaymentDTO> findAllOrderPayment() throws Exception {
        List<Payment> alPayment = paymentDAO.findAllOrderPayments();
        List<PaymentDTO> dtos = new ArrayList<>();

        for (Payment payment : alPayment) {
            dtos.add(new PaymentDTO(payment.getPaymentId(),payment.getPaymentDate(),payment.getPaymentType(),payment.getPrescriptionId(),payment.getOrderId(),
                    payment.getTotalAmount()));
        }
        return dtos;
    }

    @Override
    public List<PaymentDTO> findAllPresPayment() throws Exception {
        List<Payment> alPayment = paymentDAO.findAllPresPayments();
        List<PaymentDTO> dtos = new ArrayList<>();

        for (Payment payment : alPayment) {
            dtos.add(new PaymentDTO(payment.getPaymentId(),payment.getPaymentDate(),payment.getPaymentType(),payment.getPrescriptionId(),payment.getOrderId(),
                    payment.getTotalAmount()));
        }
        return dtos;
    }

    @Override
    public String getLastPaymentId() throws Exception {
        return paymentDAO.getLastPaymentId();
    }

    @Override
    public PaymentDTO findPayment(String paymentId) throws Exception {
        Payment payment = paymentDAO.find(paymentId);
        return new PaymentDTO(payment.getPaymentId(),payment.getPaymentDate(),payment.getPaymentType(),payment.getPrescriptionId(),payment.getOrderId(),
                payment.getTotalAmount());

    }

    @Override
    public List<String> getAllPaymentIds() throws Exception {
        List<Payment> payments = paymentDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Payment payment : payments) {
            ids.add(payment.getPaymentId());
        }
        return ids;
    }

    @Override
    public List<PaymentDTO> searchPayment(String query) throws Exception {
        List<Payment> search = paymentDAO.search(query + "%");
        List<PaymentDTO> dtos = new ArrayList<>();

        for (Payment payment : search) {
            dtos.add(new PaymentDTO(payment.getPaymentId(),payment.getPaymentDate(),payment.getPaymentType(),payment.getPrescriptionId(),payment.getOrderId(),
                    payment.getTotalAmount()));
        }
        return dtos;
    }
}

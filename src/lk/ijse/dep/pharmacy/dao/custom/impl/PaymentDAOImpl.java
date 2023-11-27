package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.PaymentDAO;
import lk.ijse.dep.pharmacy.entity.Payment;
import lk.ijse.dep.pharmacy.entity.Supplier;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public String getLastPaymentId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT paymentId FROM Payment ORDER BY paymentId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Payment> findAllOrderPayments() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Payment WHERE orderId LIKE '%ODR%'");
        List<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            ));
        }
        return payments;
    }

    @Override
    public List<Payment> findAllPresPayments() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Payment WHERE prescriptionId LIKE '%PRES%'");
        List<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            ));
        }
        return payments;
    }

    @Override
    public List<Payment> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Payment");
        List<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            ));
        }
        return payments;
    }

    @Override
    public Payment find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Payment WHERE paymentId=?", s);
        if (rst.next()) {
            return new Payment(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            );
        }
        return null;
    }

    @Override
    public boolean save(Payment payment) throws Exception {
        return CrudUtil.execute("INSERT INTO Payment VALUES (?,?,?,?,?,?)",
                payment.getPaymentId(), payment.getPaymentDate(), payment.getPaymentType(),payment.getPrescriptionId(), payment.getOrderId(), payment.getTotalAmount());

    }

    @Override
    public boolean update(Payment payment) throws Exception {
        return CrudUtil.execute("UPDATE Payment SET paymentDate=?, paymentType=?,prescriptionId=?,orderId=?,totalAmount=? WHERE paymentId=?",
                payment.getPaymentDate(), payment.getPaymentType(), payment.getPrescriptionId(), payment.getOrderId(), payment.getTotalAmount(), payment.getPaymentId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Payment WHERE paymentId=?", s);
    }

    @Override
    public List<Payment> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Payment " +
                        "WHERE paymentId LIKE ? OR paymentDate LIKE ? OR paymentType LIKE ? OR prescriptionId LIKE ? OR orderId LIKE ? OR totalAmount LIKE ?",
                query,query,query,query,query,query);
        List<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            payments.add(new Payment(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            ));
        }
        return payments;
    }
}

package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.Order;

public interface OrderDAO extends CrudDAO<Order, String> {
    String getLastOrderId() throws Exception;

    boolean existsBySupplierId(String supplierId) throws Exception;
}

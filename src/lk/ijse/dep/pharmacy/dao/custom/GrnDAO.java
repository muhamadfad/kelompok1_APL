package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.Grn;

public interface GrnDAO extends CrudDAO<Grn, String> {
    String getLastGrnId() throws Exception;

    boolean existsByOrderId(String orderId) throws Exception;
}

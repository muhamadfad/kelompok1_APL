package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.OrderDetail;
import lk.ijse.dep.pharmacy.entity.OrderDetailPK;

import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail, OrderDetailPK> {
    boolean existsByDrugCode(String drugCode) throws Exception;

    List<OrderDetail> findOrderDetailsByOrderId(String orderId) throws Exception;

}

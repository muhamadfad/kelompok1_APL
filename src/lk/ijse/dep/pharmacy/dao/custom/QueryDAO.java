package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.SuperDAO;
import lk.ijse.dep.pharmacy.entity.CustomEntity;
import lk.ijse.dep.pharmacy.entity.CustomEntity2;
import lk.ijse.dep.pharmacy.entity.CustomEntity4;
import lk.ijse.dep.pharmacy.entity.CustomEntity5;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    CustomEntity getOrderInfo(String orderId) throws Exception;

    CustomEntity getOrderInfo2(String orderId) throws Exception;

    /**
     *
     * @param query customerId, customerName, orderId, orderDate
     * @return
     * @throws Exception
     */
    List<CustomEntity> getOrdersInfo(String query) throws Exception;

    List<CustomEntity2> getPrescriptionsInfo(String query) throws Exception;

    List<CustomEntity4> getGrnInfo(String query) throws  Exception;

    List<CustomEntity5> getGrnDetailInfo(String grnId) throws Exception;

}

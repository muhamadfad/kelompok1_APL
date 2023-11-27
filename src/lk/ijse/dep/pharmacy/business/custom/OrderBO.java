package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.dto.OrderDTO;
import lk.ijse.dep.pharmacy.dto.OrderDTO2;
import lk.ijse.dep.pharmacy.dto.OrderDetailDTO;
import lk.ijse.dep.pharmacy.entity.CustomEntity;

import java.util.List;

public interface OrderBO extends SuperBO {
    String getLastOrderId() throws Exception;

    boolean placeOrder(OrderDTO order) throws Exception;

    List<OrderDTO2> getOrderInfo(String query) throws Exception;

    OrderDTO findOrder(String orderId) throws Exception;

    List<OrderDetailDTO> findOrderDetails(String orderId) throws Exception;

}

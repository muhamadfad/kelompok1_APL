package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.OrderBO;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.*;
import lk.ijse.dep.pharmacy.db.DBConnection;
import lk.ijse.dep.pharmacy.dto.*;
import lk.ijse.dep.pharmacy.entity.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("Duplicates")
public class OrderBOImpl implements OrderBO {

    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private StockDAO stockDAO = DAOFactory.getInstance().getDAO(DAOTypes.STOCK);
    private BatchDAO batchDAO = DAOFactory.getInstance().getDAO(DAOTypes.BATCH);
    private DrugItemDAO drugItemDAO = DAOFactory.getInstance().getDAO(DAOTypes.DRUG_ITEM);
    private GrnDAO grnDAO = DAOFactory.getInstance().getDAO(DAOTypes.GRN);
    private GrnDetailDAO grnDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.GRN_DETAIL);
    private PaymentDAO paymentDAO = DAOFactory.getInstance().getDAO(DAOTypes.PAYMENT);
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);


    @Override
    public String getLastOrderId() throws Exception {
        return orderDAO.getLastOrderId();
    }

    @Override
    public boolean placeOrder(OrderDTO order) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            // Let's start a transaction
            connection.setAutoCommit(false);

            String oId = order.getId();
            boolean result = orderDAO.save(new Order(oId, new java.sql.Date(new Date().getTime()),
                    order.getSupId()));

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something, something went wrong");
            }

            String grnId = order.getGrnId();
            result = grnDAO.save(new Grn(grnId,oId));

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something, something went wrong");
            }

            for (GrnDetailDTO grnDetail : order.getGrnDetail()) {
                result = grnDetailDAO.save(new GrnDetail(grnId,grnDetail.getCode(),grnDetail.getQty(),grnDetail.getUnitPrice()));

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }
            }


            String bId = order.getBatchId();
            for (StockDTO stock : order.getStock()) {
                result = stockDAO.save(new Stock(bId, stock.getCode(), stock.getQty(), stock.getUnitPrice()));

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }


                Batch batch = batchDAO.find(bId);
                result = batchDAO.update(new Batch(bId,batch.getBatchDes(),batch.getBatchExpireDate(),false));

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }
            }

            for (OrderDetailDTO orderDetail : order.getOrderDetail()) {
                result = orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(),
                        orderDetail.getQty(), orderDetail.getUnitPrice()));

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }

//                DrugItem drugItem = drugItemDAO.find(orderDetail.getCode());
//                result = drugItemDAO.update(new DrugItem(drugItem.getDrugCode(), drugItem.getDrugDes(), drugItem.getDrugManufactureDate(), drugItem.getDrugExpireDate(),
//                        orderDetail.getQty(), orderDetail.getUnitPrice(), drugItem.getLocationId()));
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }

//                result = grnDetailDAO.save(new GrnDetail(,orderDetail.getCode(),orderDetail.getQty(),orderDetail.getUnitPrice()));
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }
                DrugItem item = drugItemDAO.find(orderDetail.getCode());
                item.setDrugUnitPrice(orderDetail.getUnitPrice());
                item.setDrugQtyOnHand(item.getDrugQtyOnHand() + orderDetail.getQty());
                result = drugItemDAO.update(item);

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something, something went wrong");
                }
            }

            result = paymentDAO.save(new Payment(order.getPaymentDTO().getId(),order.getPaymentDTO().getDate(),
                    order.getPaymentDTO().getType(),order.getPaymentDTO().getPresId(),oId,order.getPaymentDTO().getTotal()));

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something, something went wrong");
            }

            connection.commit();
            return true;

        } catch (Throwable e) {

            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<OrderDTO2> getOrderInfo(String query) throws Exception {
        List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo(query + "%");
        List<OrderDTO2> dtos = new ArrayList<>();

        for (CustomEntity info : ordersInfo) {
            dtos.add(new OrderDTO2(info.getOrderId(), info.getSupplierId(), info.getSupplierName(),
                    info.getOrderDate(),info.getTotal()));
        }
        return dtos;
    }

    @Override
    public OrderDTO findOrder(String orderId) throws Exception {
        Order order = orderDAO.find(orderId);
        return new OrderDTO(order.getOrderId(), order.getOrderDate(), order.getSupplierId());
    }

    @Override
    public List<OrderDetailDTO> findOrderDetails(String orderId) throws Exception {
        List<OrderDetail> orderDetails = orderDetailDAO.findOrderDetailsByOrderId(orderId);
        List<OrderDetailDTO> dtos = new ArrayList<>();
        for (OrderDetail info : orderDetails) {
            dtos.add(new OrderDetailDTO(info.getOrderDetailPK().getDrugCode(), info.getQuantity(), info.getUnitPrice()));
        }
        return dtos;
    }
}

package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pharmacy.entity.OrderDetail;
import lk.ijse.dep.pharmacy.entity.OrderDetailPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean existsByDrugCode(String drugCode) throws Exception {
        ResultSet rst =  CrudUtil.execute("SELECT * FROM OrderDetails WHERE drugCode=?",drugCode);
        return rst.next();
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(String orderId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM OrderDetails WHERE orderId=?", orderId);
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (rst.next()){
            orderDetails.add(new OrderDetail(rst.getString(1),
                     rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)));
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetail> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM OrderDetails");
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (rst.next()){
            orderDetails.add(new OrderDetail(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)));
        }
        return orderDetails;
    }

    @Override
    public OrderDetail find(OrderDetailPK orderDetailPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM OrderDetails WHERE orderId=? AND drugCode=?", orderDetailPK.getOrderId(), orderDetailPK.getDrugCode());
        if (rst.next()){
            return new OrderDetail(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4));
        }
        return null;
    }

    @Override
    public boolean save(OrderDetail orderDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO OrderDetails VALUES (?,?,?,?)",
                orderDetail.getOrderDetailPK().getOrderId(), orderDetail.getOrderDetailPK().getDrugCode(), orderDetail.getQuantity(), orderDetail.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws Exception {
        return CrudUtil.execute("UPDATE OrderDetails SET quantity=?, unitPrice=?  WHERE orderId=? AND drugCode=?",
                orderDetail.getQuantity(), orderDetail.getUnitPrice(), orderDetail.getOrderDetailPK().getOrderId(), orderDetail.getOrderDetailPK().getDrugCode());
    }

    @Override
    public boolean delete(OrderDetailPK orderDetailPK) throws Exception {
        return CrudUtil.execute("DELETE FROM OrderDetails WHERE orderId=? AND drugCode=?",
                orderDetailPK.getOrderId(), orderDetailPK.getDrugCode());
    }

    @Override
    public List<OrderDetail> search(OrderDetailPK orderDetailPK) throws Exception {
        return null;
    }
}

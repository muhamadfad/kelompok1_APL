package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.OrderDAO;
import lk.ijse.dep.pharmacy.entity.DrugItem;
import lk.ijse.dep.pharmacy.entity.Order;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class OrderDAOImpl implements OrderDAO {
    @Override
    public String getLastOrderId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public boolean existsBySupplierId(String supplierId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM `Order` WHERE supplierId=?", supplierId);
        return rst.next();
    }

    @Override
    public List<Order> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM `Order`");
        List<Order> orders = new ArrayList<>();
        while (rst.next()){
            orders.add(new Order(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3)));
        }
        return orders;
    }

    @Override
    public Order find(String s) throws Exception {
        ResultSet rst =CrudUtil.execute("SELECT * FROM `Order` WHERE orderId=?", s);
        if (rst.next()){
            return new Order(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3));
        }
        return null;
    }

    @Override
    public boolean save(Order order) throws Exception {
        return CrudUtil.execute("INSERT INTO `Order` VALUES (?,?,?)", order.getOrderId(), order.getOrderDate(), order.getSupplierId());
    }

    @Override
    public boolean update(Order order) throws Exception {
        return CrudUtil.execute("UPDATE `Order` SET orderDate=?, supplierId=? WHERE orderId=?", order.getOrderDate(), order.getSupplierId(), order.getOrderId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM `Order` WHERE orderId=?", s);
    }

    @Override
    public List<Order> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM `Order` " +
                        "WHERE orderId LIKE ? OR orderDate LIKE ? OR supplierId LIKE ?",
                query,query,query);
        List<Order> order = new ArrayList<>();
        while (rst.next()) {
            order.add(new Order(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3)
            ));
        }
        return order;
    }
}

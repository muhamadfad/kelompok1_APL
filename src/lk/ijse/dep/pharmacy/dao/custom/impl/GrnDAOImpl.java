package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.GrnDAO;
import lk.ijse.dep.pharmacy.entity.Grn;
import lk.ijse.dep.pharmacy.entity.Order;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class GrnDAOImpl implements GrnDAO {
    @Override
    public String getLastGrnId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT grnId FROM Grn ORDER BY grnId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public boolean existsByOrderId(String orderId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Grn WHERE orderId=?", orderId);
        return rst.next();
    }

    @Override
    public List<Grn> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Grn");
        List<Grn> grns = new ArrayList<>();
        while (rst.next()){
            grns.add(new Grn(rst.getString(1),rst.getString(2)));
        }
        return grns;
    }

    @Override
    public Grn find(String s) throws Exception {
        ResultSet rst =CrudUtil.execute("SELECT * FROM Grn WHERE orderId=?", s);
        if (rst.next()){
            return new Grn(rst.getString(1), rst.getString(2));
        }
        return null;
    }

    @Override
    public boolean save(Grn grn) throws Exception {
        return CrudUtil.execute("INSERT INTO Grn VALUES (?,?)", grn.getGrnId(),grn.getOrderId());
    }

    @Override
    public boolean update(Grn grn) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Grn WHERE grnId=?", s);
    }

    @Override
    public List<Grn> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Grn " +
                        "WHERE grnId LIKE ?",
                query);
        List<Grn> grn = new ArrayList<>();
        while (rst.next()) {
            grn.add(new Grn(rst.getString(1),
                    rst.getString(2)
            ));
        }
        return grn;
    }
}

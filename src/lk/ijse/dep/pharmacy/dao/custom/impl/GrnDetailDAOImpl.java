package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.GrnDetailDAO;
import lk.ijse.dep.pharmacy.entity.GrnDetail;
import lk.ijse.dep.pharmacy.entity.GrnDetailPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GrnDetailDAOImpl implements GrnDetailDAO {
    @Override
    public boolean existsByDrugCode(String drugCode) throws Exception {
        ResultSet rst =  CrudUtil.execute("SELECT * FROM GrnDetails WHERE drugCode=?",drugCode);
        return rst.next();
    }

    @Override
    public List<GrnDetail> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM OrderDetails");
        List<GrnDetail> grnDetails = new ArrayList<>();
        while (rst.next()){
            grnDetails.add(new GrnDetail(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)));
        }
        return grnDetails;
    }

    @Override
    public GrnDetail find(GrnDetailPK grnDetailPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM GrnDetails WHERE grnId=? AND drugCode=?",  grnDetailPK.getGrnId(),grnDetailPK.getDrugCode());
        if (rst.next()){
            return new GrnDetail(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4));
        }
        return null;
    }

    @Override
    public boolean save(GrnDetail grnDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO GrnDetails VALUES (?,?,?,?)",
                 grnDetail.getGrnDetailPK().getGrnId(),grnDetail.getGrnDetailPK().getDrugCode(), grnDetail.getQty(), grnDetail.getUnitPrice());
    }

    @Override
    public boolean update(GrnDetail grnDetail) throws Exception {
        return CrudUtil.execute("UPDATE GrnDetails SET qty=?, unitPrice=?  WHERE grnId=? AND drugCode=?",
                grnDetail.getQty(), grnDetail.getUnitPrice(), grnDetail.getGrnDetailPK().getGrnId(),grnDetail.getGrnDetailPK().getDrugCode());
    }

    @Override
    public boolean delete(GrnDetailPK grnDetailPK) throws Exception {
        return CrudUtil.execute("DELETE FROM GrnDetails WHERE grnId=? AND drugCode=?",
                grnDetailPK.getGrnId(),grnDetailPK.getDrugCode());
    }

    @Override
    public List<GrnDetail> search(GrnDetailPK query) throws Exception {
        return null;
    }
}

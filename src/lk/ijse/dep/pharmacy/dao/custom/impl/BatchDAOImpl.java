package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.BatchDAO;
import lk.ijse.dep.pharmacy.entity.Batch;
import lk.ijse.dep.pharmacy.entity.Location;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class BatchDAOImpl implements BatchDAO {
    @Override
    public String getLastBatchId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT batchId FROM Batch ORDER BY batchId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public List<Batch> findAvailableBatches() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Batch WHERE batchStatus=TRUE");
        List<Batch> batches = new ArrayList<>();
        while (rst.next()) {
            batches.add(new Batch(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getBoolean(4)
            ));
        }
        return batches;
    }

    @Override
    public List<Batch> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Batch");
        List<Batch> batches = new ArrayList<>();
        while (rst.next()) {
            batches.add(new Batch(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getBoolean(4)
            ));
        }
        return batches;
    }

    @Override
    public Batch find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Batch WHERE batchId=?", s);
        if (rst.next()) {
            return new Batch(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getBoolean(4)
            );
        }
        return null;
    }

    @Override
    public boolean save(Batch entity) throws Exception {
        return CrudUtil.execute("INSERT INTO Batch VALUES (?,?,?,?)",
                entity.getBatchId(), entity.getBatchDes(), entity.getBatchExpireDate(),entity.isBatchStatus());
    }

    @Override
    public boolean update(Batch entity) throws Exception {
        return CrudUtil.execute("UPDATE Batch SET batchDes=?, batchExpireDate=?,batchStatus=? WHERE batchId=?",
                entity.getBatchDes(), entity.getBatchExpireDate(),entity.isBatchStatus(),entity.getBatchId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Batch WHERE batchId=?", s);
    }

    @Override
    public List<Batch> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Batch " +
                        "WHERE batchId LIKE ? OR batchDes LIKE ? OR batchExpireDate LIKE ? OR batchStatus LIKE ?",
                query,query,query,query);
        List<Batch> batches = new ArrayList<>();
        while (rst.next()) {
            batches.add(new Batch(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getBoolean(4)
            ));
        }
        return batches;
    }
}

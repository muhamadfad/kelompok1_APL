package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.StockDAO;
import lk.ijse.dep.pharmacy.entity.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockDAOImpl implements StockDAO {
    @Override
    public boolean existsByDrugCode(String drugCode) throws Exception {
        ResultSet rst =  CrudUtil.execute("SELECT * FROM Stock WHERE drugCode=?",drugCode);
        return rst.next();
    }

    @Override
    public boolean existsByBatchId(String batchId) throws Exception {
        ResultSet rst =  CrudUtil.execute("SELECT * FROM Stock WHERE batchId=?",batchId);
        return rst.next();
    }

    @Override
    public List<CustomEntity3> getStockInfo(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Stock " +
                        "WHERE batchId LIKE ? OR drugCode LIKE ? OR drugQty LIKE ? OR drugUnitPrice LIKE ?",
                query,query,query,query);
        List<CustomEntity3> stocks = new ArrayList<>();
        while (rst.next()) {
            stocks.add(new CustomEntity3(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));
        }
        return stocks;

    }

    @Override
    public List<Stock> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Stock");
        List<Stock> stocks = new ArrayList<>();
        while (rst.next()){
            stocks.add(new Stock(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)));
        }
        return stocks;
    }

    @Override
    public Stock find(StockPK stockPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Stock WHERE batchId=? AND drugCode=?", stockPK.getBatchId(), stockPK.getDrugCode());
        if (rst.next()){
            return new Stock(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4));
        }
        return null;
    }

    @Override
    public boolean save(Stock stock) throws Exception {
        return CrudUtil.execute("INSERT INTO Stock VALUES (?,?,?,?)",
                stock.getStockPK().getBatchId(), stock.getStockPK().getDrugCode(), stock.getDrugQty(), stock.getDrugUnitPrice());
    }

    @Override
    public boolean update(Stock stock) throws Exception {
        return CrudUtil.execute("UPDATE Stock SET drugQty=?, drugUnitPrice=?  WHERE batchId=? AND drugCode=?",
                stock.getDrugQty(), stock.getDrugUnitPrice(), stock.getStockPK().getBatchId(), stock.getStockPK().getDrugCode());
    }

    @Override
    public boolean delete(StockPK stockPK) throws Exception {
        return CrudUtil.execute("DELETE FROM Stock WHERE batchId=? AND drugCode=?",
                stockPK.getBatchId(), stockPK.getDrugCode());
    }

    @Override
    public List<Stock> search(StockPK query) throws Exception {
        return null;
    }
}

package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.CustomEntity3;
import lk.ijse.dep.pharmacy.entity.Stock;
import lk.ijse.dep.pharmacy.entity.StockPK;

import java.util.List;

public interface StockDAO extends CrudDAO<Stock, StockPK> {
    boolean existsByDrugCode(String drugCode) throws Exception;

    boolean existsByBatchId(String batchId) throws Exception;

    List<CustomEntity3> getStockInfo(String query) throws Exception;
}

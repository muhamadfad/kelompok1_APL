package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.StockDTO2;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;

import java.util.List;

public interface StockBO extends SuperBO {
    boolean updateStock(StockDTO2 stock)throws Exception;

//    boolean deleteStock(String stockId) throws Exception;
//
//    List<StockDTO2> findAllStock() throws Exception;
//
//    StockDTO2 findStock(String stockId) throws Exception;

    List<StockDTO2> searchStock(String query) throws Exception;
}

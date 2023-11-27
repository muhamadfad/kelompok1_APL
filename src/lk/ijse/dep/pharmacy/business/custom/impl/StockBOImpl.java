package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.StockBO;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.StockDAO;
import lk.ijse.dep.pharmacy.dto.DoctorDTO;
import lk.ijse.dep.pharmacy.dto.StockDTO2;
import lk.ijse.dep.pharmacy.entity.CustomEntity3;
import lk.ijse.dep.pharmacy.entity.Doctor;
import lk.ijse.dep.pharmacy.entity.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockBOImpl implements StockBO {

    private StockDAO stockDAO = DAOFactory.getInstance().getDAO(DAOTypes.STOCK);

    @Override
    public boolean updateStock(StockDTO2 stock) throws Exception {
        return stockDAO.update(new Stock(stock.getId(), stock.getCode(), stock.getQty(), stock.getUnitPrice()));

    }

    @Override
    public List<StockDTO2> searchStock(String query) throws Exception {
        List<CustomEntity3> search = stockDAO.getStockInfo(query + "%");
        List<StockDTO2> dtos = new ArrayList<>();

        for (CustomEntity3 stock : search) {
            dtos.add(new StockDTO2(stock.getBatchId(), stock.getDrugCode(), stock.getDrugQty(),
                    stock.getDrugUnitPrice()));
        }
        return dtos;
    }
}

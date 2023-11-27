package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.DrugItem;

public interface DrugItemDAO extends CrudDAO<DrugItem, String> {

    boolean existsByLocationId(String locationId) throws Exception;


    String getLastDrugCode() throws Exception;

}

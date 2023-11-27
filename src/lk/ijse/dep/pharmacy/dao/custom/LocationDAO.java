package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.dao.SuperDAO;
import lk.ijse.dep.pharmacy.entity.DrugItem;
import lk.ijse.dep.pharmacy.entity.Location;

import java.util.List;

public interface LocationDAO extends CrudDAO<Location, String> {
    String getLastLocation() throws Exception;

    List<Location> findAvailableLocations() throws Exception;
}

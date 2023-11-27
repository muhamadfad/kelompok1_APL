package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.Batch;
import lk.ijse.dep.pharmacy.entity.Location;

import java.util.List;

public interface BatchDAO extends CrudDAO<Batch, String> {
    String getLastBatchId() throws Exception;

    List<Batch> findAvailableBatches() throws Exception;


}

package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.Supplier;

public interface SupplierDAO extends CrudDAO<Supplier, String> {
    String getLastSupplierId() throws Exception;

}

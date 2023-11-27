package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;

import java.util.List;

public interface SupplierBO extends SuperBO {
    boolean saveSupplier(SupplierDTO supplier)throws Exception;

    boolean updateSupplier(SupplierDTO supplier)throws Exception;

    boolean deleteSupplier(String supplierId) throws Exception;

    List<SupplierDTO> findAllSuppliers() throws Exception;

    String getLastSupplierId()throws Exception;

    SupplierDTO findSupplier(String supplierId) throws Exception;

    List<String> getAllSupplierIds() throws Exception;

    List<String> getAllSupplierNames() throws Exception;

    List<SupplierDTO> searchSupplier(String query) throws Exception;
}

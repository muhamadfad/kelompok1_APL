package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.SupplierBO;
import lk.ijse.dep.pharmacy.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.OrderDAO;
import lk.ijse.dep.pharmacy.dao.custom.SupplierDAO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;
import lk.ijse.dep.pharmacy.entity.Supplier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SupplierBOImpl implements SupplierBO {

    private SupplierDAO supplierDAO = DAOFactory.getInstance().getDAO(DAOTypes.SUPPLIER);
    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);


    @Override
    public boolean saveSupplier(SupplierDTO supplier) throws Exception {
        return supplierDAO.save(new Supplier(supplier.getId(), supplier.getName(), supplier.getAddress(),
                supplier.getMobile(), supplier.getLand(), supplier.getEmail(), supplier.getVehicalNo()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) throws Exception {
        return supplierDAO.update(new Supplier(supplier.getId(), supplier.getName(), supplier.getAddress(),
                supplier.getMobile(), supplier.getLand(), supplier.getEmail(), supplier.getVehicalNo()));
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws Exception {
        if (orderDAO.existsBySupplierId(supplierId)) {
            throw new AlreadyExistsInOrderException("Supplier already exists in an order, hence unable to delete");
        }
        return supplierDAO.delete(supplierId);
    }

    @Override
    public List<SupplierDTO> findAllSuppliers() throws Exception {
        List<Supplier> alSupplier = supplierDAO.findAll();
        List<SupplierDTO> dtos = new ArrayList<>();

        for (Supplier supplier : alSupplier) {
            dtos.add(new SupplierDTO(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierMobile(), supplier.getSupplierLand(),
                    supplier.getSupplierEmail(), supplier.getSupplierVehicleNumber()));
        }
        return dtos;
    }

    @Override
    public String getLastSupplierId() throws Exception {
        return supplierDAO.getLastSupplierId();
    }

    @Override
    public SupplierDTO findSupplier(String supplierId) throws Exception {
        Supplier supplier = supplierDAO.find(supplierId);
        return new SupplierDTO(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierMobile(), supplier.getSupplierLand(),
                supplier.getSupplierEmail(), supplier.getSupplierVehicleNumber());
    }

    @Override
    public List<String> getAllSupplierIds() throws Exception {
        List<Supplier> suppliers = supplierDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            ids.add(supplier.getSupplierId());
        }
        return ids;
    }

    @Override
    public List<String> getAllSupplierNames() throws Exception {
        List<Supplier> suppliers = supplierDAO.findAll();
        List<String> names = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            names.add(supplier.getSupplierName());
        }
        return names;
    }

    @Override
    public List<SupplierDTO> searchSupplier(String query) throws Exception {
        List<Supplier> search = supplierDAO.search(query + "%");
        List<SupplierDTO> dtos = new ArrayList<>();

        for (Supplier searchSupplier : search) {
            dtos.add(new SupplierDTO(searchSupplier.getSupplierId(), searchSupplier.getSupplierName(), searchSupplier.getSupplierAddress(),
                    searchSupplier.getSupplierMobile(), searchSupplier.getSupplierLand(), searchSupplier.getSupplierEmail(), searchSupplier.getSupplierVehicleNumber()));
        }
        return dtos;
    }
}

package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.SupplierDAO;
import lk.ijse.dep.pharmacy.entity.Doctor;
import lk.ijse.dep.pharmacy.entity.Supplier;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public String getLastSupplierId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT supplierId FROM Supplier ORDER BY supplierId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Supplier> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");
        List<Supplier> suppliers = new ArrayList<>();
        while (rst.next()) {
            suppliers.add(new Supplier(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return suppliers;
    }

    @Override
    public Supplier find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier WHERE supplierId=?", s);
        if (rst.next()) {
            return new Supplier(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }
        return null;
    }

    @Override
    public boolean save(Supplier entity) throws Exception {
        return CrudUtil.execute("INSERT INTO Supplier VALUES (?,?,?,?,?,?,?)",
                entity.getSupplierId(), entity.getSupplierName(), entity.getSupplierAddress(),entity.getSupplierMobile(), entity.getSupplierLand(), entity.getSupplierEmail(), entity.getSupplierVehicleNumber());
    }

    @Override
    public boolean update(Supplier supplier) throws Exception {
        return CrudUtil.execute("UPDATE Supplier SET supplierName=?, supplierAddress=?,supplierMobile=?,supplierLand=?,supplierEmail=?,supplierVehicleNumber=? WHERE supplierId=?",
                supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierMobile(), supplier.getSupplierLand(), supplier.getSupplierEmail(), supplier.getSupplierVehicleNumber(),supplier.getSupplierId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Supplier WHERE supplierId=?", s);
    }

    @Override
    public List<Supplier> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier " +
                        "WHERE supplierId LIKE ? OR supplierName LIKE ? OR supplierAddress LIKE ? OR supplierMobile LIKE ? OR supplierLand LIKE ? OR supplierEmail LIKE ? OR supplierVehicleNumber LIKE ?",
                query,query,query,query,query,query,query);
        List<Supplier> suppliers = new ArrayList<>();
        while (rst.next()) {
            suppliers.add(new Supplier(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return suppliers;
    }
}

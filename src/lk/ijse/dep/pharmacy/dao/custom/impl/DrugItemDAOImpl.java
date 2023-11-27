package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.DrugItemDAO;
import lk.ijse.dep.pharmacy.entity.DrugItem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class DrugItemDAOImpl implements DrugItemDAO {
    @Override
    public boolean existsByLocationId(String locationId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM DrugItem WHERE locationId=?", locationId);
        return rst.next();
    }

    @Override
    public String getLastDrugCode() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT drugCode FROM DrugItem ORDER BY drugCode DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public List<DrugItem> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM DrugItem");
        List<DrugItem> drugItems = new ArrayList<>();
        while (rst.next()) {
            drugItems.add(new DrugItem(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7)
            ));
        }
        return drugItems;
    }

    @Override
    public DrugItem find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM DrugItem WHERE drugCode=?", s);
        if (rst.next()) {
            return new DrugItem(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7)
            );
        }
        return null;
    }

    @Override
    public boolean save(DrugItem entity) throws Exception {
        return CrudUtil.execute("INSERT INTO DrugItem VALUES (?,?,?,?,?,?,?)",
                entity.getDrugCode(), entity.getDrugDes(), entity.getDrugManufactureDate(), entity.getDrugExpireDate(), entity.getDrugQtyOnHand(), entity.getDrugQtyOnHand(), entity.getLocationId());
    }

    @Override
    public boolean update(DrugItem drugItem) throws Exception {
        return CrudUtil.execute("UPDATE DrugItem SET drugDes=?, drugManufactureDate=?,drugExpireDate=?,drugQtyOnHand=?,drugUnitPrice=?,locationId=? WHERE drugCode=?",
                drugItem.getDrugDes(), drugItem.getDrugManufactureDate(), drugItem.getDrugExpireDate(), drugItem.getDrugQtyOnHand(), drugItem.getDrugUnitPrice(), drugItem.getLocationId(), drugItem.getDrugCode());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM DrugItem WHERE drugCode=?", s);
    }

    @Override
    public List<DrugItem> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM DrugItem " +
                        "WHERE drugCode LIKE ? OR drugDes LIKE ? OR drugManufactureDate LIKE ? OR drugExpireDate LIKE ? OR drugQtyOnHand LIKE ? OR drugUnitPrice LIKE ? OR locationId LIKE ?",
                query, query, query, query, query, query, query);
        List<DrugItem> drugItems = new ArrayList<>();
        while (rst.next()) {
            drugItems.add(new DrugItem(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7)
            ));
        }
        return drugItems;
    }
}

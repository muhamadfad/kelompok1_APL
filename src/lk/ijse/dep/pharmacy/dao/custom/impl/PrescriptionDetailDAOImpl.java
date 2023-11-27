package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.PrescriptionDAO;
import lk.ijse.dep.pharmacy.dao.custom.PrescriptionDetailDAO;
import lk.ijse.dep.pharmacy.entity.OrderDetail;
import lk.ijse.dep.pharmacy.entity.PrescriptionDetail;
import lk.ijse.dep.pharmacy.entity.PrescriptionDetailPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PrescriptionDetailDAOImpl implements PrescriptionDetailDAO {
    @Override
    public boolean existsByDrugCode(String drugCode) throws Exception {
        ResultSet rst =  CrudUtil.execute("SELECT * FROM PrescriptionDetails WHERE drugCode=?",drugCode);
        return rst.next();
    }

    @Override
    public List<PrescriptionDetail> findPrescriptionDetailsByPresId(String presId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM PrescriptionDetails WHERE prescriptionId=?", presId);
        List<PrescriptionDetail> prescriptionDetails = new ArrayList<>();
        while (rst.next()){
            prescriptionDetails.add(new PrescriptionDetail(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)));
        }
        return prescriptionDetails;
    }

    @Override
    public List<PrescriptionDetail> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM PrescriptionDetails");
        List<PrescriptionDetail> prescriptionDetails = new ArrayList<>();
        while (rst.next()){
            prescriptionDetails.add(new PrescriptionDetail(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)));
        }
        return prescriptionDetails;
    }

    @Override
    public PrescriptionDetail find(PrescriptionDetailPK prescriptionDetailPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM PrescriptionDetails WHERE prescriptionId=? AND drugCode=?", prescriptionDetailPK.getPrescriptionId(), prescriptionDetailPK.getDrugCode());
        if (rst.next()){
            return new PrescriptionDetail(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4));
        }
        return null;
    }

    @Override
    public boolean save(PrescriptionDetail prescriptionDetail) throws Exception {
        return CrudUtil.execute("INSERT INTO PrescriptionDetails VALUES (?,?,?,?)",
                prescriptionDetail.getPrescriptionDetailPK().getPrescriptionId(), prescriptionDetail.getPrescriptionDetailPK().getDrugCode(), prescriptionDetail.getQuantity(), prescriptionDetail.getUnitPrice());

    }

    @Override
    public boolean update(PrescriptionDetail prescriptionDetail) throws Exception {
        return CrudUtil.execute("UPDATE PrescriptionDetails SET quantity=?, unitPrice=?  WHERE prescriptionId=? AND drugCode=?",
                prescriptionDetail.getQuantity(), prescriptionDetail.getUnitPrice(), prescriptionDetail.getPrescriptionDetailPK().getPrescriptionId(), prescriptionDetail.getPrescriptionDetailPK().getDrugCode());

    }

    @Override
    public boolean delete(PrescriptionDetailPK prescriptionDetailPK) throws Exception {
        return CrudUtil.execute("DELETE FROM PrescriptionDetails WHERE prescriptionId=? AND drugCode=?",
                prescriptionDetailPK.getPrescriptionId(), prescriptionDetailPK.getDrugCode());

    }

    @Override
    public List<PrescriptionDetail> search(PrescriptionDetailPK query) throws Exception {
        return null;
    }
}

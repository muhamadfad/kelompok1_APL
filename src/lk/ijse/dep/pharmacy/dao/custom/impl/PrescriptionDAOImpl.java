package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.PrescriptionDAO;
import lk.ijse.dep.pharmacy.entity.Prescription;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PrescriptionDAOImpl implements PrescriptionDAO {
    @Override
    public String getLastPrescriptionId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT prescriptionId FROM Prescription ORDER BY prescriptionId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

    @Override
    public boolean existsByPatientId(String patientId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Prescription WHERE patientId=?", patientId);
        return rst.next();
    }

    @Override
    public boolean existsByDoctorId(String doctorId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Prescription WHERE doctorId=?", doctorId);
        return rst.next();
    }

    @Override
    public List<Prescription> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Prescription");
        List<Prescription> prescriptions = new ArrayList<>();
        while (rst.next()){
            prescriptions.add(new Prescription(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4)));
        }
        return prescriptions;
    }

    @Override
    public Prescription find(String s) throws Exception {
        ResultSet rst =CrudUtil.execute("SELECT * FROM Prescription WHERE prescriptionId=?", s);
        if (rst.next()){
            return new Prescription(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4));
        }
        return null;
    }

    @Override
    public boolean save(Prescription prescription) throws Exception {
        return CrudUtil.execute("INSERT INTO Prescription VALUES (?,?,?,?)", prescription.getPrescriptionId(), prescription.getPrescriptionDate(), prescription.getDoctorId(),prescription.getPatientId());

    }

    @Override
    public boolean update(Prescription prescription) throws Exception {
        return CrudUtil.execute("UPDATE Prescription SET prescriptionDate=?, patientId=?,doctorId=? WHERE prescriptionId=?",
                prescription.getPrescriptionDate(), prescription.getPatientId(), prescription.getDoctorId(),prescription.getPrescriptionId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Prescription WHERE prescriptionId=?", s);
    }

    @Override
    public List<Prescription> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Prescription " +
                        "WHERE prescriptionId LIKE ? OR prescriptionDate LIKE ? OR patientId LIKE ? OR doctorId LIKE ?",
                query,query,query);
        List<Prescription> prescriptions = new ArrayList<>();
        while (rst.next()) {
            prescriptions.add(new Prescription(rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return prescriptions;
    }
}

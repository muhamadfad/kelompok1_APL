package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.PatientDAO;
import lk.ijse.dep.pharmacy.entity.Patient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PatientDAOImpl implements PatientDAO {
    @Override
    public String getLastPatientId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT patientId FROM Patient ORDER BY patientId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Patient> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Patient");
        List<Patient> patients = new ArrayList<>();
        while (rst.next()) {
            patients.add(new Patient(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getDate(9),
                    rst.getInt(10)
                    ));
        }
        return patients;
    }

    @Override
    public Patient find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Patient WHERE patientId=?", s);
        if (rst.next()) {
            return new Patient(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getDate(9),
                    rst.getInt(10)
            );
        }
        return null;
    }

    @Override
    public boolean save(Patient entity) throws Exception {
        return CrudUtil.execute("INSERT INTO Patient VALUES (?,?,?,?,?,?,?,?,?,?)",
                entity.getPatientId(), entity.getPatientNic(), entity.getPatientName(),entity.getPatientAddress(), entity.getPatientMobile(), entity.getPatientLand(), entity.getPatientEmail(),entity.getPatientGender(),entity.getPatientDOB(),entity.getPatientAge());
    }

    @Override
    public boolean update(Patient patient) throws Exception {
        return CrudUtil.execute("UPDATE Patient SET patientName=?,patientNic=? ,patientAddress=?,patientMobile=?,patientLand=?,patientAge=?,patientDOB=?,patientEmail=?,patientGender=? WHERE patientId=?",
                patient.getPatientName(),patient.getPatientNic() ,patient.getPatientAddress(), patient.getPatientMobile(), patient.getPatientLand(), patient.getPatientAge(), patient.getPatientDOB(),patient.getPatientEmail(),patient.getPatientGender(), patient.getPatientId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Patient WHERE patientId=?", s);
    }

    @Override
    public List<Patient> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Patient " +
                        "WHERE patientId LIKE ? OR patientNic LIKE ? OR patientName LIKE ? OR patientAddress LIKE ? OR patientMobile LIKE ? OR patientLand LIKE ? OR patientEmail LIKE ? OR patientDOB LIKE ? OR patientAge LIKE ?",
                query,query,query,query,query,query,query,query,query);
        List<Patient> patients = new ArrayList<>();
        while (rst.next()) {
            patients.add(new Patient(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getDate(9),
                    rst.getInt(10)
            ));
        }
        return patients;
    }
}

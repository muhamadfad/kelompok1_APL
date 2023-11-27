package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.DoctorDAO;
import lk.ijse.dep.pharmacy.entity.Doctor;
import lk.ijse.dep.pharmacy.entity.Patient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class DoctorDAOImpl implements DoctorDAO {
    @Override
    public String getLastDoctorId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT doctorId FROM Doctor ORDER BY doctorId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Doctor> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Doctor");
        List<Doctor> doctors = new ArrayList<>();
        while (rst.next()) {
            doctors.add(new Doctor(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return doctors;
    }

    @Override
    public Doctor find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Doctor WHERE doctorId=?", s);
        if (rst.next()) {
            return new Doctor(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
        }
        return null;
    }

    @Override
    public boolean save(Doctor entity) throws Exception {
        return CrudUtil.execute("INSERT INTO Doctor VALUES (?,?,?,?,?,?,?,?,?)",
                entity.getDoctorId(), entity.getDoctorNic(), entity.getDoctorName(),entity.getDoctorAddress(), entity.getDoctorMobile(), entity.getDoctorLand(), entity.getDoctorEmail(),entity.getDoctorSpecialization(),entity.getDoctorRegHospital());
    }

    @Override
    public boolean update(Doctor doctor) throws Exception {
        return CrudUtil.execute("UPDATE Doctor SET doctorNic=?, doctorName=?, doctorAddress=?,doctorMobile=?,doctorLand=?,doctorRegHospital=?,doctorSpecialization=?,doctorEmail=? WHERE doctorId=?",
                doctor.getDoctorNic(), doctor.getDoctorName(), doctor.getDoctorAddress(), doctor.getDoctorMobile(), doctor.getDoctorLand(), doctor.getDoctorRegHospital(), doctor.getDoctorSpecialization(),doctor.getDoctorEmail(), doctor.getDoctorId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM Doctor WHERE doctorId=?", s);
    }

    @Override
    public List<Doctor> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Doctor " +
                        "WHERE doctorId LIKE ? OR doctorNic LIKE ? OR doctorName LIKE ? OR doctorAddress LIKE ? OR doctorMobile LIKE ? OR doctorLand LIKE ? OR doctorEmail LIKE ? OR doctorSpecialization LIKE ? OR doctorRegHospital LIKE ?",
                query,query,query,query,query,query,query,query,query);
        List<Doctor> doctors = new ArrayList<>();
        while (rst.next()) {
            doctors.add(new Doctor(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return doctors;
    }
}

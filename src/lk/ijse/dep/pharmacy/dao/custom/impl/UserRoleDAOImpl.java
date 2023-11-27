package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.UserRoleDAO;
import lk.ijse.dep.pharmacy.entity.User;
import lk.ijse.dep.pharmacy.entity.UserRole;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAOImpl implements UserRoleDAO {
    @Override
    public List<UserRole> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM UserRole");
        List<UserRole> userRole = new ArrayList<>();
        while (rst.next()) {
            userRole.add(new UserRole(rst.getString(1),
                    rst.getString(2)
            ));
        }
        return userRole;
    }

    @Override
    public UserRole find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM UserRole WHERE usrRoleId=?", s);
        if (rst.next()) {
            return new UserRole(rst.getString(1),
                    rst.getString(2)
            );
        }
        return null;
    }

    @Override
    public boolean save(UserRole entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(UserRole entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<UserRole> search(String query) throws Exception {
        return null;
    }
}

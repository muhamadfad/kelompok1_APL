package lk.ijse.dep.pharmacy.dao.custom.impl;

import lk.ijse.dep.pharmacy.dao.CrudUtil;
import lk.ijse.dep.pharmacy.dao.custom.UserDAO;
import lk.ijse.dep.pharmacy.entity.Supplier;
import lk.ijse.dep.pharmacy.entity.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class UserDAOImpl implements UserDAO {
    @Override
    public String getLastUserId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT userId FROM PharmacyUser ORDER BY userId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean existsByUserName(String userId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM PharmacyUser WHERE userId=?", userId);
        return rst.next();
    }


    @Override
    public List<User> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM PharmacyUser");
        List<User> user = new ArrayList<>();
        while (rst.next()) {
            user.add(new User(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return user;
    }

    @Override
    public User find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM PharmacyUser WHERE userId=?", s);
        if (rst.next()) {
            return new User(rst.getString(1),
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
    public boolean save(User entity) throws Exception {
        return CrudUtil.execute("INSERT INTO PharmacyUser VALUES (?,?,?,?,?,?,?)",
                entity.getUserId(), entity.getUserFullName(), entity.getUserNic(),entity.getUserRole(), entity.getUserEmail(), entity.getUserName(), entity.getPassword());

    }

    @Override
    public boolean update(User user) throws Exception {
        return CrudUtil.execute("UPDATE PharmacyUser SET userFullName=?, userNic=?,userEmail=?,userRole=?,userName=?,password=? WHERE userId=?",
                user.getUserFullName(), user.getUserNic(), user.getUserEmail(), user.getUserRole(), user.getUserName(), user.getPassword(),user.getUserId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM PharmacyUser WHERE userId=?", s);

    }

    @Override
    public List<User> search(String query) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM PharmacyUser " +
                        "WHERE userId LIKE ? OR userFullName LIKE ? OR userNic LIKE ? OR userEmail LIKE ? OR userRole LIKE ? OR userName LIKE ? OR password LIKE ?",
                query,query,query,query,query,query,query);
        List<User> user = new ArrayList<>();
        while (rst.next()) {
            user.add(new User(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return user;
    }
}

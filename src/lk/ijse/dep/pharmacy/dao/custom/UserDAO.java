package lk.ijse.dep.pharmacy.dao.custom;

import lk.ijse.dep.pharmacy.dao.CrudDAO;
import lk.ijse.dep.pharmacy.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    String getLastUserId() throws Exception;

    boolean existsByUserName(String userId) throws Exception;


}

package lk.ijse.dep.pharmacy.business.custom;

import lk.ijse.dep.pharmacy.business.SuperBO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;
import lk.ijse.dep.pharmacy.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO user)throws Exception;

    boolean updateUser(UserDTO user)throws Exception;

    boolean deleteUser(String userId) throws Exception;

    boolean findUserName(String userId) throws Exception;

    List<UserDTO> findAllUsers() throws Exception;

    String getLastUserId()throws Exception;

    UserDTO findUser(String userId) throws Exception;

    List<String> getAllUserIds() throws Exception;

    List<String> getAllUserRoles() throws Exception;

    List<UserDTO> searchUser(String query) throws Exception;
}

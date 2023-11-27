package lk.ijse.dep.pharmacy.business.custom.impl;

import lk.ijse.dep.pharmacy.business.custom.UserBO;
import lk.ijse.dep.pharmacy.dao.DAOFactory;
import lk.ijse.dep.pharmacy.dao.DAOTypes;
import lk.ijse.dep.pharmacy.dao.custom.SupplierDAO;
import lk.ijse.dep.pharmacy.dao.custom.UserDAO;
import lk.ijse.dep.pharmacy.dao.custom.UserRoleDAO;
import lk.ijse.dep.pharmacy.dto.SupplierDTO;
import lk.ijse.dep.pharmacy.dto.UserDTO;
import lk.ijse.dep.pharmacy.entity.Supplier;
import lk.ijse.dep.pharmacy.entity.User;
import lk.ijse.dep.pharmacy.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class UserBOImpl implements UserBO {

    private UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOTypes.USER);
    private UserRoleDAO userRoleDAO = DAOFactory.getInstance().getDAO(DAOTypes.USER_ROLE);


    @Override
    public boolean saveUser(UserDTO user) throws Exception {
        return userDAO.save(new User(user.getUserId(), user.getUserFullName(), user.getUserNic(),
                user.getUserRole(), user.getUserEmail(), user.getUserName(), user.getPassword()));

    }

    @Override
    public boolean updateUser(UserDTO user) throws Exception {
        return userDAO.update(new User(user.getUserId(), user.getUserFullName(), user.getUserNic(),
                user.getUserRole(), user.getUserEmail(), user.getUserName(), user.getPassword()));
    }

    @Override
    public boolean deleteUser(String userId) throws Exception {
        //        if (orderDAO.existsByCustomerId(customerId)){
//            throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
//        }
        return userDAO.delete(userId);

    }

    @Override
    public boolean findUserName(String userId) throws Exception {
        return  userDAO.existsByUserName(userId);
    }

    @Override
    public List<UserDTO> findAllUsers() throws Exception {
        List<User> alUser = userDAO.findAll();
        List<UserDTO> dtos = new ArrayList<>();

        for (User user : alUser) {
            dtos.add(new UserDTO(user.getUserId(),user.getUserFullName(),user.getUserNic(),user.getUserRole(),user.getUserEmail(),
                    user.getUserName(),user.getPassword()));
        }
        return dtos;
    }

    @Override
    public String getLastUserId() throws Exception {
        return userDAO.getLastUserId();
    }

    @Override
    public UserDTO findUser(String userId) throws Exception {
        User user = userDAO.find(userId);
        return new UserDTO(user.getUserId(),user.getUserFullName(),user.getUserNic(),user.getUserRole(),user.getUserEmail(),
                user.getUserName(),user.getPassword());
    }

    @Override
    public List<String> getAllUserIds() throws Exception {
        List<User> users = userDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (User user : users) {
            ids.add(user.getUserId());
        }
        return ids;
    }

    @Override
    public List<String> getAllUserRoles() throws Exception {
        List<UserRole> userRoles = userRoleDAO.findAll();
        List<String> names = new ArrayList<>();
        for (UserRole name : userRoles) {
            names.add(name.getUsrRoleId());
        }
        return names;
    }

    @Override
    public List<UserDTO> searchUser(String query) throws Exception {
        List<User> search = userDAO.search(query + "%");
        List<UserDTO> dtos = new ArrayList<>();

        for (User searchUser : search) {
            dtos.add(new UserDTO(searchUser.getUserId(), searchUser.getUserFullName(), searchUser.getUserNic(),
                    searchUser.getUserRole(),searchUser.getUserEmail(),searchUser.getUserName(),searchUser.getPassword()));
        }
        return dtos;
    }
}

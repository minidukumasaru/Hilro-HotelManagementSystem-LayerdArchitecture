package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import lk.ijse.model.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDTO> allUsers= new ArrayList<>();
        List<User> all = userDAO.getAll();
        for (User u : all) {
            allUsers.add(new UserDTO(u.getUserId(),u.getUserName(),u.getContact(),u.getPosition(),u.getPassword(),u.getDate()));
        }
        return (ArrayList<UserDTO>) allUsers;
    }

    @Override
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(dto.getUserId(),dto.getUserName(),dto.getContact(),dto.getPosition(),dto.getPassword(),dto.getDate()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getUserId(),dto.getUserName(),dto.getContact(),dto.getPosition(),dto.getPassword(),dto.getDate()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public String generateNewUserID() throws SQLException, ClassNotFoundException {
        return userDAO.generateNewID();
    }

    @Override
    public List<String> getIds() throws SQLException {
        return userDAO.getIds();
    }
}

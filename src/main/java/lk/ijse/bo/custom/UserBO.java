package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserBO extends SuperBO {
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    public String generateNewUserID() throws SQLException, ClassNotFoundException;
    List<String> getIds() throws SQLException;
}

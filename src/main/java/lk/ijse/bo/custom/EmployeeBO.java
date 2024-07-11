package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.EmployeeDTO;
import lk.ijse.model.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public UserDTO searchUsers(String id) throws SQLException, ClassNotFoundException ;
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException;
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;

}

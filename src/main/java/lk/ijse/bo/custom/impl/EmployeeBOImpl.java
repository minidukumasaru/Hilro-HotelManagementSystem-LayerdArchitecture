package lk.ijse.bo.custom.impl;

import lk.ijse.bo.SuperBO;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Employee;
import lk.ijse.entity.User;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.EmployeeDTO;
import lk.ijse.model.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public UserDTO searchUsers(String id) throws SQLException, ClassNotFoundException {
        User c = userDAO.search(id);
        return new UserDTO(c.getUserId(),c.getUserName(),c.getContact(),c.getPosition(),c.getPassword(),c.getDate());
    }
    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> userEntityData = (ArrayList<User>) userDAO.getAll();
        ArrayList<UserDTO> convertToDto= new ArrayList<>();
        for (User c : userEntityData) {
            convertToDto.add(new UserDTO(c.getUserId(),c.getUserName(),c.getContact(),c.getPosition(),c.getPassword(),c.getDate()));
        }
        return convertToDto;

    }

    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        List<EmployeeDTO> arrayList = new ArrayList<>();
        List<Employee> employees = employeeDAO.getAll();
        for(Employee e : employees){
            arrayList.add(new EmployeeDTO(e.getEmployeeId(),e.getEmployeeName(),e.getDate(),e.getWorkHours(),e.getContact(),e.getSalary(),e.getPosition(),e.getAttendence(),e.getAddress(),e.getUserId()));
        }
        return arrayList;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getEmployeeId(), dto.getEmployeeName(), dto.getDate(), dto.getWorkHours(),dto.getContact(), dto.getSalary(), dto.getPosition(), dto.getAttendence(), dto.getAddress(), dto.getUserId()));

    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getEmployeeId(), dto.getEmployeeName(), dto.getDate(), dto.getWorkHours(),dto.getContact(), dto.getSalary(), dto.getPosition(), dto.getAttendence(), dto.getAddress(), dto.getUserId()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }


}

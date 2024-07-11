package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
        List<Employee> employees = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("EmployeeId"), rst.getString("EmployeeName"),rst.getDate("Date"), rst.getString("WorkHours"), rst.getString("Contact"),  rst.getDouble("Salary"), rst.getString("Position"), rst.getString("Attendence"), rst.getString("Address"), rst.getString("UserId"));
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Employee(EmployeeId,EmployeeName,Date,WorkHours,Contact,Salary,Position,Attendence,Address,UserId) VALUES(?,?,?,?,?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getEmployeeName(),
                entity.getDate(),
                entity.getWorkHours(),
                entity.getContact(),
                entity.getSalary(),
                entity.getPosition(),
                entity.getAttendence(),
                entity.getAddress(),
                entity.getUserId());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Employee SET EmployeeName = ?,Date = ?,WorkHours = ?,Contact = ?,Salary = ?,Position = ?,Attendence = ?,Address = ?,UserId = ? WHERE EmployeeId = ?",
                entity.getEmployeeName(),
                entity.getDate(),
                entity.getWorkHours(),
                entity.getContact(),
                entity.getSalary(),
                entity.getPosition(),
                entity.getAttendence(),
                entity.getAddress(),
                entity.getUserId(),
                entity.getEmployeeId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Employee WHERE EmployeeId = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT EmployeeId FROM Employee ORDER BY EmployeeId DESC LIMIT 1");
        if (rst.next()) {
            String string = rst.getString(1);
            String[] strings = string.split("E0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2) {
                return "E00" + id;
            } else {
                if (length < 3) {
                    return "E0" + id;
                } else {
                    return "E" + id;
                }
            }
        } else {
            return "E001";
        }
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}

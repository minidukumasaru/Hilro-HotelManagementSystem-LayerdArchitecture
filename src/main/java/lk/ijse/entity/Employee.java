package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String EmployeeId;
    private String EmployeeName;
    private java.sql.Date Date;
    private String WorkHours;
    private String Contact;
    private double Salary;
    private String Position;
    private String Attendence;
    private String Address;
    private  String UserId;
}

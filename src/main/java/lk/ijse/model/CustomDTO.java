package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomDTO {

    //Customer
    private String CustomerId;
    private String CustomerName;
    private String Contact;
    private String Address;
    private java.sql.Date Date;
    private  String Nic;

    //Employee
    private String EmployeeId;
    private String EmployeeName;
    private String WorkHours;
    private double Salary;
    private String Position;
    private String Attendence;
    private  String UserId;

    //meal
    private String MealId;
    private String MealName;
    private double Price;

}

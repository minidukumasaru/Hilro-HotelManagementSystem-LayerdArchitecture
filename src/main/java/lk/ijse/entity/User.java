package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String UserId;
    private  String UserName;
    private String Contact;
    private String Position;
    private String Password;
    private java.sql.Date Date;
}

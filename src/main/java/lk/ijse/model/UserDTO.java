package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String UserId;
    private  String UserName;
    private String Contact;
    private String Position;
    private String Password;
    private java.sql.Date Date;
}

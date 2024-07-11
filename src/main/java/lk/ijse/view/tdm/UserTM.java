package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTM {
    private String UserId;
    private  String UserName;
    private String Contact;
    private String Position;
    private String Password;
    private java.sql.Date Date;
}

package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String CustomerId;
    private String CustomerName;
    private String Contact;
    private String Address;
    private java.sql.Date Date;
    private  String Nic;
}

package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTM  {
    private String CustomerId;
    private String CustomerName;
    private String Contact;
    private String Address;
    private java.sql.Date Date;
    private String Nic;

}

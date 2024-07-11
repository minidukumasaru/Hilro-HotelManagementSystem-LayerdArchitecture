package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SupplierTM { private String SupplierId;
    private String SupplierName;
    private String Address;
    private String Quantity;
    private String Contact;
    private String ProductName;
    private java.sql.Date Date;
}

package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier {
    private String SupplierId;
    private String SupplierName;
    private String Address;
    private String Quantity;
    private String Contact;
    private String ProductName;
    private java.sql.Date Date;
}

package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SupplierDTO {
    private String SupplierId;
    private String SupplierName;
    private String Address;
    private String Quantity;
    private String Contact;
    private String ProductName;
    private java.sql.Date Date;
}

package lk.ijse.view.tdm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartTM {
    private String UserId;
    private String ReservationId;
    private Date InDate;
    private Date OutDate;
    private String id;
    private String name;
    private double Price;
    private String Address;
    private String Contact;
    private JFXButton btnRemove;
}

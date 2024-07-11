package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReservationTM {
    private String ReservationId;
    private java.sql.Date Date;
    private String Duration;
    private String Nic;
    private String UserId;
}

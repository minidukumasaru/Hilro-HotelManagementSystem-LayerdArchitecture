package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private String ReservationId;
    private java.sql.Date Date;
    private String Nic;
    private String UserId;
}

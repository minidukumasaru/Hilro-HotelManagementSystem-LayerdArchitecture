package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReservationDTO {
    private String ReservationId;
    private java.sql.Date Date;
    private String Nic;
    private String UserId;

}

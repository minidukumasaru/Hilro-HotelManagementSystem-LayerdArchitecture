package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetailsDTO {
    private String ReservationId;
    private String UserId;
    private String nic;
    private String RoomId;
    private String MealId;
    private Date In_Date;
    private Date Out_Date;
}

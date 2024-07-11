package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RoomTM {
    private String RoomId;
    private double Price;
    private java.sql.Date Date;
    private String Avaliability;
    private String Description;
    private String Type;
}

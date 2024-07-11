package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private String RoomId;
    private double Price;
    private java.sql.Date Date;
    private String Avaliability;
    private String Description;
    private String Type;
}

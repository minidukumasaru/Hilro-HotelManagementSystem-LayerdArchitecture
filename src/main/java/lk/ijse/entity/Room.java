package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Room {
    private String RoomId;
    private double Price;
    private java.sql.Date Date;
    private String Avaliability;
    private String Description;
    private String Type;
}

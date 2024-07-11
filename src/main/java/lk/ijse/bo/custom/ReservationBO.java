package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Reservation;
import lk.ijse.entity.ReservationPlaced;
import lk.ijse.entity.Room;
import lk.ijse.model.*;

import java.sql.SQLException;

public interface ReservationBO extends SuperBO {
    public   String generateNewOrderID() throws SQLException,ClassNotFoundException;
    public CustomerDTO SEARCHbyNic(String nic) throws SQLException, ClassNotFoundException;
    public RoomDTO SEARCHbyRoomId(String roomid) throws SQLException, ClassNotFoundException;
    public MealDTO SEARCHbyMealId(String mealid) throws SQLException, ClassNotFoundException;
    public boolean placereservation(ReservationPlacedDTO dto) throws SQLException,ClassNotFoundException;

    public boolean add(ReservationDTO entity) throws SQLException, ClassNotFoundException;
}

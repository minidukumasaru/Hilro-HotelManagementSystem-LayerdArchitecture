package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.RoomDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface RoomBO extends SuperBO {
    public List<RoomDTO> getAllRoom() throws SQLException, ClassNotFoundException;

    public boolean addRoom(RoomDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException;

    public String generateNewRoomID() throws SQLException, ClassNotFoundException;
    List<String> getIds() throws SQLException;
    boolean updateAvailability(String roomId, String ave) throws SQLException;
    boolean save(String reservationId, String roomId, Date in_Date, Date out_Date) throws SQLException;

}

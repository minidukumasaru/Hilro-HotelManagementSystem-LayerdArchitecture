package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Room;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface RoomDAO extends CrudDAO<Room> {
    List<String> getIds() throws SQLException;
    boolean updateAvailability(String roomId, String ave) throws SQLException;
    boolean save(String reservationId, String roomId, Date in_Date,Date out_Date) throws SQLException;

}

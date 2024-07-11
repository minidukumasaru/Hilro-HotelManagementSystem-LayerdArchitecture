package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.RoomDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Meal;
import lk.ijse.entity.Room;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<Room> getAll() throws SQLException, ClassNotFoundException {
        List<Room> rooms = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM Room");
        while (rst.next()) {
            Room room = new Room(rst.getString("RoomId"), rst.getDouble("Price"), rst.getDate("Date"), rst.getString("Avaliability"),  rst.getString("Description"),rst.getString("Type"));
            rooms.add(room);
        }
        return rooms;
    }

    @Override
    public boolean add(Room entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Room(RoomId,Price,Date,Avaliability,Description,Type) VALUES (?,?,?,?,?,?)",
                entity.getRoomId(),
                entity.getPrice(),
                entity.getDate(),
                entity.getAvaliability(),
                entity.getDescription(),
                entity.getType());

    }

    @Override
    public boolean update(Room entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Room SET Price = ?, Date = ? , Avaliability = ?, Description = ?, Type = ?  WHERE RoomId = ?",
                entity.getPrice(),
                entity.getDate(),
                entity.getAvaliability(),
                entity.getDescription(),
                entity.getType(),
                entity.getRoomId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "DELETE FROM Room WHERE RoomId = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT RoomId FROM Room ORDER BY RoomId DESC LIMIT 1");
        if(rst.next()){
            String string = rst.getString(1);
            String[] strings = string.split("R0");
            int id =Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if(length < 2){
                return "R00"+id;
            }else {
                if(length < 3){
                    return "R0"+id;
                }else {
                    return "R"+id;
                }
            }
        }else {
            return "R001";
        }
    }

    @Override
    public Room search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Room WHERE RoomId = ? ",id);
        if (rst.next()) {
            return new Room(
                    rst.getString("RoomId"),
                    rst.getDouble("Price"),
                    rst.getDate("Date"),
                    rst.getString("Avaliability"),
                    rst.getString("Description"),
                    rst.getString("Type")
            );
        }else {
            return null;
        }
    }

    @Override
    public List<String> getIds() throws SQLException {
        List<String> List = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT RoomId FROM Room");
        while (rst.next()) {
            List.add(rst.getString(1));
        }
        return List;

    }

    @Override
    public boolean updateAvailability(String roomId, String ave) throws SQLException {
        return SQLUtil.execute ( "Update Room set Avaliability =  ? where RoomId = ?",
                ave,
                roomId);

    }

    @Override
    public boolean save(String reservationId, String roomId, Date in_Date, Date out_Date) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO RoomReservationInfo(ReservationId,RoomId,In_Date,Out_Date) VALUES (?,?,?,?)",
                reservationId,roomId,in_Date,out_Date);

    }
}

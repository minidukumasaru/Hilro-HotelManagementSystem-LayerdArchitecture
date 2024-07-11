package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ReservationDAO;
import lk.ijse.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public List<Reservation> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Reservation entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Reservation (ReservationId,Date,Nic,UserId) VALUES (?,?,?,?)",entity.getReservationId(),entity.getDate(), entity.getNic(),entity.getUserId());
    }

    public boolean add1(Reservation entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Reservation VALUES (?,?,?,?)",entity.getReservationId(),entity.getDate(), entity.getNic(),entity.getUserId());
    }

    @Override
    public boolean update(Reservation entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT ReservationId FROM Reservation ORDER BY ReservationId DESC LIMIT 1;");
        if (rst.next()) {
            String string = rst.getString(1);
            String idNumber = string.replaceAll("\\D", ""); // Remove non-digit characters
            int id = 0;
            if (!idNumber.isEmpty()) {
                id = Integer.parseInt(idNumber);
            }
            id++;
            String ID = String.format("R%03d", id); // Format ID with leading zeros
            return ID;
        } else {
            return "R001";
        }
    }

    @Override
    public Reservation search(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT * FROM Room WHERE RoomId = ?",id
        );
    }
}

package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Reservation;

import java.sql.SQLException;

public interface ReservationDAO extends CrudDAO<Reservation> {
    public boolean add1(Reservation entity) throws SQLException, ClassNotFoundException;
}

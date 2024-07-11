package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.ReservationDetailsDAO;
import lk.ijse.entity.ReservationDetails;

import java.sql.SQLException;
import java.util.List;

public class ReservationDetailsDAOImpl implements ReservationDetailsDAO {
    @Override
    public List<ReservationDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(ReservationDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ReservationDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ReservationDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}

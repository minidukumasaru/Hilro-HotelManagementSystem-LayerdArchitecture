package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RoomBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.RoomDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Room;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.RoomDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {

    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    @Override
    public List<RoomDTO> getAllRoom() throws SQLException, ClassNotFoundException {
        List<RoomDTO> arrayList = new ArrayList<>();
        List<Room> rooms = roomDAO.getAll();
        for (Room c : rooms){
            arrayList.add(new RoomDTO(c.getRoomId(),c.getPrice(),c.getDate(),c.getAvaliability(),c.getDescription(),c.getType()));
        }
        return arrayList;

    }

    @Override
    public boolean addRoom(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return roomDAO.add(new Room(dto.getRoomId(), dto.getPrice(), dto.getDate(), dto.getAvaliability(), dto.getDescription(), dto.getType()));
    }

    @Override
    public boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return roomDAO.update(new Room(dto.getRoomId(),dto.getPrice(), dto.getDate(), dto.getAvaliability(),  dto.getDescription(),dto.getType()));
    }

    @Override
    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(id);
    }

    @Override
    public String generateNewRoomID() throws SQLException, ClassNotFoundException {
        return roomDAO.generateNewID();
    }

    @Override
    public List<String> getIds() throws SQLException {
        return roomDAO.getIds();
    }

    @Override
    public boolean updateAvailability(String roomId, String ave) throws SQLException {
        return roomDAO.updateAvailability(roomId,ave);
    }

    @Override
    public boolean save(String reservationId, String roomId, Date in_Date, Date out_Date) throws SQLException {
        return roomDAO.save(reservationId,roomId,in_Date,out_Date);
    }
}

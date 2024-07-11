package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ReservationBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.*;
import lk.ijse.db.dbConnection;
import lk.ijse.entity.*;
import lk.ijse.model.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservationBOImpl implements ReservationBO {
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    MealDAO mealDAO = (MealDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEAL);
    ReservationDetailsDAO reservationDetailsDAO = (ReservationDetailsDAO)  DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION_DETAILS); // Ensure this gets OrderItemDAOImpl
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return reservationDAO.generateNewID();
    }

    @Override
    public CustomerDTO SEARCHbyNic(String nic) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(nic);
        return new CustomerDTO(customer.getCustomerId(), customer.getCustomerName(),customer.getContact(), customer.getAddress(), customer.getDate(),customer.getNic());
    }

    @Override
    public RoomDTO SEARCHbyRoomId(String roomid) throws SQLException, ClassNotFoundException {
        Room room =roomDAO.search(roomid);
        return new RoomDTO(room.getRoomId(),room.getPrice(),room.getDate(),room.getAvaliability(),room.getDescription(),room.getType()) ;
    }

   @Override
    public MealDTO SEARCHbyMealId(String mealid ) throws SQLException, ClassNotFoundException {
        Meal meal = mealDAO.search(mealid);
       return new MealDTO(meal.getMealId(), meal.getMealName(),meal.getPrice(), meal.getDate());
    }
//@Override
//public MealDTO SEARCHbyMealId(String mealId) throws SQLException, ClassNotFoundException {
//    Meal meal = mealDAO.search(mealId);
//    if (meal == null) {
//        // Handle the case where no meal is found
//        throw new NullPointerException("Meal with ID " + mealId + " not found.");
//        // Alternatively, you could return null or a default value if that's more appropriate
//        // return null;
//    }
//    return new MealDTO(meal.getMealId(), meal.getMealName(), meal.getPrice(), meal.getDate());
//}



    @Override
    public boolean placereservation(ReservationPlacedDTO reservationPlaced) throws SQLException, ClassNotFoundException {


            Connection connection = dbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            try {
                System.out.println( "hhhhhhlllllllllll");
                Boolean IsSaved =reservationDAO.add
                        (new Reservation(reservationPlaced.getReservation().getReservationId(),reservationPlaced.getReservation().getDate(),reservationPlaced.getReservation().getNic(),reservationPlaced.getReservation().getUserId()));
                System.out.println( "issaved"+IsSaved);
                if (IsSaved) {
                    boolean isUpdate = false;
                    for (ReservationDetails reservationDetails : reservationPlaced.getOdList()) {
                        if (reservationDetails.getMealId() == null) {
                            String ave = "No";
                            isUpdate = roomDAO.updateAvailability(reservationDetails.getRoomId(), ave);

                        } else {

                            isUpdate = mealDAO.SAVE1(reservationDetails.getMealId(), reservationDetails.getReservationId());
                            System.out.println(reservationDetails.getMealId() + reservationDetails.getReservationId());
                        }
                    }
                    System.out.println( "isUpdate"+isUpdate);
                    if (isUpdate) {
                        boolean isSave1 = false;
                        for (ReservationDetails ar : reservationPlaced.getOdList()) {
                            if (ar.getMealId() == null) {

                                isSave1 = roomDAO.save(ar.getRoomId(), ar.getReservationId(), ar.getIn_Date(), ar.getOut_Date());
                            }
                            System.out.println( "issaved111"+isSave1);
                            if (isSave1) {
                                connection.commit();
                                return true;
                            }
                        }
                    }

                }
                connection.rollback();
                return false;

            } catch (Exception e) {
                connection.rollback();
                return false;
            }finally {
                connection.setAutoCommit(true);
            }

        }

    @Override
    public boolean add(ReservationDTO entity) throws SQLException, ClassNotFoundException {
        return reservationDAO.add(new Reservation(entity.getReservationId(),entity.getDate(),entity.getNic(),entity.getUserId()));
    }
}

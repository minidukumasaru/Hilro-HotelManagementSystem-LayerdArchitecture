package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,SUPPLIER,EMPLOYEE,MEAL,USER,ROOM,RESERVATION,RESERVATION_DETAILS
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case SUPPLIER:
               return new SupplierDAOImpl();
          case EMPLOYEE:
                return new EmployeeDAOImpl();
            case MEAL:
                return new MealDAOImpl();
            case USER:
                return new UserDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case RESERVATION_DETAILS:
                return new ReservationDetailsDAOImpl();
            default:
                return null;
        }
    }
}

package lk.ijse.bo;


import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public  enum BOTypes{
        CUSTOMER,SUPPLIER,EMPLOYEE,MEAL,USER,ROOM,RESERVATION
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case MEAL:
                return new MealBOImpl();
            case USER:
                return new UserBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            default:
                return null;
        }
    }
}

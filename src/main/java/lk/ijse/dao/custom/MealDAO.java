package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Meal;

import java.sql.SQLException;
import java.util.List;

public interface MealDAO extends CrudDAO<Meal> {
    List<String> getIds() throws SQLException;
    boolean SAVE1(String ReservationId,String MealId) throws SQLException;

}

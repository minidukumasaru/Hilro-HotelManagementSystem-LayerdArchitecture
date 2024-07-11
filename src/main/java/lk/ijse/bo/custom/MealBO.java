package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.MealDTO;

import java.sql.SQLException;
import java.util.List;

public interface MealBO extends SuperBO {
    public List<MealDTO> getAllMeal() throws SQLException, ClassNotFoundException;

    public boolean addMeal(MealDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateMeal(MealDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteMeal(String id) throws SQLException, ClassNotFoundException;

    public String generateNewMealID() throws SQLException, ClassNotFoundException;
    List<String> getIds() throws SQLException;
    boolean SAVE1(String ReservationId,String MealId) throws SQLException;
}

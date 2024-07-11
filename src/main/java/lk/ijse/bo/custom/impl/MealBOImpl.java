package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.MealBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.MealDAO;
import lk.ijse.entity.Meal;
import lk.ijse.model.MealDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealBOImpl implements MealBO {

    MealDAO mealDAO = (MealDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEAL);
    @Override
    public List<MealDTO> getAllMeal() throws SQLException, ClassNotFoundException {
        List<MealDTO> arrayList = new ArrayList<>();
        List<Meal> meals = mealDAO.getAll();
        for (Meal c : meals){
            arrayList.add(new MealDTO(c.getMealId(),c.getMealName(),c.getPrice(),c.getDate()));
        }
        return arrayList;

    }


    @Override
    public boolean addMeal(MealDTO dto) throws SQLException, ClassNotFoundException {
        return mealDAO.add(new Meal(dto.getMealId(), dto.getMealName(), dto.getPrice(), dto.getDate()));
    }

    @Override
    public boolean updateMeal(MealDTO dto) throws SQLException, ClassNotFoundException {
        return mealDAO.update(new Meal(dto.getMealId(),dto.getMealName(), dto.getPrice(),  dto.getDate()));
    }


    @Override
    public boolean deleteMeal(String id) throws SQLException, ClassNotFoundException {
        return mealDAO.delete(id);
    }

    @Override
    public String generateNewMealID() throws SQLException, ClassNotFoundException {
        return mealDAO.generateNewID();
    }

    @Override
    public List<String> getIds() throws SQLException {
        return mealDAO.getIds();
    }

    @Override
    public boolean SAVE1(String ReservationId, String MealId) throws SQLException {
        return mealDAO.SAVE1(ReservationId,MealId);
    }
}

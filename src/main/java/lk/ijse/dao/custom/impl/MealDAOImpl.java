package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MealDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealDAOImpl implements MealDAO {
    @Override
    public List<Meal> getAll() throws SQLException, ClassNotFoundException {
        List<Meal> meals = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM Meal");
        while (rst.next()) {
            Meal meal = new Meal(rst.getString("MealId"), rst.getString("MealName"), rst.getDouble("Price"),   rst.getDate("Date"));
            meals.add(meal);
        }
        return meals;

    }

    @Override
    public boolean add(Meal entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Meal(MealId, MealName,Price,Date) VALUES (?,?,?,?)",
                entity.getMealId(),
                entity.getMealName(),
                entity.getPrice(),
                entity.getDate());


    }

    @Override
    public boolean update(Meal entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Meal SET MealName = ?, Price = ? , Date = ?  WHERE MealId = ?",
                entity.getMealName(),
                entity.getPrice(),
                entity.getDate(),
                entity.getMealId());
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "DELETE FROM Meal WHERE MealId = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT MealId FROM Meal ORDER BY MealId DESC LIMIT 1");
        if(rst.next()){
            String string = rst.getString(1);
            String[] strings = string.split("M0");
            int id =Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if(length < 2){
                return "M00"+id;
            }else {
                if(length < 3){
                    return "M0"+id;
                }else {
                    return "M"+id;
                }
            }
        }else {
            return "M001";
        }
    }

    @Override
    public Meal search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Meal WHERE MealId = ? ",id);
        if (rst.next()) {
            return new Meal(
                    rst.getString("MealId"),
                    rst.getString("MealName"),
                    rst.getDouble("Price"),
                    rst.getDate("Date")
            );
        }else {
            return null;
        }
    }

    @Override
    public List<String> getIds() throws SQLException {
        List<String> List = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT MealId FROM Meal");
        while (rst.next()) {
            List.add(rst.getString(1));
        }
        return List;

    }

    @Override
    public boolean SAVE1(String ReservationId, String MealId) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO MealReservationInfo VALUES (?,?)",
                ReservationId,
                MealId);
    }
}

package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM User");
        while (rst.next()) {
            User user = new User(rst.getString("UserId"), rst.getString("UserName"),rst.getString("Contact"),rst.getString("Position"),rst.getString("Password"),rst.getDate("Date"));
            allUsers.add(user);
        }
        return allUsers;
    }

    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO User(UserId , UserName, Contact,Position, Password,Date) VALUES(?,?,?,?,?,?)",entity.getUserId(),entity.getUserName(),entity.getContact(),entity.getPosition(),entity.getPassword(),entity.getDate());
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE User SET UserName = ?, Contact = ?, Position = ? ,Password = ? ,Date = ? WHERE UserId = ?",entity.getUserName(),entity.getContact(),entity.getPosition(),entity.getPassword(),entity.getDate(),entity.getUserId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM User WHERE UserId = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT UserId FROM User ORDER BY UserId DESC LIMIT 1;");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("U0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "U00"+id;
            }else {
                if (length < 3){
                    return "U0"+id;
                }else {
                    return "U"+id;
                }
            }
        } else {
            return "U001";
        }
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {
        List<String> List = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT UserId FROM User");
        while (rst.next()) {
            List.add(rst.getString(1));
        }
        return List;

    }
}

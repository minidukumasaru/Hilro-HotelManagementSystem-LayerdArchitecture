package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    List<String> getIds() throws SQLException;
}

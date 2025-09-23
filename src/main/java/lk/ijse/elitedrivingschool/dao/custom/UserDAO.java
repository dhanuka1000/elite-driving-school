package lk.ijse.elitedrivingschool.dao.custom;

import lk.ijse.elitedrivingschool.dao.CrudDAO;
import lk.ijse.elitedrivingschool.entity.Lesson;
import lk.ijse.elitedrivingschool.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO extends CrudDAO<User> {

    Optional<User> findById(String id) throws SQLException;
}

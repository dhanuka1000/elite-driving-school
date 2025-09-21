package lk.ijse.elitedrivingschool.dao.custom;

import lk.ijse.elitedrivingschool.dao.CrudDAO;
import lk.ijse.elitedrivingschool.entity.Lesson;

import java.sql.SQLException;
import java.util.Optional;

public interface LessonDAO extends CrudDAO<Lesson> {

    Optional<Lesson> findById(String id) throws SQLException;
}

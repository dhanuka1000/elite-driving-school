package lk.ijse.elitedrivingschool.dao.custom;

import lk.ijse.elitedrivingschool.dao.CrudDAO;
import lk.ijse.elitedrivingschool.entity.Instructor;
import lk.ijse.elitedrivingschool.entity.Lesson;

import java.sql.SQLException;
import java.util.Optional;

public interface InstructorDAO extends CrudDAO<Instructor> {
    Optional<Instructor> findById(String id) throws SQLException;
}

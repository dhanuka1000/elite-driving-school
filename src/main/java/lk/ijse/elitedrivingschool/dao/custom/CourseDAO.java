package lk.ijse.elitedrivingschool.dao.custom;

import lk.ijse.elitedrivingschool.dao.CrudDAO;
import lk.ijse.elitedrivingschool.entity.Course;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseDAO extends CrudDAO<Course> {

    Optional<Course> findById(String courseId) throws SQLException;
}

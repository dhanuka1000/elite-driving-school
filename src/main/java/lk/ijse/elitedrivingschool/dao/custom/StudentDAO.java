package lk.ijse.elitedrivingschool.dao.custom;

import lk.ijse.elitedrivingschool.dao.CrudDAO;
import lk.ijse.elitedrivingschool.entity.Student;

import java.sql.SQLException;
import java.util.Optional;

public interface StudentDAO extends CrudDAO<Student> {
    Optional<Student> findById(String id) throws SQLException;
}

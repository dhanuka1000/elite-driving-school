package lk.ijse.elitedrivingschool.dao.custom;

import lk.ijse.elitedrivingschool.dao.CrudDAO;
import lk.ijse.elitedrivingschool.entity.Enrolment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EnrolmentDAO extends CrudDAO<Enrolment> {
    boolean existsByStudentId(String id);
    List<String> getAllStudentIds() throws SQLException;
    List<String> getCourseIdsByStudent(String studentId) throws SQLException;
    Optional<Enrolment> findById(String id) throws SQLException;
}

package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.dto.CourseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CourseBO extends SuperBO {

    ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException;
    boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException;
    boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException;
    boolean deleteCourse(String id) throws SQLException, ClassNotFoundException;
    String generateNewCourseId() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException;
}

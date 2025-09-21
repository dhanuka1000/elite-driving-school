package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.CourseBO;
import lk.ijse.elitedrivingschool.dto.CourseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CourseBOImpl implements CourseBO {
    @Override
    public ArrayList<CourseDTO> getAllCourses() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteCourse(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewCourseId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }
}

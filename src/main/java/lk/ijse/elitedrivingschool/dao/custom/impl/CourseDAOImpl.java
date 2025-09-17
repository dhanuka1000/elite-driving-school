package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.dao.custom.CourseDAO;
import lk.ijse.elitedrivingschool.entity.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    @Override
    public List<Course> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(Course course) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Course course) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }
}

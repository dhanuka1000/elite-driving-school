package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.dao.custom.LessonDAO;
import lk.ijse.elitedrivingschool.entity.Lesson;

import java.sql.SQLException;
import java.util.List;

public class LessonDAOImpl implements LessonDAO {
    @Override
    public List<Lesson> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(Lesson lesson) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Lesson lesson) throws SQLException {
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

package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.dao.custom.InstructorDAO;
import lk.ijse.elitedrivingschool.entity.Instructor;

import java.sql.SQLException;
import java.util.List;

public class InstructorDAOImpl implements InstructorDAO {
    @Override
    public List<Instructor> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(Instructor instructor) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Instructor instructor) throws SQLException {
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

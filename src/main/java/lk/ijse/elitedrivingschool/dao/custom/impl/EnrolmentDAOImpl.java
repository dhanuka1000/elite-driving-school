package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.dao.custom.EnrolmentDAO;
import lk.ijse.elitedrivingschool.entity.Enrolment;

import java.sql.SQLException;
import java.util.List;

public class EnrolmentDAOImpl implements EnrolmentDAO {
    @Override
    public List<Enrolment> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(Enrolment enrolment) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Enrolment enrolment) throws SQLException {
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

    @Override
    public boolean existsByStudentId(String id) {
        return false;
    }
}

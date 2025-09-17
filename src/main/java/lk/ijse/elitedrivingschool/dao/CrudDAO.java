package lk.ijse.elitedrivingschool.dao;

import lk.ijse.elitedrivingschool.dao.custom.SuperDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> extends SuperDAO {

    List<T> getAll() throws SQLException;
    String getLastId() throws SQLException;
    boolean save(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    boolean delete(String id) throws SQLException;
    List<String> getAllIds() throws SQLException;
}

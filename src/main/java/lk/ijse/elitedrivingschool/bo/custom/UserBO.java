package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

    ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;
    boolean saveUsers(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    boolean updateUsers(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    boolean deleteUsers(String id) throws SQLException, ClassNotFoundException;
    String generateNewUserId() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException;
}

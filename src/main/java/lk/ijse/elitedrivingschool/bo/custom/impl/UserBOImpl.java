package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.UserBO;
import lk.ijse.elitedrivingschool.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveUsers(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateUsers(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteUsers(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewUserId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }
}

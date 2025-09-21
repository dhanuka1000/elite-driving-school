package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.LessonBO;
import lk.ijse.elitedrivingschool.dto.LessionDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class LessionBOImpl implements LessonBO {
    @Override
    public ArrayList<LessionDTO> getAllLessons() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveLessons(LessionDTO lessionDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateLessons(LessionDTO lessionDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteLessons(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewLessonId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }
}

package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.dto.InstructorDTO;
import lk.ijse.elitedrivingschool.dto.LessionDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LessonBO extends SuperBO {

    ArrayList<LessionDTO> getAllLessons() throws SQLException, ClassNotFoundException;
    boolean saveLessons(LessionDTO lessionDTO) throws SQLException, ClassNotFoundException;
    boolean updateLessons(LessionDTO lessionDTO) throws SQLException, ClassNotFoundException;
    boolean deleteLessons(String id) throws SQLException, ClassNotFoundException;
    String generateNewLessonId() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException;
}

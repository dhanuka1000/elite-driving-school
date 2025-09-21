package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.dto.CourseDTO;
import lk.ijse.elitedrivingschool.dto.InstructorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InstructorBO extends SuperBO {

    ArrayList<InstructorDTO> getAllInstructors() throws SQLException, ClassNotFoundException;
    boolean saveInstructors(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException;
    boolean updateInstructors(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException;
    boolean deleteInstructors(String id) throws SQLException, ClassNotFoundException;
    String generateNewInstructorId() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException;
}

package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.bo.exception.InUseException;
import lk.ijse.elitedrivingschool.dto.PaymentDTO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {

    ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;
    boolean saveStudents(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;
    boolean updateStudents(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;
    boolean deleteStudents(String id) throws SQLException, ClassNotFoundException, InUseException;
    String generateNewStudentId() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException;
}

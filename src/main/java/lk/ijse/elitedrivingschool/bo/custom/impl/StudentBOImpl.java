package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.StudentBO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {
    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveStudents(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateStudents(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteStudents(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewStudentId() throws SQLException, ClassNotFoundException {
        return "";
    }
}

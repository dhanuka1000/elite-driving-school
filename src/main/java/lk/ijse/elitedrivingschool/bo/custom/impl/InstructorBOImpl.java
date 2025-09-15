package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.InstructorBO;
import lk.ijse.elitedrivingschool.dto.InstructorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class InstructorBOImpl implements InstructorBO {
    @Override
    public ArrayList<InstructorDTO> getAllInstructors() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveInstructors(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateInstructors(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteInstructors(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewInstructorId() throws SQLException, ClassNotFoundException {
        return "";
    }
}

package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.InstructorBO;
import lk.ijse.elitedrivingschool.bo.exception.DuplicateException;
import lk.ijse.elitedrivingschool.bo.exception.NotFoundException;
import lk.ijse.elitedrivingschool.bo.util.EntityDTOConverter;
import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.InstructorDAO;
import lk.ijse.elitedrivingschool.dto.InstructorDTO;
import lk.ijse.elitedrivingschool.entity.Instructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstructorBOImpl implements InstructorBO {

    private final InstructorDAO instructorDAO = DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTOR);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public ArrayList<InstructorDTO> getAllInstructors() throws SQLException, ClassNotFoundException {
        try {
            List<Instructor> instructors = instructorDAO.getAll();
            ArrayList<InstructorDTO> instructorDTOS = new ArrayList<>();

            for (Instructor instructor : instructors) {
                instructorDTOS.add(converter.getInstructorDTO(instructor));
            }
            return instructorDTOS;
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve instructor", e);
        }
    }

    @Override
    public boolean saveInstructors(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Instructor> optionalInstructor = instructorDAO.findById(instructorDTO.getInstructorId());
            if (optionalInstructor.isPresent()) {
                throw new DuplicateException("Duplicate lesson ID: " + instructorDTO.getInstructorId());
            }

            Instructor instructor = converter.getInstructor(instructorDTO);
            return instructorDAO.save(instructor);

        } catch (DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to save instructor", e);
        }
    }

    @Override
    public boolean updateInstructors(InstructorDTO instructorDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Instructor> optionalInstructor = instructorDAO.findById(instructorDTO.getInstructorId());
            if (optionalInstructor.isEmpty()) {
                throw new NotFoundException("Lesson not found with ID: " + instructorDTO.getInstructorId());
            }

            Instructor instructor = converter.getInstructor(instructorDTO);
            return instructorDAO.update(instructor);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to update instructor", e);
        }
    }

    @Override
    public boolean deleteInstructors(String id) throws SQLException, ClassNotFoundException {
        try {
            Optional<Instructor> optionalInstructor = instructorDAO.findById(id);
            if (optionalInstructor.isEmpty()) {
                throw new NotFoundException("Instructor not found with ID: " + id);
            }

            return instructorDAO.delete(id);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to delete instructor", e);
        }
    }

    @Override
    public String generateNewInstructorId() throws SQLException, ClassNotFoundException {
        return getNextId();
    }

    @Override
    public String getNextId() throws SQLException {
        try {
            String lastId = instructorDAO.getLastId();
            char tableChar = 'I';

            if (lastId != null && !lastId.isEmpty()) {
                String lastIdNumberString = lastId.substring(1);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                return String.format(tableChar + "%03d", nextIdNumber);
            }
            return tableChar + "001";
        } catch (Exception e) {
            throw new SQLException("Failed to generate next instructor ID", e);
        }
    }
}

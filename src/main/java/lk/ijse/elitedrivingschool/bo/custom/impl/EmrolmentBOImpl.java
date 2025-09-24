package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.EnrolmentBO;
import lk.ijse.elitedrivingschool.bo.exception.DuplicateException;
import lk.ijse.elitedrivingschool.bo.exception.NotFoundException;
import lk.ijse.elitedrivingschool.bo.util.EntityDTOConverter;
import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.EnrolmentDAO;
import lk.ijse.elitedrivingschool.dto.EnrolmentDTO;
import lk.ijse.elitedrivingschool.entity.Course;
import lk.ijse.elitedrivingschool.entity.Enrolment;
import lk.ijse.elitedrivingschool.entity.Instructor;
import lk.ijse.elitedrivingschool.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmrolmentBOImpl implements EnrolmentBO {

    private final EnrolmentDAO enrolmentDAO = DAOFactory.getInstance().getDAO(DAOTypes.ENROLMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public ArrayList<EnrolmentDTO> getAllEnrolment() throws SQLException, ClassNotFoundException {
        try {
            List<Enrolment> enrolments = enrolmentDAO.getAll();
            ArrayList<EnrolmentDTO> enrolmentDTOS = new ArrayList<>();

            for (Enrolment enrolment : enrolments) {
                enrolmentDTOS.add(converter.getEnrolmentDTO(enrolment));
            }
            return enrolmentDTOS;
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve enrolment", e);
        }
    }

    @Override
    public boolean saveEnrolment(EnrolmentDTO dto) throws SQLException, ClassNotFoundException {

        try {
            Optional<Enrolment> optionalEnrolment = enrolmentDAO.findById(dto.getId());
            if (optionalEnrolment.isPresent()) {
                throw new DuplicateException("Duplicate lesson ID: " + dto.getId());
            }

            Enrolment enrolment = converter.getEnrolment(dto);
            return enrolmentDAO.save(enrolment);

        } catch (DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to save enrolment", e);
        }
    }

    @Override
    public boolean updateEnrolment(EnrolmentDTO dto) throws SQLException, ClassNotFoundException {
        try {
            Optional<Enrolment> optionalEnrolment = enrolmentDAO.findById(dto.getId());
            if (optionalEnrolment.isEmpty()) {
                throw new NotFoundException("Enrolment not found with ID: " + dto.getId());
            }

            Enrolment enrolment = converter.getEnrolment(dto);
            return enrolmentDAO.save(enrolment);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to update Enrolment", e);
        }
    }

    @Override
    public boolean deleteEnrolment(String id) throws SQLException, ClassNotFoundException {
        try {
            Optional<Enrolment> optionalInstructor = enrolmentDAO.findById(id);
            if (optionalInstructor.isEmpty()) {
                throw new NotFoundException("Enrolment not found with ID: " + id);
            }

            return enrolmentDAO.delete(id);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to delete Enrolment", e);
        }
    }

    @Override
    public String generateNewEnrolmentId() throws SQLException, ClassNotFoundException {
        return getNextId();
    }

    @Override
    public String getNextId() throws SQLException {
        String lastId = enrolmentDAO.getLastId();
        try {
            if (lastId != null && lastId.startsWith("E-")) {
                int num = Integer.parseInt(lastId.split("-")[1]);
                num++;
                return "E-" + String.format("%03d", num);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "E-001";
    }


    @Override
    public ArrayList<String> getAllStudentIds() throws SQLException, ClassNotFoundException {
        return new ArrayList<>(enrolmentDAO.getAllStudentIds());
    }

    @Override
    public ArrayList<String> getCourseIdsByStudent(String studentId) throws SQLException, ClassNotFoundException {
        return new ArrayList<>(enrolmentDAO.getCourseIdsByStudent(studentId));
    }
}

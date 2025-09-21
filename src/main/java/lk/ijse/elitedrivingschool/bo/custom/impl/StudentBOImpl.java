package lk.ijse.elitedrivingschool.bo.custom.impl;
import lk.ijse.elitedrivingschool.bo.custom.StudentBO;
import lk.ijse.elitedrivingschool.bo.exception.DuplicateException;
import lk.ijse.elitedrivingschool.bo.exception.InUseException;
import lk.ijse.elitedrivingschool.bo.exception.NotFoundException;
//import lk.ijse.elitedrivingschool.bo.util.EntityDTOConverter;
import lk.ijse.elitedrivingschool.bo.util.EntityDTOConverter;
import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.EnrolmentDAO;
import lk.ijse.elitedrivingschool.dao.custom.StudentDAO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);
    private final EnrolmentDAO enrolmentDAO = DAOFactory.getInstance().getDAO(DAOTypes.ENROLMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        try {
            List<Student> students = studentDAO.getAll();
            List<StudentDTO> studentDTOs = new ArrayList<>();

            for (Student student : students) {
                studentDTOs.add(converter.getStudentDTO(student));
            }
            return (ArrayList<StudentDTO>) studentDTOs;
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve students", e);
        }
    }

    @Override
    public boolean saveStudents(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Student> optionalStudent = studentDAO.findById(studentDTO.getStudentId());
            if (optionalStudent.isPresent()) {
                throw new DuplicateException("Duplicate student ID: " + studentDTO.getStudentId());
            }
            Student student = converter.getStudent(studentDTO);
            return studentDAO.save(student);

        } catch (DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to save student", e);
        }
    }

    @Override
    public boolean updateStudents(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Student> optionalStudent = studentDAO.findById(studentDTO.getStudentId());
            if (optionalStudent.isEmpty()) {
                throw new NotFoundException("Student not found with ID: " + studentDTO.getStudentId());
            }

            Student student = converter.getStudent(studentDTO);
            return studentDAO.update(student);

        } catch (NotFoundException | DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to update student", e);
        }
    }

    @Override
    public boolean deleteStudents(String id) throws SQLException, ClassNotFoundException, InUseException {
        try {
            // Check if student exists
            Optional<Student> optionalStudent = studentDAO.findById(id);
            if (optionalStudent.isEmpty()) {
                throw new NotFoundException("Student not found with ID: " + id);
            }

            if (enrolmentDAO.existsByStudentId(id)) {
                throw new InUseException("Cannot delete student. Student has existing enrollments");
            }

            // Delete student
            return studentDAO.delete(id);

        } catch (NotFoundException | InUseException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to delete student", e);
        }
    }

    @Override
    public String generateNewStudentId() throws SQLException, ClassNotFoundException {
        return getNextId();
    }

    @Override
    public String getNextId() throws SQLException {
        try {
            String lastId = studentDAO.getLastId();
            char tableChar = 'S';

            if (lastId != null && !lastId.isEmpty()) {
                String lastIdNumberString = lastId.substring(1);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                return String.format(tableChar + "%03d", nextIdNumber);
            }
            return tableChar + "001";
        } catch (Exception e) {
            throw new SQLException("Failed to generate next student ID", e);
        }
    }
}

package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.dto.CourseDTO;
import lk.ijse.elitedrivingschool.dto.EnrolmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EnrolmentBO extends SuperBO {

    ArrayList<EnrolmentDTO> getAllEnrolment() throws SQLException, ClassNotFoundException;
    boolean saveEnrolment(EnrolmentDTO enrolmentDTO) throws SQLException, ClassNotFoundException;
    boolean updateEnrolment(EnrolmentDTO enrolmentDTO) throws SQLException, ClassNotFoundException;
    boolean deleteEnrolment(String id) throws SQLException, ClassNotFoundException;
    String generateNewEnrolmentId() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException;
    ArrayList<String> getAllStudentIds() throws SQLException, ClassNotFoundException;
    ArrayList<String> getCourseIdsByStudent(String studentId) throws SQLException, ClassNotFoundException;

}

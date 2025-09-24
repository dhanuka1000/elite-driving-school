package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.PaymentBO;
import lk.ijse.elitedrivingschool.bo.exception.DuplicateException;
import lk.ijse.elitedrivingschool.bo.exception.InUseException;
import lk.ijse.elitedrivingschool.bo.exception.NotFoundException;
import lk.ijse.elitedrivingschool.bo.util.EntityDTOConverter;
import lk.ijse.elitedrivingschool.dao.DAOFactory;
import lk.ijse.elitedrivingschool.dao.DAOTypes;
import lk.ijse.elitedrivingschool.dao.custom.EnrolmentDAO;
import lk.ijse.elitedrivingschool.dao.custom.PaymentDAO;
import lk.ijse.elitedrivingschool.dao.custom.StudentDAO;
import lk.ijse.elitedrivingschool.dto.PaymentDTO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.entity.Payment;
import lk.ijse.elitedrivingschool.entity.Student;
import lk.ijse.elitedrivingschool.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = DAOFactory.getInstance().getDAO(DAOTypes.PAYMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        try {
            List<Payment> payments = paymentDAO.getAll();
            List<PaymentDTO> paymentDTOS = new ArrayList<>();

            for (Payment payment : payments) {
                paymentDTOS.add(converter.getPaymentDTO(payment));
            }
            return (ArrayList<PaymentDTO>) paymentDTOS;
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve payments", e);
        }
    }

    @Override
    public boolean savePayments(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Payment> optionalPayment = paymentDAO.findById(paymentDTO.getPaymentId());
            if (optionalPayment.isPresent()) {
                throw new DuplicateException("Duplicate payment ID: " + paymentDTO.getPaymentId());
            }

            Optional<Student> optionalStudent = studentDAO.findById(paymentDTO.getStudentId());
            if (optionalStudent.isEmpty()) {
                Student newStudent = new Student();
                newStudent.setStudentId(paymentDTO.getStudentId());
                newStudent.setFullName("Student");
                newStudent.setEmail("email@gmail.com");
                newStudent.setPhone("0112233444");
                newStudent.setDob("****-**-**");
                newStudent.setAddress("Address");
                studentDAO.save(newStudent);
            }

            Payment payment = converter.getPayment(paymentDTO);
            return paymentDAO.save(payment);

        } catch (DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to save payment", e);
        }
    }

    @Override
    public boolean updatePayments(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        try {
            Optional<Payment> optionalPayment = paymentDAO.findById(paymentDTO.getPaymentId());
            if (optionalPayment.isEmpty()) {
                throw new NotFoundException("Payment not found with ID: " + paymentDTO.getStudentId());
            }

            Payment payment = converter.getPayment(paymentDTO);
            return paymentDAO.update(payment);

        } catch (NotFoundException | DuplicateException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to update payment", e);
        }
    }

    @Override
    public boolean deletePayments(String id) throws SQLException, ClassNotFoundException {
        try {
            Optional<Payment> optionalPayment = paymentDAO.findById(id);
            if (optionalPayment.isEmpty()) {
                throw new NotFoundException("Payment not found with ID: " + id);
            }

            return paymentDAO.delete(id);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to delete payment", e);
        }
    }

    @Override
    public String generateNewPaymentId() throws SQLException, ClassNotFoundException {
        return getNextId();
    }

    @Override
    public String getNextId() throws SQLException {
        try {
            String lastId = paymentDAO.getLastId();
            char tableChar = 'P';

            if (lastId != null && !lastId.isEmpty()) {
                String lastIdNumberString = lastId.substring(1);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                return String.format(tableChar + "%03d", nextIdNumber);
            }
            return tableChar + "001";
        } catch (Exception e) {
            throw new SQLException("Failed to generate next Payment" + "ID", e);
        }
    }
}

package lk.ijse.elitedrivingschool.bo.custom;

import lk.ijse.elitedrivingschool.bo.SuperBO;
import lk.ijse.elitedrivingschool.dto.LessionDTO;
import lk.ijse.elitedrivingschool.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {

    ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;
    boolean savePayments(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    boolean updatePayments(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    boolean deletePayments(String id) throws SQLException, ClassNotFoundException;
    String generateNewPaymentId() throws SQLException, ClassNotFoundException;
}

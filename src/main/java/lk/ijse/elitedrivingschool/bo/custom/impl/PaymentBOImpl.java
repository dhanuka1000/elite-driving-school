package lk.ijse.elitedrivingschool.bo.custom.impl;

import lk.ijse.elitedrivingschool.bo.custom.PaymentBO;
import lk.ijse.elitedrivingschool.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean savePayments(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updatePayments(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deletePayments(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewPaymentId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }
}

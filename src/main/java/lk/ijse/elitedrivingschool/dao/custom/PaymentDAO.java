package lk.ijse.elitedrivingschool.dao.custom;

import lk.ijse.elitedrivingschool.dao.CrudDAO;
import lk.ijse.elitedrivingschool.entity.Payment;

import java.sql.SQLException;
import java.util.Optional;

public interface PaymentDAO extends CrudDAO<Payment> {
    Optional<Payment> findById(String paymentId) throws SQLException;
}

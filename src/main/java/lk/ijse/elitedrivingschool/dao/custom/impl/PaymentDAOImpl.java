package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.config.FactoryConfiguration;
import lk.ijse.elitedrivingschool.dao.custom.PaymentDAO;
import lk.ijse.elitedrivingschool.entity.Payment;
import lk.ijse.elitedrivingschool.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public List<Payment> getAll() throws SQLException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Query<Payment> query = session.createQuery("FROM Payment ", Payment.class);
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all payment", e);
        }
    }

    @Override
    public String getLastId() throws SQLException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Query<String> query = session.createQuery(
                    "SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC", String.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new SQLException("Failed to get last Payment ID", e);
        }
    }

    @Override
    public boolean save(Payment payment) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            session.persist(payment);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SQLException("Failed to save payment", e);
        }
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            session.merge(payment);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new SQLException("Failed to update payment", e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            TransactionManagement.beginTransaction();

            transaction = session.beginTransaction();
            Payment payment = session.get(Payment.class, id);
            if (payment != null) {
                session.remove(payment);
                transaction.commit();

                TransactionManagement.commitTransaction();
                return true;
            }

            transaction.rollback();
            TransactionManagement.rollbackTransaction();
            return false;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            try {
                TransactionManagement.rollbackTransaction();
            } catch (SQLException ex) {
                System.err.println("Failed to rollback transaction: " + ex.getMessage());
            }
            throw new SQLException("Failed to delete student", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            Query<String> query = session.createQuery(
                    "SELECT p.paymentId FROM Payment p", String.class
            );
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all payment IDs", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Payment> findById(String paymentId) throws SQLException {
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            Payment payment = session.get(Payment.class, paymentId);
            return Optional.ofNullable(payment);
        } catch (Exception e) {
            throw new SQLException("Failed to find payment by ID", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}

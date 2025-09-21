package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.config.FactoryConfiguration;
import lk.ijse.elitedrivingschool.dao.custom.StudentDAO;
import lk.ijse.elitedrivingschool.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> getAll() throws SQLException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all students", e);
        }
    }

    @Override
    public String getLastId() throws SQLException {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Query<String> query = session.createQuery(
                    "SELECT s.studentId FROM Student s ORDER BY s.studentId DESC", String.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new SQLException("Failed to get last student ID", e);
        }
    }

    @Override
    public boolean save(Student student) throws SQLException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();

            TransactionManagement.beginTransaction();

            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();

            TransactionManagement.commitTransaction();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            try {
                TransactionManagement.rollbackTransaction();
            } catch (SQLException ex) {
                System.err.println("Failed to rollback transaction: " + ex.getMessage());
            }
            throw new SQLException("Failed to save student", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Student student) throws SQLException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            TransactionManagement.beginTransaction();

            transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();

            TransactionManagement.commitTransaction();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            try {
                TransactionManagement.rollbackTransaction();
            } catch (SQLException ex) {
                System.err.println("Failed to rollback transaction: " + ex.getMessage());
            }
            throw new SQLException("Failed to update student", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
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
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.remove(student);
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
                    "SELECT s.studentId FROM Student s", String.class
            );
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all student IDs", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Student> findById(String id) throws SQLException {
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        } catch (Exception e) {
            throw new SQLException("Failed to find student by ID", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}

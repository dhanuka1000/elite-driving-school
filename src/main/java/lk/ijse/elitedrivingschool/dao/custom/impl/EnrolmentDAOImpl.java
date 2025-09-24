package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.config.FactoryConfiguration;
import lk.ijse.elitedrivingschool.dao.custom.EnrolmentDAO;
import lk.ijse.elitedrivingschool.entity.Enrolment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class EnrolmentDAOImpl implements EnrolmentDAO {
    @Override
    public List<Enrolment> getAll() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery("FROM Enrolment", Enrolment.class).list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all enrolments", e);
        }
    }

    @Override
    public String getLastId() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery("SELECT e.id FROM Enrolment e ORDER BY e.id DESC", String.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new SQLException("Failed to get last enrolment ID", e);
        }
    }

    @Override
    public boolean save(Enrolment enrolment) throws SQLException {
        Transaction tx = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.persist(enrolment);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new SQLException("Failed to save enrolment", e);
        }
    }

    @Override
    public boolean update(Enrolment enrolment) throws SQLException {
        Transaction tx = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            tx = session.beginTransaction();
            session.merge(enrolment);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new SQLException("Failed to update enrolment", e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Transaction tx = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            tx = session.beginTransaction();
            Enrolment enrolment = session.get(Enrolment.class, id);
            if (enrolment != null) {
                session.remove(enrolment);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new SQLException("Failed to delete enrolment", e);
        }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery("SELECT e.id FROM Enrolment e", String.class).list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all enrolment IDs", e);
        }
    }

    @Override
    public boolean existsByStudentId(String id) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(e) FROM Enrolment e WHERE e.student.studentId = :id", Long.class);
            query.setParameter("id", id);
            return query.uniqueResult() > 0;
        }
    }
}

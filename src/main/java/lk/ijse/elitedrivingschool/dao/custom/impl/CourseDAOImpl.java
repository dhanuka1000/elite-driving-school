package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.config.FactoryConfiguration;
import lk.ijse.elitedrivingschool.dao.custom.CourseDAO;
import lk.ijse.elitedrivingschool.entity.Course;
import lk.ijse.elitedrivingschool.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {
    @Override
    public List<Course> getAll() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<Course> query = session.createQuery("FROM Course ", Course.class);
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all Course", e);
        }
    }

    @Override
    public String getLastId() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery(
                    "SELECT c.courseId FROM Course c ORDER BY c.courseId DESC", String.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new SQLException("Failed to get last Course ID", e);
        }
    }

    @Override
    public boolean save(Course course) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Failed to save course", e);
        }
    }

    @Override
    public boolean update(Course course) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.merge(course);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Failed to update course", e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                session.remove(course);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Failed to delete course", e);
        }
    }

    @Override
    public List<String> getAllIds() throws SQLException {

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery(
                    "SELECT l.courseId FROM Course l", String.class
            );
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all course IDs", e);
        }
    }

    @Override
    public Optional<Course> findById(String courseId) throws SQLException {

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Course course = session.get(Course.class, courseId);
            return Optional.ofNullable(course);
        } catch (Exception e) {
            throw new SQLException("Failed to find lesson by ID", e);
        }
    }
}

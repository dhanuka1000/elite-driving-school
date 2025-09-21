package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.config.FactoryConfiguration;
import lk.ijse.elitedrivingschool.dao.custom.LessonDAO;
import lk.ijse.elitedrivingschool.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LessonDAOImpl implements LessonDAO {

    @Override
    public List<Lesson> getAll() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<Lesson> query = session.createQuery("FROM Lesson", Lesson.class);
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all lessons", e);
        }
    }

    @Override
    public String getLastId() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery(
                    "SELECT l.lessonId FROM Lesson l ORDER BY l.lessonId DESC", String.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new SQLException("Failed to get last lesson ID", e);
        }
    }

    @Override
    public boolean save(Lesson lesson) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.persist(lesson);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Failed to save lesson", e);
        }
    }

    @Override
    public boolean update(Lesson lesson) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.merge(lesson);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Failed to update lesson", e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            Lesson lesson = session.get(Lesson.class, id);
            if (lesson != null) {
                session.remove(lesson);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Failed to delete lesson", e);
        }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery(
                    "SELECT l.lessonId FROM Lesson l", String.class
            );
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all lesson IDs", e);
        }
    }

    @Override
    public Optional<Lesson> findById(String id) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Lesson lesson = session.get(Lesson.class, id);
            return Optional.ofNullable(lesson);
        } catch (Exception e) {
            throw new SQLException("Failed to find lesson by ID", e);
        }
    }
}
package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.config.FactoryConfiguration;
import lk.ijse.elitedrivingschool.dao.custom.UserDAO;
import lk.ijse.elitedrivingschool.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public List<User> getAll() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all users", e);
        }
    }

    @Override
    public String getLastId() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery(
                    "SELECT u.userId FROM User u ORDER BY u.userId DESC", String.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new SQLException("Failed to get last user ID", e);
        }
    }

    @Override
    public boolean save(User user) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // Hash password before saving
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);

            session.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw new SQLException("Failed to save user", e);
        }
    }

    @Override
    public boolean update(User user) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // Hash password only if it is not already hashed
            if (!user.getPassword().startsWith("$2a$")) {
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                user.setPassword(hashedPassword);
            }

            session.merge(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw new SQLException("Failed to update user", e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw new SQLException("Failed to delete user", e);
        }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery(
                    "SELECT u.userId FROM User u", String.class
            );
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to get all user IDs", e);
        }
    }

    @Override
    public Optional<User> findById(String id) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new SQLException("Failed to find user by ID", e);
        }
    }

    public Optional<User> findByUsername(String username) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<User> query = session.createQuery("FROM User u WHERE u.userName = :username", User.class);
            query.setParameter("username", username);
            User user = query.uniqueResult();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

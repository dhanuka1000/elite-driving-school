package lk.ijse.elitedrivingschool.dao.custom.impl;

import lk.ijse.elitedrivingschool.db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManagement {

    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static void beginTransaction() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        threadLocal.set(connection);
    }

    public static void commitTransaction() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null) {
            connection.commit();
            closeConnection();
        }
    }

    public static void rollbackTransaction() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null) {
            connection.rollback();
            closeConnection();
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = DBConnection.getInstance().getConnection();
        }
        return connection;
    }

    private static void closeConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null) {
            connection.setAutoCommit(true);
            connection.close();
            threadLocal.remove();
        }
    }
}

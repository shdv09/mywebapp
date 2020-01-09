package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/gym_db";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private static Connection connection;

    private ConnectionPool() {};

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
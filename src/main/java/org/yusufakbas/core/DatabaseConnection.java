package org.yusufakbas.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/OrderManagement";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "root";

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    private Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return instance;
    }
}

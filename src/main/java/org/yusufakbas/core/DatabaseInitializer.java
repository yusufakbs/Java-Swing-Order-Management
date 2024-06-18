package org.yusufakbas.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.yusufakbas.core.DatabaseConstants.*;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getInstance();
             Statement statement = connection.createStatement()) {

            createTable(statement, CREATE_CUSTOMER_TABLE);
            createTable(statement, CREATE_PRODUCT_TABLE);
            createTable(statement, CREATE_CART_TABLE);
            createTable(statement, CREATE_BASKET_TABLE);
            createTable(statement, CREATE_USER_TABLE);

            System.out.println("Database tables created successfully or already exist.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Statement statement, String createTableSQL) throws SQLException {
        statement.execute(createTableSQL);
    }
}

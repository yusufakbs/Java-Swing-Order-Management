package org.yusufakbas.core;

public class DatabaseConstants {

    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE IF NOT EXISTS Customer (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "type VARCHAR(255)," +
                    "phone VARCHAR(255)," +
                    "mail VARCHAR(255)," +
                    "address TEXT" +
                    ");";

    public static final String CREATE_PRODUCT_TABLE =
            "CREATE TABLE IF NOT EXISTS Product (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "code VARCHAR(255) NOT NULL," +
                    "price INTEGER NOT NULL," +
                    "stock INTEGER NOT NULL" +
                    ");";

    public static final String CREATE_CART_TABLE =
            "CREATE TABLE IF NOT EXISTS Cart (" +
                    "id SERIAL PRIMARY KEY," +
                    "customer_id INTEGER NOT NULL REFERENCES Customer(id)," +
                    "product_id INTEGER NOT NULL REFERENCES Product(id)," +
                    "price DECIMAL(10, 2) NOT NULL," +
                    "date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                    "note TEXT" +
                    ");";

    public static final String CREATE_BASKET_TABLE =
            "CREATE TABLE IF NOT EXISTS Basket (" +
                    "id SERIAL PRIMARY KEY," +
                    "product_id INTEGER NOT NULL REFERENCES Product(id)" +
                    ");";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS \"users\" (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "mail VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL" +
                    ");";
}

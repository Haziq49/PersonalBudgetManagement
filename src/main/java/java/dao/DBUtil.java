/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author haziq
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // Read DB connection info from environment variables
    private static final String JDBC_URL = System.getenv("jdbc:mysql://localhost:3306/personal_budget_db");
    private static final String JDBC_USERNAME = System.getenv("root");
    private static final String JDBC_PASSWORD = System.getenv("");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    /**
     * Get a new database connection.
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (JDBC_URL == null || JDBC_USERNAME == null || JDBC_PASSWORD == null) {
            throw new SQLException("Database environment variables not set.");
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/personal_budget_db", "root", "");
    }
}



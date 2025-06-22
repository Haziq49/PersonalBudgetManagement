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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Checks if a username already exists in the database.
     * @param username the username to check
     * @return true if username exists, false otherwise
     * @throws SQLException
     */
    public boolean isUserExists(String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true if at least one record found
            }
        }
    }

    /**
     * Validates user credentials for login.
     * @param username the username
     * @param password the password (plain text or hashed depending on your setup)
     * @return user ID if valid, -1 if invalid
     * @throws SQLException
     */
    public int validateUser(String username, String password) throws SQLException {
        String sql = "SELECT id, password FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // For demonstration, assuming plain text comparison.
                    // Replace with hash comparison in production.
                    if (password.equals(storedPassword)) {
                        return rs.getInt("id");
                    }
                }
            }
        }
        return -1; // invalid credentials
    }
}


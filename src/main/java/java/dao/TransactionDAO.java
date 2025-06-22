/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author haziq
 */
package dao;

import model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class to handle CRUD operations for Transaction entity.
 */
public class TransactionDAO {
    private final Connection conn;

    public TransactionDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new transaction into the database.
     * @param transaction Transaction object to insert.
     * @return true if insert successful, false otherwise.
     * @throws SQLException if database error occurs.
     */
    public boolean addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, category, amount, transaction_date, description, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getCategory());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setDate(4, new java.sql.Date(transaction.getDate().getTime()));
            stmt.setString(5, transaction.getDescription());
            stmt.setString(6, transaction.getType());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Retrieves a list of transactions for a user with optional filters.
     * @param userId User ID to filter transactions.
     * @param category Optional category filter.
     * @param type Optional type filter (income/expense).
     * @param startDate Optional start date filter.
     * @param endDate Optional end date filter.
     * @return List of matching transactions.
     * @throws SQLException if database error occurs.
     */
    public List<Transaction> getTransactions(int userId, String category, String type, java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE user_id = ?");

        if (category != null && !category.isEmpty()) {
            sql.append(" AND category = ?");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type = ?");
        }
        if (startDate != null) {
            sql.append(" AND transaction_date >= ?");
        }
        if (endDate != null) {
            sql.append(" AND transaction_date <= ?");
        }
        sql.append(" ORDER BY transaction_date DESC");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            stmt.setInt(index++, userId);
            if (category != null && !category.isEmpty()) {
                stmt.setString(index++, category);
            }
            if (type != null && !type.isEmpty()) {
                stmt.setString(index++, type);
            }
            if (startDate != null) {
                stmt.setDate(index++, new java.sql.Date(startDate.getTime()));
            }
            if (endDate != null) {
                stmt.setDate(index++, new java.sql.Date(endDate.getTime()));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setId(rs.getInt("id"));
                    t.setUserId(rs.getInt("user_id"));
                    t.setCategory(rs.getString("category"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setDate(rs.getDate("transaction_date"));
                    t.setDescription(rs.getString("description"));
                    t.setType(rs.getString("type"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * Updates an existing transaction.
     * @param transaction Transaction object with updated data (must include id).
     * @return true if update successful, false otherwise.
     * @throws SQLException if database error occurs.
     */
    public boolean updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transactions SET category = ?, amount = ?, transaction_date = ?, description = ?, type = ? WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transaction.getCategory());
            stmt.setDouble(2, transaction.getAmount());
            stmt.setDate(3, new java.sql.Date(transaction.getDate().getTime()));
            stmt.setString(4, transaction.getDescription());
            stmt.setString(5, transaction.getType());
            stmt.setInt(6, transaction.getId());
            stmt.setInt(7, transaction.getUserId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Deletes a transaction by id and user id.
     * @param transactionId Transaction ID to delete.
     * @param userId User ID to verify ownership.
     * @return true if delete successful, false otherwise.
     * @throws SQLException if database error occurs.
     */
    public boolean deleteTransaction(int transactionId, int userId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setInt(2, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}




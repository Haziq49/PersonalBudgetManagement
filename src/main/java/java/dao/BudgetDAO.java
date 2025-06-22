/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author haziq
 */
package dao;

import model.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    private Connection conn;

    public BudgetDAO(Connection conn) {
        this.conn = conn;
    }

    // Create
    public boolean addBudget(Budget budget) throws SQLException {
        String sql = "INSERT INTO budgets (user_id, category, planned_amount, month) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, budget.getUserId());
            stmt.setString(2, budget.getCategory());
            stmt.setDouble(3, budget.getPlannedAmount());
            stmt.setDate(4, new java.sql.Date(budget.getMonth().getTime()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Retrieve all budgets for user
    public List<Budget> getBudgets(int userId) throws SQLException {
        List<Budget> list = new ArrayList<>();
        String sql = "SELECT * FROM budgets WHERE user_id = ? ORDER BY month DESC, category";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Budget b = new Budget();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setCategory(rs.getString("category"));
                b.setPlannedAmount(rs.getDouble("planned_amount"));
                b.setMonth(rs.getDate("month"));
                list.add(b);
            }
        }
        return list;
    }

    // Update
    public boolean updateBudget(Budget budget) throws SQLException {
        String sql = "UPDATE budgets SET category = ?, planned_amount = ?, month = ? WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, budget.getCategory());
            stmt.setDouble(2, budget.getPlannedAmount());
            stmt.setDate(3, new java.sql.Date(budget.getMonth().getTime()));
            stmt.setInt(4, budget.getId());
            stmt.setInt(5, budget.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deleteBudget(int id, int userId) throws SQLException {
        String sql = "DELETE FROM budgets WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }
    }
}


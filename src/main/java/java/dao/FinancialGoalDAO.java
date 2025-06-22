/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author haziq
 */
package dao;

import model.FinancialGoal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinancialGoalDAO {
    private Connection conn;

    public FinancialGoalDAO(Connection conn) {
        this.conn = conn;
    }

    // Create
    public boolean addGoal(FinancialGoal goal) throws SQLException {
        String sql = "INSERT INTO financial_goals (user_id, goal_name, target_amount, current_amount, deadline, achieved) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, goal.getUserId());
            stmt.setString(2, goal.getGoalName());
            stmt.setDouble(3, goal.getTargetAmount());
            stmt.setDouble(4, goal.getCurrentAmount());
            stmt.setDate(5, new java.sql.Date(goal.getDeadline().getTime()));
            stmt.setBoolean(6, goal.isAchieved());
            return stmt.executeUpdate() > 0;
        }
    }

    // Retrieve
    public List<FinancialGoal> getGoals(int userId) throws SQLException {
        List<FinancialGoal> list = new ArrayList<>();
        String sql = "SELECT * FROM financial_goals WHERE user_id = ? ORDER BY deadline";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FinancialGoal g = new FinancialGoal();
                g.setId(rs.getInt("id"));
                g.setUserId(rs.getInt("user_id"));
                g.setGoalName(rs.getString("goal_name"));
                g.setTargetAmount(rs.getDouble("target_amount"));
                g.setCurrentAmount(rs.getDouble("current_amount"));
                g.setDeadline(rs.getDate("deadline"));
                g.setAchieved(rs.getBoolean("achieved"));
                list.add(g);
            }
        }
        return list;
    }

    // Update
    public boolean updateGoal(FinancialGoal goal) throws SQLException {
        String sql = "UPDATE financial_goals SET goal_name = ?, target_amount = ?, current_amount = ?, deadline = ?, achieved = ? WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, goal.getGoalName());
            stmt.setDouble(2, goal.getTargetAmount());
            stmt.setDouble(3, goal.getCurrentAmount());
            stmt.setDate(4, new java.sql.Date(goal.getDeadline().getTime()));
            stmt.setBoolean(5, goal.isAchieved());
            stmt.setInt(6, goal.getId());
            stmt.setInt(7, goal.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deleteGoal(int id, int userId) throws SQLException {
        String sql = "DELETE FROM financial_goals WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }
    }
}


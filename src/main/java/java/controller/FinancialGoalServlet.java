/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.FinancialGoalDAO;
import model.FinancialGoal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import model.Budget;

//@WebServlet("/goals")
public class FinancialGoalServlet extends HttpServlet {
    private FinancialGoalDAO goalDAO;

    @Override
    public void init() throws ServletException {
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
        if (conn == null) {
            throw new ServletException("DBConnection is not initialized!");
        }
        goalDAO = new FinancialGoalDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<FinancialGoal> goals = goalDAO.getGoals(userId);
            request.setAttribute("goals", goals);
            request.getRequestDispatcher("/WEB-INF/views/goals.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving goals", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                FinancialGoal goal = extractGoalFromRequest(request);
                goal.setUserId(userId);
                goalDAO.addGoal(goal);
                List<FinancialGoal> goals = goalDAO.getGoals(userId);
                request.setAttribute("goals", goals);
            } else if ("update".equals(action)) {
                FinancialGoal goal = extractGoalFromRequest(request);
                goal.setUserId(userId);
                goal.setId(Integer.parseInt(request.getParameter("id")));
                goalDAO.updateGoal(goal);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                goalDAO.deleteGoal(id, userId);
            }
            request.getRequestDispatcher("goals.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException("Error processing goal action", e);
        }
    }

    private FinancialGoal extractGoalFromRequest(HttpServletRequest request) throws Exception {
        FinancialGoal goal = new FinancialGoal();
        goal.setGoalName(request.getParameter("goalName"));
        goal.setTargetAmount(Double.parseDouble(request.getParameter("targetAmount")));
        goal.setCurrentAmount(Double.parseDouble(request.getParameter("currentAmount")));
        String deadlineStr = request.getParameter("deadline"); // yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = sdf.parse(deadlineStr);
        goal.setDeadline(deadline);
        goal.setAchieved("on".equals(request.getParameter("achieved")));
        return goal;
    }
}



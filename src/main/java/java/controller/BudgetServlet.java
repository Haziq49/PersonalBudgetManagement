/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BudgetDAO;
import model.Budget;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.Connection;

//@WebServlet("/budgets")
public class BudgetServlet extends HttpServlet {

    private BudgetDAO budgetDAO;

    @Override
    public void init() throws ServletException {
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
        if (conn == null) {
            throw new ServletException("DBConnection is not initialized!");
        }
        budgetDAO = new BudgetDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Budget> budgets = budgetDAO.getBudgets(userId);
            request.setAttribute("budgets", budgets);
            request.getRequestDispatcher("/WEB-INF/views/budgets.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving budgets", e);
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
                Budget budget = extractBudgetFromRequest(request);
                budget.setUserId(userId);
                budgetDAO.addBudget(budget);
                List<Budget> budgets = budgetDAO.getBudgets(userId);
                request.setAttribute("budgets", budgets);
            } else if ("update".equals(action)) {
                Budget budget = extractBudgetFromRequest(request);
                budget.setUserId(userId);
                budget.setId(Integer.parseInt(request.getParameter("id")));
                budgetDAO.updateBudget(budget);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                budgetDAO.deleteBudget(id, userId);
            }
            request.getRequestDispatcher("budgets.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error processing budget action", e);
        }
    }

    private Budget extractBudgetFromRequest(HttpServletRequest request) throws Exception {
        Budget budget = new Budget();
        budget.setCategory(request.getParameter("category"));
        budget.setPlannedAmount(Double.parseDouble(request.getParameter("plannedAmount")));
        String monthStr = request.getParameter("month"); // yyyy-MM format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date month = sdf.parse(monthStr);
        budget.setMonth(month);
        return budget;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.TransactionDAO;
import model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
    private TransactionDAO transactionDAO;

    @Override
    public void init() throws ServletException {
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
        if (conn == null) {
            throw new ServletException("DBConnection is not initialized!");
        }
        transactionDAO = new TransactionDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Transaction> transactions = transactionDAO.getTransactions(userId, null, null, null, null);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("/WEB-INF/views/insertTransaction.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving transactions", e);
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
                Transaction t = extractTransactionFromRequest(request);
                t.setUserId(userId);
                transactionDAO.addTransaction(t);
                // Refresh list and forward with success attribute
                List<Transaction> transactions = transactionDAO.getTransactions(userId, null, null, null, null);
                request.setAttribute("transactions", transactions);
                request.setAttribute("success", true);
            } else if ("update".equals(action)) {
                Transaction t = extractTransactionFromRequest(request);
                t.setUserId(userId);
                t.setId(Integer.parseInt(request.getParameter("id")));
                transactionDAO.updateTransaction(t);
                // Optionally, set a success attribute here
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                transactionDAO.deleteTransaction(id, userId);
                // Optionally, set a success attribute here
            }
            // Always refresh the transaction list and forward to the same JSP
            List<Transaction> transactions = transactionDAO.getTransactions(userId, null, null, null, null);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("transactions.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error processing transaction action", e);
        }
    }


    private Transaction extractTransactionFromRequest(HttpServletRequest request) throws Exception {
        Transaction t = new Transaction();
        t.setCategory(request.getParameter("category"));
        t.setAmount(Double.parseDouble(request.getParameter("amount")));
        t.setDescription(request.getParameter("description"));
        t.setType(request.getParameter("type"));
        String dateStr = request.getParameter("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        t.setDate(sdf.parse(dateStr));
        return t;
    }
}




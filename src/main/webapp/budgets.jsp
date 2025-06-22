<%-- 
    Document   : budgets
    Created on : 21 Jun 2025, 1:29:10 PM
    Author     : haziq
--%>

<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Budget" %>
<%@ page import="dao.BudgetDAO" %>

<html>
    <head>
        <title>Monthly Budgets</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f7f9fc;
                padding: 20px;
            }
            h2 {
                margin-top: 70px;
                text-align: center;
                font-weight: 400;
                color: #333;
                letter-spacing: 1px;
            }
            .nav {
                position: fixed;
                top: 10px;
                left: 10px;
                background-color: #007bff;
                padding: 10px 15px;
                border-radius: 5px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.15);
                z-index: 1000;
            }
            .nav a {
                color: white;
                text-decoration: none;
                margin-right: 15px;
                font-weight: 600;
                font-size: 14px;
                transition: color 0.3s ease;
            }
            .nav a:hover {
                color: #cce5ff;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                background: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 0 10px #ccc;
            }
            th, td {
                padding: 12px 15px;
                border-bottom: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            tr:hover {
                background-color: #f1f9ff;
            }
            .form-container {
                background: white;
                padding: 20px;
                margin-top: 30px;
                border-radius: 8px;
                box-shadow: 0 0 10px #ccc;
                max-width: 700px;
            }
            input, select {
                padding: 8px;
                margin: 5px 0 15px 0;
                width: 100%;
                box-sizing: border-box;
                border-radius: 4px;
                border: 1px solid #ccc;
            }
            button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 15px;
                border-radius: 4px;
                cursor: pointer;
            }
            button:hover {
                background-color: #0056b3;
            }
            .actions button {
                margin-right: 5px;
            }

        </style>
    </head>
    <body>

        <div class="nav">
            <a href="dashboard.jsp">Dashboard</a>
            <a href="logout.jsp">Logout</a>
        </div>
        <h2>Monthly Budgets</h2>

        <table>
            <thead>
                <tr>
                    <th>Month</th>
                    <th>Category</th>
                    <th>Planned Amount</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
                    BudgetDAO Newbudget = new BudgetDAO(conn);
                    Integer userId = (Integer) request.getSession().getAttribute("userId");
                    List<Budget> currBudget = Newbudget.getBudgets(userId);
                    request.setAttribute("budgets", currBudget);
                    List<Budget> budgets = (List<Budget>) request.getAttribute("budgets");
                    if (budgets != null && !budgets.isEmpty()) {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
                        for (Budget b : budgets) {
                %>
                <tr>
                    <td><%= sdf.format(b.getMonth())%></td>
                    <td><%= b.getCategory()%></td>
                    <td>$<%= String.format("%.2f", b.getPlannedAmount())%></td>
                    <td class="actions">
                        <form method="post" action="BudgetServlet" style="display:inline;">
                            <input type="hidden" name="action" value="delete" />
                            <input type="hidden" name="id" value="<%= b.getId()%>" />
                            <button type="submit" onclick="return confirm('Delete this budget?');">Delete</button>
                        </form>
                        <button onclick="populateEditForm('<%= b.getId()%>', '<%= b.getCategory()%>', '<%= b.getPlannedAmount()%>', '<%= new java.text.SimpleDateFormat("yyyy-MM").format(b.getMonth())%>')">Edit</button>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr><td colspan="4">No budget plans found.</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <div class="form-container">
            <h3 id="form-title">Add New Budget</h3>
            <form method="post" id="budgetForm" action="BudgetServlet">
                <input type="hidden" name="action" value="create" id="formAction" />
                <input type="hidden" name="id" id="budgetId" />
                <label>Category</label>
                <input type="text" name="category" id="category" required />
                <label>Planned Amount</label>
                <input type="number" step="0.01" name="plannedAmount" id="plannedAmount" required />
                <label>Month</label>
                <input type="month" name="month" id="month" required />
                <button type="submit">Submit</button>
                <button type="button" onclick="resetForm()">Cancel</button>
            </form>
        </div>

        <script>
            function populateEditForm(id, category, amount, month) {
                document.getElementById('form-title').innerText = 'Edit Budget';
                document.getElementById('formAction').value = 'update';
                document.getElementById('budgetId').value = id;
                document.getElementById('category').value = category;
                document.getElementById('plannedAmount').value = amount;
                document.getElementById('month').value = month;
                window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});
            }
            function resetForm() {
                document.getElementById('form-title').innerText = 'Add New Budget';
                document.getElementById('formAction').value = 'create';
                document.getElementById('budgetId').value = '';
                document.getElementById('category').value = '';
                document.getElementById('plannedAmount').value = '';
                document.getElementById('month').value = '';
            }
        </script>

    </body>
</html>


<%-- 
    Document   : goals
    Created on : 21 Jun 2025, 1:32:10 PM
    Author     : haziq
--%>

<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FinancialGoal" %>
<%@ page import="dao.FinancialGoalDAO" %>
<html>
<head>
    <title>Financial Goals</title>
    <style>
        /* Basic styling */
        body { font-family: Arial, sans-serif; background: #f7f9fc; padding: 20px; }
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
        table { border-collapse: collapse; width: 100%; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 0 10px #ccc;}
        th, td { padding: 12px 15px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #007bff; color: white; }
        tr:hover { background-color: #f1f9ff; }
        .form-container { background: white; padding: 20px; margin-top: 30px; border-radius: 8px; box-shadow: 0 0 10px #ccc; max-width: 700px; }
        input, select { padding: 8px; margin: 5px 0 15px 0; width: 100%; box-sizing: border-box; border-radius: 4px; border: 1px solid #ccc; }
        button { background-color: #007bff; color: white; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .actions button { margin-right: 5px; }
    </style>
</head>
<body>
    
    <div class="nav">
            <a href="dashboard.jsp">Dashboard</a>
            <a href="logout.jsp">Logout</a>
        </div>
<h2>Financial Goals</h2>

<table>
    <thead>
        <tr>
            <th>Goal Name</th>
            <th>Target Amount</th>
            <th>Current Amount</th>
            <th>Deadline</th>
            <th>Achieved</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <%
        
        List<FinancialGoal> goals = (List<FinancialGoal>) request.getAttribute("goals");
        if (goals != null && !goals.isEmpty()) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy");
            for (FinancialGoal g : goals) {
    %>
        <tr>
            <td><%= g.getGoalName() %></td>
            <td>$<%= String.format("%.2f", g.getTargetAmount()) %></td>
            <td>$<%= String.format("%.2f", g.getCurrentAmount()) %></td>
            <td><%= sdf.format(g.getDeadline()) %></td>
            <td><%= g.isAchieved() ? "Yes" : "No" %></td>
            <td class="actions">
                <form method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="id" value="<%= g.getId() %>" />
                    <button type="submit" onclick="return confirm('Delete this goal?');">Delete</button>
                </form>
                <button onclick="populateEditForm('<%= g.getId() %>', '<%= g.getGoalName() %>', '<%= g.getTargetAmount() %>', '<%= g.getCurrentAmount() %>', '<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(g.getDeadline()) %>', <%= g.isAchieved() %>)">Edit</button>
            </td>
        </tr>
    <%
            }
        } else {
    %>
        <tr><td colspan="6">No financial goals found.</td></tr>
    <%
        }
    %>
    </tbody>
</table>

<div class="form-container">
    <h3 id="form-title">Add New Goal</h3>
    <form method="post" action="FinancialGoalServlet" id="goalForm">
        <input type="hidden" name="action" value="create" id="formAction" />
        <input type="hidden" name="id" id="goalId" />
        <label>Goal Name</label>
        <input type="text" name="goalName" id="goalName" required />
        <label>Target Amount</label>
        <input type="number" step="0.01" name="targetAmount" id="targetAmount" required />
        <label>Current Amount</label>
        <input type="number" step="0.01" name="currentAmount" id="currentAmount" required />
        <label>Deadline</label>
        <input type="date" name="deadline" id="deadline" required />
        <label>
            <input type="checkbox" name="achieved" id="achieved" /> Achieved
        </label>
        <button type="submit">Submit</button>
        <button type="button" onclick="resetForm()">Cancel</button>
    </form>
</div>

<script>
    function populateEditForm(id, goalName, targetAmount, currentAmount, deadline, achieved) {
        document.getElementById('form-title').innerText = 'Edit Goal';
        document.getElementById('formAction').value = 'update';
        document.getElementById('goalId').value = id;
        document.getElementById('goalName').value = goalName;
        document.getElementById('targetAmount').value = targetAmount;
        document.getElementById('currentAmount').value = currentAmount;
        document.getElementById('deadline').value = deadline;
        document.getElementById('achieved').checked = achieved;
        window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});
    }
    function resetForm() {
        document.getElementById('form-title').innerText = 'Add New Goal';
        document.getElementById('formAction').value = 'create';
        document.getElementById('goalId').value = '';
        document.getElementById('goalName').value = '';
        document.getElementById('targetAmount').value = '';
        document.getElementById('currentAmount').value = '';
        document.getElementById('deadline').value = '';
        document.getElementById('achieved').checked = false;
    }
</script>

</body>
</html>


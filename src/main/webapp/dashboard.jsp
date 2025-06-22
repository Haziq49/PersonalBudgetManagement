<%-- 
    Document   : dashboard
    Created on : 12 Jun 2025, 9:46:19 PM
    Author     : haziq
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    if (session == null || session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Personal Budget Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 0;
        }
        /* Navigation Bar Styles */
        .nav {
            position: fixed;       /* Fix it on the viewport */
            top: 10px;            /* Distance from top */
            left: 10px;           /* Distance from left */
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

        /* Container below navbar */
        .container {
            max-width: 800px;
            margin: 100px auto 40px; /* Top margin to avoid navbar overlap */
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px #ccc;
        }
        h1 {
            color: #333;
            margin-top: 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome, <%= username %>!</h1>
    <p>This is your dashboard where you can manage your personal budget.</p>

    <div class="nav">
        <a href="budgets.jsp">Budget</a>
        <a href="transactions.jsp">Transaction</a>
        <a href="goals.jsp">Goal</a>
        <a href="logout.jsp">Logout</a>
    </div>
</div>
</body>
</html>


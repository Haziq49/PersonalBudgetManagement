<%-- 
    Document   : transactions
    Created on : 27 May 2025, 4:10:10 PM
    Author     : haziq
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Transaction" %>
<%@ page import="dao.TransactionDAO" %>
<html>
<head>
    <title>Your Transactions</title>
    <style>
        body {
            background: #f7f9fc;
            font-family: 'Segoe UI', Arial, sans-serif;
            margin: 0;
            padding: 0;
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
        .table-container {
            max-width: 900px;
            margin: 80px auto 40px auto;
            border-radius: 16px;
            background: #fff;
            box-shadow: 0 4px 24px rgba(0,0,0,0.10);
            overflow: hidden;
        }
        h2 {
            margin-top: 70px;
            text-align: center;
            font-weight: 400;
            color: #333;
            letter-spacing: 1px;
        }
        table { border-collapse: collapse; width: 100%; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 0 10px #ccc;}
        th, td { padding: 12px 15px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #007bff; color: white; }
        tr:hover { background-color: #f1f9ff; }
        .badge {
            display: inline-block;
            padding: 4px 16px;
            border-radius: 12px;
            font-size: 13px;
            font-weight: 500;
            letter-spacing: 0.5px;
        }
        .badge.income {
            background: #e6f9ec;
            color: #2ecc71;
        }
        .badge.expense {
            background: #ffeaea;
            color: #e74c3c;
        }
        .amount-positive {
            color: #2ecc71;
            font-weight: 600;
        }
        .amount-negative {
            color: #e74c3c;
            font-weight: 600;
        }
        .desc {
            color: #888;
            font-size: 13px;
        }
        /* Responsive */
        @media (max-width: 900px) {
            .table-container { margin: 80px 10px 40px 10px; }
            table, th, td { font-size: 13px; }
        }
    </style>
</head>
<body>
<div class="nav">
    <a href="dashboard.jsp">Dashboard</a>
    <a href="addTransaction.jsp">Add Transaction</a>
    <a href="logout.jsp">Logout</a>
</div>

<h2>Your Transactions</h2>
<div class="table-container">
    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Category</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
        <%
            Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
            TransactionDAO Newtransaction = new TranctionDAO(conn);
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            List<Transaction> currTransaction = Newtransaction.getTransaction(userId);
            request.setAttribute("transaction", currTransaction);
            List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
            if (transactions != null && !transactions.isEmpty()) {
                for (Transaction t : transactions) {
                    String typeClass = "badge " + ("income".equalsIgnoreCase(t.getType()) ? "income" : "expense");
                    boolean isPositive = t.getAmount() >= 0;
                    String amountClass = isPositive ? "amount-positive" : "amount-negative";
        %>
            <tr>
                <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(t.getDate()) %></td>
                <td><%= t.getCategory() %></td>
                <td>
                    <span class="<%= typeClass %>">
                        <%= t.getType().substring(0,1).toUpperCase() + t.getType().substring(1).toLowerCase() %>
                    </span>
                </td>
                <td>
                    <span class="<%= amountClass %>">
                        <%= isPositive ? "" : "-" %>$<%= String.format("%.2f", Math.abs(t.getAmount())) %>
                    </span>
                </td>
                <td class="desc"><%= t.getDescription() == null ? "" : t.getDescription() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="5" style="text-align:center; color:#aaa;">No transactions found.</td></tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>




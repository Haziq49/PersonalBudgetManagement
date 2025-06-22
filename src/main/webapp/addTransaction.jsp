<%-- 
    Document   : addTransaction
    Created on : 12 Jun 2025, 8:40:45 PM
    Author     : haziq
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Transaction</title>
    <%
        Boolean success = (Boolean) request.getAttribute("success");
        if (success != null && success) {
    %>
    <script>
        alert("Transaction is successful!");
    </script>
    <%
        }
    %>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f7f9fc;
            margin: 0;
            min-height: 100vh;
        }
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
        .main-container {
            max-width: 700px;
            margin: 40px auto;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.10);
            padding: 40px 50px 30px 50px;
        }
        .form-title {
            text-align: center;
            font-size: 2.5rem;
            font-weight: 400;
            color: #333;
            margin-bottom: 40px;
            letter-spacing: 1px;
        }
        form {
            display: grid;
            grid-template-columns: 1fr 2fr;
            grid-gap: 22px 28px;
            align-items: center;
        }
        label {
            font-size: 1rem;
            color: #333;
            font-weight: 500;
            margin-bottom: 0;
            text-align: right;
            padding-right: 10px;
        }
        .required {
            color: #e74c3c;
            margin-left: 2px;
        }
        input[type="text"],
        input[type="number"],
        input[type="date"],
        select {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            background: #f5f5f5;
            font-size: 1rem;
            outline: none;
            margin-bottom: 0;
            transition: border-color 0.2s;
        }
        input[type="text"]:focus,
        input[type="number"]:focus,
        input[type="date"]:focus,
        select:focus {
            border-color: #7b9cff;
            background: #fff;
        }
        .full-width {
            grid-column: 1/3;
        }
        .submit-btn {
            grid-column: 2/3;
            width: 100%;
            padding: 14px 0;
            background: #6c63ff;
            color: #fff;
            font-size: 1.1rem;
            border: none;
            border-radius: 5px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }
        .submit-btn:hover {
            background: #574fd6;
        }
        @media (max-width: 700px) {
            .main-container {
                padding: 25px 10px;
            }
            form {
                grid-template-columns: 1fr;
            }
            label {
                text-align: left;
                padding-right: 0;
                margin-bottom: 6px;
            }
            .submit-btn {
                grid-column: 1/2;
            }
        }
    </style>
    <script>
        function validateForm() {
            let amount = document.forms["transactionForm"]["amount"].value;
            if (isNaN(amount) || amount <= 0) {
                alert("Please enter a valid positive amount.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
        <div class="nav">
            <a href="dashboard.jsp">Dashboard</a>
            <a href="logout.jsp">Logout</a>
        </div>
<div class="main-container">
    <div class="form-title">Add New Transaction</div>
    <form action="TransactionServlet" name="transactionForm" method="post" onsubmit="return validateForm();">
        <input type="hidden" name="action" value="create" />

        <label for="category">Category <span class="required">*</span></label>
        <input type="text" id="category" name="category" required />

        <label for="type">Type <span class="required">*</span></label>
        <select id="type" name="type" required>
            <option value="" disabled selected>Select</option>
            <option value="income">Income</option>
            <option value="expense">Expense</option>
        </select>

        <label for="amount">Amount <span class="required">*</span></label>
        <input type="number" step="0.01" id="amount" name="amount" required />

        <label for="date">Date <span class="required">*</span></label>
        <input type="date" id="date" name="date" required />

        <label for="description">Description</label>
        <input type="text" id="description" name="description" />

        <div></div>
        <input type="submit" class="submit-btn" value="Submit" />
    </form>
</div>
</body>
</html>



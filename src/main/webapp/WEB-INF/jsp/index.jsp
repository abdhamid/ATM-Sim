<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <title>
        ATM Simulation
    </title>
</head>
<body class="app-box">
<div>
    <h2 class="mb-4">ATM Simulation</h2>
    <h5 class="mb-3">Home</h5>

    <h4>Customer name : &nbsp;${customerName}</h4>
    <h4>Balance : &nbsp;${customerBalance}</h4>

    <div class="d-grid gap-2">
        <a class="btn btn-light mb-2" href="/withdraw">Withdraw</a>
        <a class="btn btn-light mb-2" href="/transfer">Transfer</a>
        <a class="btn btn-light mb-2" href="/transaction-history">Transaction History</a>

        <a class="btn btn-primary mt-2" href="/logout">Logout</a>
    </div>

</div>
</body>
</html>
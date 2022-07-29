<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet"--%>
<%--          integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">--%>
<%--    <script async src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"--%>
<%--            integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"--%>
<%--            crossorigin="anonymous"></script>--%>
<%--    <link rel="stylesheet" href="/app.css">--%>

    <title>
        ATM Simulation
    </title>
</head>
<body class="app-box">
<div>
    <h2 class="mb-4">ATM Simulation</h2>
    <h5 class="mb-3">Home</h5>
    <p class="text-danger">${message}</p>
    <p>${welcomeMessage}</p>

    <div class="d-grid gap-2">
        <a class="btn btn-light mb-2" href="/withdraw">Withdraw</a>
        <a class="btn btn-light mb-2" href="/transfer">Transfer</a>
        <a class="btn btn-light mb-2" href="/history">Transaction History</a>

        <a class="btn btn-primary mt-2" href="/logout">Logout</a>
    </div>

</div>
</body>
</html>
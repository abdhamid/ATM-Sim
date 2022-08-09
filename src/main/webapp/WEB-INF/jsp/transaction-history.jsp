<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Abdul_H902
  Date: 28/07/2022
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transaction History</title>
</head>
<body>
<div>
    <h1>Transaction History</h1>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">ID</th>
            <th scope="col">Transaction Type</th>
            <th scope="col">Account Number</th>
            <th scope="col">Amount</th>
            <th scope="col">Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach varStatus="loopCounter" items="${history}" var="transaction">
            <tr>
                <th scope="row"><c:out value="${loopCounter.count}" /></th>
                <td><c:out value="${transaction.id}" /></td>
                <td><c:out value="${transaction.transactionType}" /></td>
                <td><c:out value="${transaction.transactionCreator}" /></td>
                <td><c:out value="${transaction.amount}" /></td>
                <td><c:out value="${transaction.timestamp}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>

</body>
</html>

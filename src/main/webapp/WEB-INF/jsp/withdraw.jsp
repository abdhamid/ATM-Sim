<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Abdul_H902
  Date: 28/07/2022
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Withdraw</title>
</head>
<body>
<div>
    <h1>Withdraw</h1>
    <c:if test="${errorStatus}">
        <div class="text-danger">${errorMessage}</div>
    </c:if>

    <form:form method="post" modelAttribute="amount">
        <h4>Choose withdraw amount</h4>
        <div>
            <form:radiobutton path="amount" value="10"></form:radiobutton>
            <label>10</label>
        </div>
        <div>
            <form:radiobutton path="amount" value="50"></form:radiobutton>
            <label>50</label>
        </div>
        <div>
            <form:radiobutton path="amount" value="100"></form:radiobutton>
            <label>100</label>
        </div>
        <button type="submit">Withdraw</button>
    </form:form>
    <form action="/withdraw-other">
        <input type="submit" value="Withdraw other amount" />
    </form>

</div>

</body>
</html>

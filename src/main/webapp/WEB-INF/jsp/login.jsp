<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Abdul_H902
  Date: 05/08/2022
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ATM Simulation</title>
</head>
<body>
<div class="container">
    <c:if test="${errorStatus}">
        <div class="text-danger">${errorMessage}</div>
    </c:if>

    <form:form action="/login" modelAttribute="login" method="post">
        <div class="form-floating mb-2">
            <form:input path="accNumber" class="form-control"/>
            <form:label path="accNumber">Account Number</form:label>
        </div>
        <div class="form-floating">
            <form:password path="accPin" class="form-control"/>
            <form:label path="accPin">PIN</form:label>
        </div>

        <form:button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</form:button>

    </form:form>
</div>
</body>
</html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Fund Transfer</title>
</head>
<body>
<div>
    <c:if test="${errorStatus}">
        <div class="text-danger">${errorMessage}</div>
    </c:if>

    <form:form action="/transfer" modelAttribute="transfer" method="post">
        <div class="form-floating mb-2">
            <form:label path="accNumber">Receiver Account Number</form:label>
            <form:input path="accNumber" class="form-control"/>
        </div>
        <div class="form-floating">
            <form:label path="amount">Transfer Amount</form:label>
            <form:input path="amount" class="form-control"/>
        </div>

        <form:button class="w-100 btn btn-lg btn-primary" type="submit">Submit</form:button>

    </form:form>
</div>

</body>
</html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Abdul_H902
  Date: 08/08/2022
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Other Withdraw</title>
</head>
<body>
<form:form action="/withdraw-other" method="post" modelAttribute="amount">
    <c:if test="${errorStatus}">
        <div class="text-danger">${errorMessage}</div>
    </c:if>
    <div>
        <form:label path="amount">Enter amount to withdraw:</form:label>
        <form:input path="amount" type="number"/>
    </div>
    <form:button class="btn btn-primary">
        Withdraw
    </form:button>
</form:form>


</body>
</html>

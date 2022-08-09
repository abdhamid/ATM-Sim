<%--
  Created by IntelliJ IDEA.
  User: Abdul_H902
  Date: 09/08/2022
  Time: 7:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transfer Summary</title>
</head>
<body>
<div>
    <h1>Transfer Summary</h1>
    <ul>
        <li>
            <label>Date :&nbsp;</label>
            ${date}
        </li>
        <li>
            <label>Reference ID :&nbsp;</label>
            ${refId}
        </li>
        <li>
            <label>Receiver :&nbsp;</label>
            ${receiver}
        </li>
        <li>
            <label>Transfer Amount :&nbsp;</label>
            ${amount}
        </li>
        <li>
            <label>Remaining Balance :&nbsp;</label>
            ${customerBalance}
        </li>
    </ul>

    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/logout">Logout</a></li>
    </ul>
</div>

</body>
</html>

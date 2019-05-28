<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
<div>
    <c:forEach var="user" items="${requestScope.user}">
        <p>Login: ${user.login} - ${user.password};</p>
    </c:forEach>
</div>
</body>
</html>

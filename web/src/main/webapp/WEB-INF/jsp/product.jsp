<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Store</title>
</head>
<body>
<form name="price" method="get" action="${pageContext.request.contextPath}/products">
    <fieldset>
        <label for="minPrice">
            <input type="text" name="minPrice" id="minPrice" placeholder="Min price">
        </label>
        <label for="maxPrice">
            <input type="text" name="maxPrice" id="maxPrice" placeholder="Max price">
        </label>
        <input type="submit" value="Done"><br>
    </fieldset>
</form>
<form name="limit" method="get" action="${pageContext.request.contextPath}/products">
    <fieldset>
        <label for="limit">
            <input type="text" name="limit" id="limit" placeholder="Limit">
        </label>
        <label for="offset">
            <input type="text" name="offset" id="offset" placeholder="Offset">
        </label>
        <input type="submit" value="Done"><br>
    </fieldset>
</form>
<div>
    <c:forEach var="productExpression" items="${sessionScope.productExpression}">
        <p>Товар: ${productExpression}</p>
    </c:forEach>
</div>
</body>
</html>

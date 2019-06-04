<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Store</title>
</head>
<body>
<form name="price" method="get" action="${pageContext.request.contextPath}/products">
    <fieldset>
        <label>
            <input type="text" name="minPrice" placeholder="Min price" value="${param.minPrice}">
        </label>
        <label>
            <input type="text" name="maxPrice" placeholder="Max price" value="${param.maxPrice}">
        </label>
        <label>
            <input type="text" name="limit" placeholder="Limit" value="${param.limit}">
        </label>
        <label>
            <input type="text" name="offset" placeholder="Offset" value="${param.offset}">
        </label>
        <label>Выберите категорию:
            <select name="category" required>
                <option value="Зелья">Зелья</option>
                <option value="Ингредиенты">Ингредиенты</option>
            </select>
        </label>
        <label>Выберите рейтинг:
            <select name="rating" required>
                <option value="1">1 звезда и выше</option>
                <option value="3">3 звезды и выше</option>
                <option value="5">5 звезд</option>
            </select>
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

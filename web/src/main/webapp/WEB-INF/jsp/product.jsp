<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Store</title>
</head>
<body>
<form method="get" action="${pageContext.request.contextPath}/products">
    <fieldset>
        <label>
            <input type="text" name="minPrice" placeholder="Min price" value="${param.minPrice}">
        </label>
        <label>
            <input type="text" name="maxPrice" placeholder="Max price" value="${param.maxPrice}">
        </label>
        <label>Выберите категорию:
            <select name="category">
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
    <c:forEach var="products" items="${sessionScope.products}">
        <p>Товар: ${products}</p>
    </c:forEach>
</div>
<form method="post" action="${pageContext.request.contextPath}/products">
    <fieldset>
        <div>
            <label>Выберите количество товаров на странице:
                <select name="size">
                    <option value="2">2</option>
                    <option value="4">4</option>
                    <option value="6">6</option>
                </select>
            </label>
        </div>
        <div>
            <label>Выберите страницу:
                <select name="page">
                    <option value="0">1</option>
                    <option value="1">2</option>
                    <option value="2">3</option>
                    <option value="3">4</option>
                </select>
            </label>
        </div>
        <div>
            <input type="submit" value="Done"><br>
        </div>
    </fieldset>
</form>
</body>
</html>

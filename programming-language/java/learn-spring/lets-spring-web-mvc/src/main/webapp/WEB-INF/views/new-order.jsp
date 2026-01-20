<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Order list</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/order/new" method="post">
    <label>
        flavor:
        <select name="flavor">
            <option value="" selected></option>
            <c:forEach items="${flavors}" var="flavor">
                <%-- ${flavor} implicitly toString()--%>
                <option value="${flavor}">${flavor}</option>
            </c:forEach>
        </select>
    </label>
    <br>
    <label>
        scoops:
        <select name="scoops">
            <option value="1" selected>1</option>
            <option value="2">2</option>
            <option value="3">3</option>
        </select>
    </label>
    <br>
    <label>
        toppings:
        <c:forEach items="${toppings}" var="topping">
            <input type="checkbox" name="toppings" value="${topping}">${topping}
        </c:forEach>
    </label>
    <br>
    <input type="submit" value="submit">
</form>

</body>
</html>

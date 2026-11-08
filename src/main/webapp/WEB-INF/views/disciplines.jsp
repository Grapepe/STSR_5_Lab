<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список дисциплин</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="menu">
    <ul>
        <li><a href="${pageContext.request.contextPath}/controller?command=welcome">На главную</a></li>
        <li><a href="${pageContext.request.contextPath}/controller?command=sign_out">Выйти</a></li>
    </ul>
</nav>

<div class="container">
    <h2>Список дисциплин</h2>

    <table border="2">
        <tr>
            <th>ID</th>
            <th>Название дисциплины</th>
            <th>Преподаватель</th>
        </tr>
        <!-- Проходимся по списку, который передал контроллер -->
        <c:forEach items="${disciplines}" var="d">
            <tr>
                <td>${d.id}</td>
                <td>${d.name}</td>
                <td>${d.teacher}</td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${empty disciplines}">
        <p>Список дисциплин пуст.</p>
    </c:if>
</div>
</body>
</html>
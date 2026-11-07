<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Главная страница</title>
</head>
<body>

<nav>
    <a href="${pageContext.request.contextPath}/controller?command=login_page">
        Вход
    </a>

    |

    <a href="${pageContext.request.contextPath}/controller?command=sign_out">
        Выйти
    </a>
</nav>

<h2>Список вашей группы</h2>

<table border="2">
    <tr>
        <th>Имя</th>
        <th>Телефон</th>
        <th>Email</th>
    </tr>

    <c:forEach items="${group}" var="person">
        <tr>
            <td>${person.name}</td>
            <td>${person.phone}</td>
            <td>${person.email}</td>
        </tr>
    </c:forEach>
</table>

<hr>

<p style="color: red;">${errorMessage}</p>

<form accept-charset="UTF-8"
      method="POST"
      action="${pageContext.request.contextPath}/controller?command=add_new_person">

    <h3>Добавить новый контакт</h3>

    <div>
        <input name="nname"
               type="text"
               placeholder="Введите имя"
               required>
    </div>

    <div>
        <input name="nphone"
               type="text"
               placeholder="Введите телефон"
               required>
    </div>

    <div>
        <input name="nemail"
               type="text"
               placeholder="Введите email"
               required>
    </div>

    <div>
        <input value="Добавить" type="submit">
    </div>

</form>

</body>
</html>
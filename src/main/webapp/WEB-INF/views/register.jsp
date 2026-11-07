<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>

    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="container">
    <section id="content">

        <p style="color: red;">${errorRegister}</p>

        <form accept-charset="UTF-8"
              action="${pageContext.request.contextPath}/controller?command=register_new_user"
              method="POST">

            <h1>Регистрация нового пользователя</h1>

            <div>
                <input placeholder="Введите имя"
                       required
                       name="newLoginName"
                       type="text">
            </div>

            <div>
                <input placeholder="Введите пароль"
                       id="password"
                       required
                       name="newPassword"
                       type="password">
            </div>

            <div>
                <input type="submit" value="Зарегистрировать">
            </div>

        </form>
    </section>
</div>

</body>
</html>
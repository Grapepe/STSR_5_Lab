<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Вход в систему</title>

  <link rel="stylesheet"
        type="text/css"
        href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="container">
  <section id="content">

    <p style="color: red;">${errorMessage}</p>

    <form accept-charset="UTF-8"
          action="${pageContext.request.contextPath}/controller?command=login"
          method="POST">

      <h1>Вход в систему</h1>

      <div>
        <input placeholder="Имя"
               required
               id="username"
               name="loginName"
               type="text">
      </div>

      <div>
        <input placeholder="Пароль"
               required
               id="password"
               name="password"
               type="password">
      </div>

      <div>
        <input type="submit" value="Войти">
      </div>

      <div>
        <a href="${pageContext.request.contextPath}/controller?command=registration_page">
          Регистрация
        </a>
      </div>

    </form>
  </section>
</div>

</body>
</html>
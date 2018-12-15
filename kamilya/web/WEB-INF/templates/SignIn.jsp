
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kazan</title>

    <link rel="icon" href="../img/icon.jpg">
    <link rel="js" href="../js/main.js>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>

</head>
<body>
<div id= "header">
    Kazan
</div>
<nav role='navigation'>

    <a class="navbar-logo" href="index.jsp"><img src=../img/icon.jpg height="60" width="60">
    </a>


    <ul>

        <li><a href="index.jsp">Главная</a></li>
        <li><a href="#">Места</a>
            <ul>
                <li><a href="restaurant.jsp">Кафе</a></li>
                <li><a href="exhibitions.jsp">Выставки</a></li>
                <li><a href="kino.jsp">Кино</a></li>
                <li><a href="sport.jsp">Спортивные развлечения</a></li>
            </ul>
        </li>

        <li><a href="signUp.jsp">Регистрация</a></li>
        <li><a href="signIn.jsp">Войти</a></li>
    </ul>
</nav>

<h1 class="reg">Войти</h1>


<form class="form-style-7">
    <ul>

        <li>
            <%--@declare id="email"--%><label for="email">Email</label>
            <input type="email" name="email" maxlength="100">
            <span>Enter a valid email address</span>
        </li>

        <li>
            <%--@declare id="password"--%><label for="password">Пароль</label>
            <input type="password" name="password" maxlength="50">
            <span>Enter your password</span>
        </li>
        <li>
            <input type="submit" value="Send This" >
        </li>
    </ul>
</form>


</body>
</html>




<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kazan</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>

    <link rel="icon" href="../img/icon.jpg">
    <link rel="js" href="../js/main.js>">

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

<form class="form-style-7">
    <ul>
        <li>
            <%--@declare id="name"--%><label for="name">Имя</label>
            <input type="text" name="name" maxlength="100">
            <span>Enter your first name here</span>
        </li>
        <li>
            <%--@declare id="secname"--%><label for="secName">Фамилия</label>
            <input type="text" name="surname" maxlength="100">
            <span>Enter your second name</span>
        </li>

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
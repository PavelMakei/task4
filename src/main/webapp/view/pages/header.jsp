<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 19.04.2022
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>

<!DOCTYPE html>


<head>
    <%-----------------Prevent to return to previous page---------------%>
    <script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function() {
            null
        };
        history.pushState(null, null, document.URL);
    </script>
    <%------------------------------------------%>

    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

<%--    <link rel="stylesheet" href="${absolutePath}/bootstrap/css/bootstrap.min.css">--%>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-black bg-opacity-75">
    <div class="container-fluid">
        <a class="navbar-brand" style="color: goldenrod" href="#">Lighting shop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Переключатель навигации">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" style="color: white" aria-current="page" href="#">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="color: white" href="#">Ссылка</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false" style="color: white">
                        Выпадающий список
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Действие</a></li>
                        <li><a class="dropdown-item" href="#">Другое действие</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="#">Что-то еще здесь</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Отключенная</a>
                </li>
            </ul>


            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Поиск" aria-label="Поиск">
                <button class="btn btn-outline-warning" style="color: white; border-color: orange; " type="submit">
                    Поиск
                </button>
            </form>


            <form class="d-flex" method="get" action="${absolutePath}/controller">
                <input type="hidden" name="command" value="login">
                <button class="btn btn-outline-warning"
                        style="color: black; background-color: white; border-color: orange;border-radius: 100% "
                        type="submit">
                    login
                </button>
            </form>
            <%----------------------------------------Language--------------------------------%>

            <form class="d-flex" method="get" action="${absolutePath}/controller">
                <input type="hidden" name="command" value="change_language">
                <button class="btn btn-outline-warning" style="color: white; border-color: orange" type="submit">
                    <c:choose>
                        <c:when test="${locale eq 'ru_RU'}">English</c:when>
                        <c:when test="${locale eq 'en_US'}">Русский</c:when>
                    </c:choose>
                </button>
            </form>

        </div>
    </div>
</nav>

</body>
</html>

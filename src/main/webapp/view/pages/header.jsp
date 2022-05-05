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

<c:set var="path">${pageContext.request.contextPath}</c:set>

<fmt:message key="admin.product.menu" var="admin_product_menu"/>
<fmt:message key="admin.user.menu" var="admin_user_menu"/>
<fmt:message key="admin.order.menu" var="admin_order_menu"/>
<fmt:message key="add.new.product" var="add_new_product_label"/>
<fmt:message key="show.users" var="show_users"/>
<fmt:message key="show.orders" var="show_orders"/>
<fmt:message key="main.page.label" var="main_page_label"/>
<fmt:message key="about.label" var="about_label"/>
<fmt:message key="log.in" var="log_in"/>
<fmt:message key="log.out" var="log_out"/>




<head>
    <link rel="icon" href="${pageContext.request.contextPath}/icons/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon"${pageContext.request.contextPath}/icons/favicon.ico" type="image/x-icon" />
    <link rel="bookmark" href="${pageContext.request.contextPath}/icons/favicon.ico" type="image/x-icon" />

    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.min.css">
    <script src="${path}/bootstrap/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-black bg-opacity-75">
    <div class="container-fluid">
        <a class="navbar-brand" style="color: goldenrod" href="${path}/index.jsp">Lighting shop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" >
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" style="color: white" aria-current="page" href="${path}/index.jsp">${main_page_label}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="color: white" href="#">${about_label}</a>
                </li>
<%--                ----------TODO add role--%>
                <%@include file="adminproductmenu.jspx"%>
<%--                ---------------%>
<%--                <li class="nav-item">--%>
<%--                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Отключенная</a>--%>
<%--                </li>--%>
            </ul>

<%--            <form class="d-flex">--%>
<%--                <input class="form-control me-2" type="search" placeholder="Поиск" aria-label="Поиск">--%>
<%--                <button class="btn btn-outline-warning" style="color: white; border-color: orange; " type="submit">--%>
<%--                    Поиск--%>
<%--                </button>--%>
<%--            </form>--%>





            <form class="d-flex" method="get" action="${path}/controller">


                    <c:choose>
                        <c:when test="${access_level eq 'ADMIN' or access_level eq 'USER'}">
                        <input type="hidden" name="command" value="logout">
                        <button class="btn btn-outline-warning rounded-pill"
                                style="color: black; background-color: white; border-color: orange;border-radius: 100% "
                                type="submit">
                                ${log_out}
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="command" value="go_to_login_page">
                            <button class="btn btn-outline-warning rounded-pill"
                                    style="color: black; background-color: white; border-color: orange;border-radius: 100% "
                                    type="submit">
                                    ${log_in}
                        </c:otherwise>
                    </c:choose>



                </button>
            </form>
            <%----------------------------------------Language--------------------------------%>

            <form class="d-flex" method="get" action="${path}/controller">
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

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
<c:set var="rate" value="N/D"/>

<fmt:message key="admin.menu" var="admin_menu"/>
<fmt:message key="add.new.product" var="add_new_product_label"/>
<fmt:message key="show.users" var="show_users"/>
<fmt:message key="show.orders" var="show_orders"/>
<fmt:message key="main.page.label" var="main_page_label"/>
<fmt:message key="about.label" var="about_label"/>
<fmt:message key="log.in" var="log_in"/>
<fmt:message key="log.out" var="log_out"/>
<fmt:message key="profile.update" var="update_profile_label"/>
<fmt:message key="deposit.money.label" var="deposit_money_label"/>
<fmt:message key="cart" var="cart_label"/>
<fmt:message key="user.menu" var="user_menu"/>
<fmt:message key="order.management" var="manage_order"/>
<fmt:message key="my.orders" var="my_orders"/>



<head>
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.min.css">
    <link href="${path}/css/enter.css" rel="stylesheet">

    <script src="${path}/bootstrap/js/bootstrap.bundle.min.js"></script>


</head>
<body>
<nav class="navbar navbar-expand navbar-light bg-black bg-opacity-75">
    <div class="container-fluid">
        <a class="navbar-brand" style="color: goldenrod" href="${path}/index.jsp">Lighting shop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <c:if test="${access_level ne 'BLOCKED'}">
                        <a class="nav-link active" style="color: white" aria-current="page"
                           href="${path}/index.jsp">${main_page_label}</a>
                    </c:if>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="color: white"
                       href="${path}/controller?command=go_to_about">${about_label}</a>
                </li>
                <c:if test="${!empty user && user.accessLevel eq 'ADMIN'}">
                    <%@include file="../admin/adminproductmenu.jspx" %>
                </c:if>
                <c:if test="${!empty user && user.accessLevel eq 'USER'}">
                    <%@include file="../user/usermenu.jspx" %>
                </c:if>
            </ul>

            <%--                ---------------%>

            <%--            <li class="nav-item">--%>
            <%--                <a class="nav-link" style="color: white" href="https://www.nbrb.by/">${exchange_course}</a>--%>
            <%--            </li>--%>

            <ul class="nav nav-pills" style="border-color: goldenrod;padding: 6px; border-width: 1px; border-style: solid; border-radius: .25rem;">
                <li class="nav-item" style="color: white;" id="curr_scale"></li>
                <li class="nav-item ">
                    <div class="currency">
                        <select class="bg-black bg-opacity-75 border-0" style="margin-top: 2px; color: goldenrod; cursor: pointer;" id="currency">
                            <option value="AED">AED</option>
                            <option value="ARS">ARS</option>
                            <option value="AUD">AUD</option>
                            <option value="BGN">BGN</option>
                            <option value="BRL">BRL</option>
                            <option value="CAD">CAD</option>
                            <option value="CHF">CHF</option>
                            <option value="CLP">CLP</option>
                            <option value="CNY">CNY</option>
                            <option value="COP">COP</option>
                            <option value="CZK">CZK</option>
                            <option value="DKK">DKK</option>
                            <option value="EGP">EGP</option>
                            <option value="EUR">EUR</option>
                            <option value="GBP">GBP</option>
                            <option value="HKD">HKD</option>
                            <option value="HRK">HRK</option>
                            <option value="HUF">HUF</option>
                            <option value="IDR">IDR</option>
                            <option value="ILS">ILS</option>
                            <option value="INR">INR</option>
                            <option value="ISK">ISK</option>
                            <option value="JPY">JPY</option>
                            <option value="KRW">KRW</option>
                            <option value="KZT">KZT</option>
                            <option value="MXN">MXN</option>
                            <option value="MYR">MYR</option>
                            <option value="NOK">NOK</option>
                            <option value="NZD">NZD</option>
                            <option value="PEN">PEN</option>
                            <option value="PHP">PHP</option>
                            <option value="PKR">PKR</option>
                            <option value="PLN">PLN</option>
                            <option value="RON">RON</option>
                            <option value="RUB">RUB</option>
                            <option value="SAR">SAR</option>
                            <option value="SEK">SEK</option>
                            <option value="SGD">SGD</option>
                            <option value="THB">THB</option>
                            <option value="TRY">TRY</option>
                            <option value="TWD">TWD</option>
                            <option value="UAH">UAH</option>
                            <option value="USD" selected>USD</option>
                            <option value="UYU">UYU</option>
                            <option value="VND">VND</option>
                            <option value="ZAR">ZAR</option>
                        </select>
                    </div>
                </li>
                <li class="nav-item" style="color: white;">
                                    <a class="nav-link" style="color: white; padding: 0;" href="https://www.nbrb.by/" target="_blank" id="rate"></a>
                </li>
            </ul>


            <c:if test="${access_level ne 'BLOCKED'}">
                <button type="button" class="btn btn-primary"
                        onclick="
                                let fullPath = '${path}/controller?command=go_to_show_cart';
                                window.open(fullPath,'ShowCart','width=1400,height=650,left=300,toolbar=no,status=no,resizable=no,location=no,directories=no');
                                "
                >
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-cart4"
                         viewBox="0 0 16 16">
                        <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l.5 2H5V5H3.14zM6 5v2h2V5H6zm3 0v2h2V5H9zm3 0v2h1.36l.5-2H12zm1.11 3H12v2h.61l.5-2zM11 8H9v2h2V8zM8 8H6v2h2V8zM5 8H3.89l.5 2H5V8zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"/>
                    </svg>
                        ${cart_label}
                </button>
            </c:if>
            <form class="d-flex" method="get" action="${path}/controller">

                <c:choose>
                <c:when test="${sessionScope.access_level eq 'ADMIN' or access_level eq 'USER' or access_level eq 'BLOCKED'}">
                <input type="hidden" name="command" value="logout">
                <button class="btn btn-outline-warning"
                        style="color: black; background-color: white; border-width: 2px; border-color: goldenrod;border-radius: .25rem; "
                        type="submit">
                        ${log_out}
                    </c:when>
                    <c:otherwise>
                    <input type="hidden" name="command" value="go_to_login">
                    <button class="btn btn-outline-warning "
                            style="color: black; background-color: white; border-width: 2px; border-color: goldenrod;border-radius: 5% "
                            type="submit">
                            ${log_in}
                        </c:otherwise>
                        </c:choose>

                    </button>
            </form>
            <%----------------------------------------Language--------------------------------%>
            <form class="d-flex" method="get" action="${path}/controller">
                <input type="hidden" name="command" value="change_language">
                <button class="btn btn-outline-warning border-0"
                        type="submit">
                    <c:choose>
                        <c:when test="${locale eq 'ru_RU'}">
                            <img src="${path}/icons/united-kingdom.png" width="20" alt="png"/>
                        </c:when>
                        <c:when test="${locale eq 'en_US'}"><img src="${path}/icons/russia.png" width="20"
                                                                 alt="png"/></c:when>
                    </c:choose>
                </button>
            </form>
        </div>
    </div>
</nav>

<script src="${path}/js/header.js"></script>

</body>
</html>

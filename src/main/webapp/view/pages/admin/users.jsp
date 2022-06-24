<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 22.05.2022
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>

<c:set var="path">${pageContext.request.contextPath}</c:set>

<fmt:message key="first.name" var="first_name"/>
<fmt:message key="last.name" var="last_name"/>
<fmt:message key="login" var="login"/>
<fmt:message key="mobile.phone" var="mobile_phone"/>
<fmt:message key="access.level" var="access_level"/>
<fmt:message key="registration.date" var="registration_date"/>
<fmt:message key="money.amount" var="money_amount"/>
<fmt:message key="users" var="users_lable"/>
<fmt:message key="purchased.products" var="purchased_products"/>
<fmt:message key="for.the.amount" var="for_the_amount"/>

<c:set var="email">email</c:set>
<c:set var="url_part1">${path}/controller?command=update_access_level&id=</c:set>
<c:set var="url_part2">&access_level=</c:set>

<html>
<head>
    <title>${users_lable}</title>
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/enter.css">


    <%-----------------Prevent to return to previous page---------------%>
    <script>
        function preventBack() {
            window.history.forward();
        }

        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null
        };
        history.pushState(null, null, document.URL);
    </script>
    <%------------------------------------------%>


</head>
<body>
<div class="wrapper">
    <div class="header">
        <%@include file="../common/header.jsp" %>
    </div> <!-- end of header -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large">
                            ${users_lable}
                            <c:if test="${!empty message}">
                                <p style="color: goldenrod">
                                    <fmt:message key="${message}"></fmt:message>
                                </p>
                            </c:if>
                            ${head_label}
                        </div>
                        <div class="card-body bg-dark bg-opacity-75">
                            <%------------------------------------------------------Users-----------------------------------------------%>
                            <div class="form-group bg-white" style="color: white">
                                <table class="table table-responsive table-bordered">
                                    <thead>
                                    <tr>
                                        <th scope="col">id</th>
                                        <th scope="col">${first_name}</th>
                                        <th scope="col">${last_name}</th>
                                        <th scope="col">${login}</th>
                                        <th scope="col">${email}</th>
                                        <th scope="col">${mobile_phone}</th>
                                        <th scope="col">${registration_date}</th>
                                        <th scope="col">${money_amount}</th>
                                        <th scope="col">${purchased_products}</th>
                                        <th scope="col">${for_the_amount}</th>
                                        <th scope="col">${access_level}</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user_elem" items="${user_list}">
                                        <tr
                                                <c:if test="${user_elem.key.accessLevel eq 'ADMIN'}">
                                                    class="table-warning"
                                                </c:if>
                                                <c:if test="${user_elem.key.accessLevel eq 'USER'}">
                                                    class="table-success"
                                                </c:if>
                                                <c:if test="${user_elem.key.accessLevel eq 'BLOCKED'}">
                                                    class="table-danger"
                                                </c:if>
                                        >
                                            <th scope="row">${user_elem.key.id}</th>
                                            <td>${user_elem.key.firstName}</td>
                                            <td>${user_elem.key.lastName}</td>
                                            <td>${user_elem.key.login}</td>
                                            <td>${user_elem.key.email}</td>
                                            <td>${user_elem.key.phone}</td>
                                            <fmt:parseDate value="${user_elem.key.date}" pattern="yyyy-MM-dd'T'HH:mm"
                                                           var="parsedDateTime" type="both"/>
                                            <td><fmt:formatDate pattern="dd.MM.yyyy" value="${parsedDateTime}"/></td>
                                            <td><fmt:formatNumber value="${user_elem.key.amount}" maxFractionDigits="2" minFractionDigits="2"/></td>
                                            <td><fmt:formatNumber value="${user_elem.value[0]}" maxFractionDigits="0"/></td>
                                            <td><fmt:formatNumber value="${user_elem.value[1]}" maxFractionDigits="2" minFractionDigits="2"/></td>

                                            <td>
                                                <select
                                                        id="access_level" name="access_level" required
                                                        onchange="this.options[this.selectedIndex].value && (window.location
                                                                = '${url_part1}'+this.options[this.selectedIndex].id
                                                                +'${url_part2}'+this.options[this.selectedIndex].value);">
                                                    <option
                                                            id="${user_elem.key.id}"
                                                            value="${user_elem.key.accessLevel}"
                                                            selected>${user_elem.key.accessLevel}
                                                    </option>
                                                    <c:forEach var="access_level_elem"
                                                               items="${access_level_list}">
                                                        <c:if test="${user_elem.key.accessLevel ne access_level_elem}">
                                                            <option value="${access_level_elem}"
                                                                    id="${user_elem.key.id}"
                                                            >${access_level_elem}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>

</body>
</html>
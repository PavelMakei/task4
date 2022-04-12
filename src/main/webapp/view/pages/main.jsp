<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 30.03.2022
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set> <%--путь к корневой папке проекта--%>
<c:set var="current_page" value="${pageContext.request.requestURI}" scope="session"/> <%--ссылка на текущую страницу--%>

<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}"
                                                         scope="session"/></c:when><%--если аттрибут language не пустой, установить локаль из этого аттрибута--%>
    <c:when test="${empty language}"> <fmt:setLocale value="ru_RU"
                                                     scope="session"/></c:when><%--если пустой, установить локаль ru_RU--%>
</c:choose>
<fmt:setBundle basename="language_text"/> <%--локаль будет тянуть из language.text проперти--%>

<c:choose>
    <c:when test="${empty user}"> <c:set var="access_level" scope="session"
                                         value="GUEST"/></c:when><%--если пользователь не установлен, установить уровень доступа GUEST--%>
</c:choose>


<!DOCTYPE html>
<head>
    <title>Main</title>
</head>
<body>

<c:if test="${sessionScope.access_level == 'GUEST'}">
    <jsp:forward page="login.jsp"/>
</c:if>
<c:if test="${sessionScope.access_level == 'ADMIN' || sessionScope.access_level == 'USER'}">


    <br>
    <form action="controller" method="post">
        <table>
            <tbody>
            <tr>
                <input type="hidden" name="command" value="logout">
                First Name = ${sessionScope.user.firstName}
                <br/>
                Last Name = ${sessionScope.user.lastName}
                <br/>
                Access level = ${sessionScope.user.accessLevel}
                <br/>
                <td><input type="submit" value=
                    <fmt:message key="logout.button"/> </td>
            </tr>
            </tbody>
        </table>
    </form>
</c:if>




</body>

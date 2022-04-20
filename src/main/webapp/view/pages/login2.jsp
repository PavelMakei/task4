<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ft" uri="lightingshoptags" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>



<link rel="stylesheet" href="css/login.css">



<html>
<body>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->

    <title><fmt:message key="login.page"/></title>
</head>

<div class="wrapper">

    <div class="content">здесь контент

        <img src="${absolutePath}/images/bulb.jpg" alt="200x200" class="img-rounded" style="width: 200px; height: 200px;">
        <img src="${absolutePath}/images/bulb.jpg" class="img-thumbnail" alt="300x300" style="width: 300px; height: auto">
<%--        <img src="..." alt="..." class="img-circle">--%>
<%--        <img src="..." alt="..." class="img-thumbnail">--%>

    </div>

    <div class="footer"><ft:footerTag/></div>

</div>

</body>
</html>

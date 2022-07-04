<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 05.04.2022
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path">${pageContext.request.contextPath}</c:set>


<html lang="en">


<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Error 403</title>
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.min.css">
</head>


<body>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">403</h1>
        <p class="fs-3"><span class="text-danger">Sorry</span> Access is forbidden.</p>
        <p class="lead">
            You are not allowed to do this.
        </p>
        <a href="${path}/index.jsp" class="btn btn-primary">Return Home</a>
    </div>
</div>
</body>
</html>

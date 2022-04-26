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

<%--<fmt:setLocale value="${locale}" scope="session"/>--%>
<%--<fmt:setBundle basename="language_text"/>--%>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>





<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">--%>

<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>--%>

    <link  rel="stylesheet" href="${absolutePath}/bootstrap/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a href="" class="navbar-brand">WebDewBlog</a>
        <button class="navbar-toggler" productType="button" data-toggle="collapse" data-target="#navbarContent"
                aria-controls="navbarContent" aria-expanded="false">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
             <ul class="navbar-nav mr-auto mb-2">
                 <li class="nav-item">
                   <a  href="" class="nav-link">Home</a>
                 </li>
                 <li class="nav-item">
                     <a  href="" class="nav-link">About</a>
                 </li>
                 <li class="nav-item">
                     <a  href="" class="nav-link">Delivery</a>
                 </li>
                 <li class="nav-item">
                     <a  href="" class="nav-link">Blog</a>
                 </li>
                 <li class="nav-item">
                 <a  href="" class="nav-link">Language</a>
             </li>
             </ul>
    </div>
    </div>
</nav>

</body>
</html>

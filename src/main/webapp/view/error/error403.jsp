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


<html>
<head>
    <title>Error 403</title>
</head>
<body>
<link rel="stylesheet" href="${path}css/error.css">
<br>
Request From -> ${pageContext.errorData.requestURI}
<hr/>
Reason -> ${message}
<hr/>
Exception -> ${pageContext.exception}
<hr/>
Exception Status -> ${pageContext.errorData.statusCode}
<hr/>
Servlet Name -> ${pageContext.errorData.servletName}
<hr/>
<a href="${path}/index.jsp">backToStartPage</a>
</body>
</html>


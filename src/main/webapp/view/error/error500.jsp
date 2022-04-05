<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 05.04.2022
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Error 500</title>
</head>
<br>
<body>
<br>
Request From -> ${pageContext.errorData.requestURI}
<hr/>
Exception -> ${pageContext.exception}
<hr/>
Exception Status -> ${pageContext.errorData.statusCode}
<hr/>
Servlet Name -> ${pageContext.errorData.servletName}
<hr/>
<a href="${pageContext.request.contextPath}/index.jsp">returnToStartPage</a>
</body>
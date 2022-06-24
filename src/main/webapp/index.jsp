<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 09.04.2022
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path">${pageContext.request.contextPath}</c:set>

<head>
    <title>Index</title>
</head>
<body>
<c:if test="${sessionScope.access_level eq 'BLOCKED'}">
    <jsp:forward page="${path}/view/pages/common/blockeduser.jsp"/>
</c:if>
<jsp:forward page="/controller?command=go_to_main"/>
</body>

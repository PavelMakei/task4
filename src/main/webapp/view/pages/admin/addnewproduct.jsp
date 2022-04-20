<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 19.04.2022
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>

<link rel="stylesheet" href="${absolutePath}/css/enter.css">

<!DOCTYPE html>

<head>
    <title><title><fmt:message key="add.new.product"/></title>
    <link href="${absolutePath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <%--    TODO Check if Admin?!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--%>
    <%--    TODO Not ready!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--%>

</head>
<body>
<div class="wrapper">
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card">

                        <div class="card-header" style="text-align:center;"><fmt:message key="add.new.product"/></div>
                        <div class="card-body">

                            <form class="form-horizontal" method="post" action="${absolutePath}/controller">
                                <input type="hidden" name="command" value="addnewproduct">

                                <div class="form-group">
                                    <label for="first_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_first_name}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="first.name"/>
                                        <c:if test="${!empty invalid_first_name}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                       aria-hidden="true"></i></span>
                                            <input type="text" class="form-control" name="first_name" id="first_name"
                                                    <c:if test="${!empty first_name}">
                                                        value="${first_name}"
                                                    </c:if>
                                                   placeholder="<fmt:message key="name.pattern"/>"
                                                   required pattern="^[A-Za-zА-Яа-я]{3,20}$"/>
                                        </div>
                                    </div>
                                </div>




                                <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                    <fmt:message
                                            key="register"/></button>

                                <%--                        //TODO куда переходить?--%>


                                <div class="login-register">
                                    <a href="${absolutePath}/index.jsp"><fmt:message key="return.main.page"/></a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer"><ft:footerTag/></div>
</div>

</body>
</html>

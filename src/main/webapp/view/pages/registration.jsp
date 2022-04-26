<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 12.04.2022
  Time: 14:15
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

<html>
<head>

<%--    <script>--%>
<%--        function preventBack() {--%>
<%--            window.history.forward();--%>
<%--        }--%>

<%--        setTimeout("preventBack()", 0);--%>
<%--        window.onunload = function() {--%>
<%--            null--%>
<%--        };--%>
<%--    </script>--%>

    <link href="${absolutePath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">



<%--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--%>
<%--    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>--%>
<%--    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>
    <!------ Include the above in your HEAD tag ---------->

    <title><fmt:message key="registration.user"/></title>
</head>
<body>

<div class="wrapper">
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">

                        <div class="card-header" style="text-align:center;"><fmt:message key="registration.user"/></div>
                        <div class="card-body">

                            <form class="form-horizontal" method="post" action="${absolutePath}/controller">
                                <input type="hidden" name="command" value="registration">
                                <div class="form-group">
                                    <label for="first_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_first_name}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="first.name"/>
                                        <c:if test="${!empty invalid_first_name}">
                                            <fmt:message key="incorrect.it.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
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

                                <div class="form-group">
                                    <label for="last_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_last_name}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="last.name"/>
                                        <c:if test="${!empty invalid_last_name}">
                                            <fmt:message key="incorrect.it.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <input type="text" class="form-control" name="last_name" id="last_name"
                                                   placeholder="<fmt:message key="name.pattern"/>"
                                                    <c:if test="${!empty last_name}">
                                                        value="${last_name}"
                                                    </c:if>
                                                   required pattern="^[A-Za-zА-Яа-я]{3,20}$"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="login" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_login ||!empty busy_login }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="login"/>
                                        <c:if test="${!empty invalid_login}">
                                            <fmt:message key="incorrect.he.enter"/>
                                        </c:if>
                                        <c:if test="${!empty busy_login}">
                                            <fmt:message key="exists.choose.other"/>
                                        </c:if>
                                    </label>

                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <input type="text" class="form-control" name="login" id="login"
                                                   placeholder="<fmt:message key="login.pattern"/>"
                                                    <c:if test="${!empty login}">
                                                        value="${login}"
                                                    </c:if>
                                                   required pattern="^[A-Za-zА-Яа-я0-9_]{4,16}$"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="email" class="cols-sm-2 control-label"
                                    <c:if test="${!empty invalid_email ||!empty busy_email }">
                                           style="color: red"
                                    </c:if>
                                    >
                                        <fmt:message key="email"/>
                                        <c:if test="${!empty invalid_email}">
                                            <fmt:message key="incorrect.he.enter"/>
                                        </c:if>
                                        <c:if test="${!empty busy_email}">
                                            <fmt:message key="exists.choose.other"/>
                                        </c:if>
                                    </label>
                                        <div class="cols-sm-10">
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-envelope fa"
                                                                                   aria-hidden="true"></i></span>
                                                <input type="email" class="form-control" name="email" id="email"
                                                       placeholder="example@mail.com"
                                                        <c:if test="${!empty email}">
                                                            value="${email}"
                                                        </c:if>
                                                       required
                                                       pattern="^[^[\d\.]][A-Za-z\.\d]{1,30}@[a-z]{2,10}\.([a-z]{2,4}|[a-z]{2,4}\.[a-z]{2,4})$"/>
                                            </div>
                                        </div>
                                </div>

                                <div class="form-group">
                                    <label for="password" class="cols-sm-2 control-label"><fmt:message key="password"/>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                               aria-hidden="true"></i></span>
                                            <input type="password" class="form-control" name="password" id="password"
                                                   placeholder="<fmt:message key="password.pattern"/>"
                                                   required pattern="^[A-Za-zА-Яа-я0-9_!@#,\.]{6,16}$"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="phone" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_phone ||!empty busy_phone }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="mobile.phone"/>
                                        <c:if test="${!empty invalid_phone}">
                                            <fmt:message key="incorrect.he.enter"/>
                                        </c:if>
                                        <c:if test="${!empty busy_phone}">
                                            <fmt:message key="exists.choose.other"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                               aria-hidden="true"></i></span>
                                            <input type="tel" class="form-control" name="phone" id="phone"
                                                   placeholder="(025,029,044)1234567"
                                                    <c:if test="${!empty phone}">
                                                        value="${phone}"
                                                    </c:if>
                                                   required pattern="^\((025|029|044)\)\d{7}$"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group ">

                                    <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                        <fmt:message
                                                key="register"/></button>
                                </div>

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

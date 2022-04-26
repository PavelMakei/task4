<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 03.04.2022
  Time: 4:51
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
    <%--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--%>
    <%--    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>--%>
    <%--    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>
    <!------ Include the above in your HEAD tag ---------->

        <link href="${absolutePath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<%--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--%>

    <title><fmt:message key="login.page"/></title>
</head>
<body>
<div class="wrapper">
    <div class="header">
        <%@include file="header.jsp" %>
    </div> <!-- end of header -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card">
                        <div class="card-header bg-light" style="text-align:center; color: black; font-size: large"><fmt:message
                                key="login.welcome"/></div>

                        <c:if test="${!empty invalid_login_or_password_message}">
                             <div class="bg-dark bg-opacity-75"
                             type="text" style="text-align:center;color: red"
                                   ><fmt:message key="incorrect.login"/>
                             </div>
                        </c:if>

                        <div class="card-body bg-dark bg-opacity-75">
                            <form class="form-horizontal" method="post" action="${absolutePath}/controller">
                                <input type="hidden" name="command" value="login">

                                <div class="form-group">
                                    <label for="login" class="cols-sm-2 control-label" style="color: white">
                                        <fmt:message key="login"/>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                            <input type="text" class="form-control" name="login" id="login"
                                                   placeholder="<fmt:message key="enter.login"/>"
                                                   required pattern="^[A-Za-zА-Яа-я0-9_]{4,16}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="password" class="cols-sm-2 control-label"
                                           style="color: white"><fmt:message
                                            key="password"/></label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                           aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                            <input type="password" class="form-control" name="password" id="password"
                                                   placeholder="<fmt:message key="enter.password"/>"
                                                   required pattern="^[A-Za-zА-Яа-я0-9_!@#,\.]{6,16}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="d-grid gap-2">

<%--                                    <button class="btn btn-primary" type="button">Button</button>--%>
                                    <button type="submit" class="btn btn-primary btn-warning "
                                            style="background-color: goldenrod; color: white">
                                        <fmt:message
                                                key="enter.button"/></button>
                                    </div>

                                <%--                        //TODO куда переходить?--%>


                                <div class="login-register" >
                                    <a href="${absolutePath}/index.jsp" style="color: goldenrod"><fmt:message key="return.main.page"/></a>
<%--                                    todo через контроллер?--%>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer"style="color: white"><ft:footerTag/></div>
</div>

</body>
</html>
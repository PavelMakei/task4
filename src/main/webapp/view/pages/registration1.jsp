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
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>




<html>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->

    <title><fmt:message key="registration.user"/></title>
</head>
<body>


<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="registration.user"/></div>
                <div class="card-body">

                    <form class="form-horizontal" method="post" action="controller">
                        <input type="hidden" name="command" value="registration">
                        <div class="form-group">
                            <label for="first_name" class="cols-sm-2 control-label"><fmt:message key="first.name"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="first_name" id = "first_name" placeholder="<fmt:message key="name.pattern"/>"
                                           required pattern="^[A-Za-zА-Яа-я]{3,20}$"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="last_name" class="cols-sm-2 control-label"><fmt:message key="last.name"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="last_name" id="last_name" placeholder="<fmt:message key="name.pattern"/>"
                                           required pattern="^[A-Za-zА-Яа-я]{3,20}$"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="cols-sm-2 control-label"><fmt:message key="email"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                    <input type="email" class="form-control" name="email" id="email" placeholder="<fmt:message key="enter.your.email" />"
                                           required pattern="^[A-Za-z0-9\.]{1,30}@[a-z]{2,7}\.[a-z]{2,4}$"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="login" class="cols-sm-2 control-label"><fmt:message key="login" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="login" id="login" placeholder="<fmt:message key="enter.login"/>"
                                           required pattern="^[A-Za-zА-Яа-я0-9_]{4,16}$"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="cols-sm-2 control-label"><fmt:message key="password"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="password.pattern"/>"
                                           required pattern="^[A-Za-zА-Яа-я0-9\.]{5,40}$"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="cols-sm-2 control-label"><fmt:message key="mobile.phone" /></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="tel" class="form-control" name="phone" id="phone" placeholder="(025,029,044)1234567"
                                           required pattern="(029|025|044)\d{7}"/>
                                    <div id="passHelp" class="form-text">some message</div>
                                </div>
                            </div>
                        </div>

<%--                        <div class="form-group">--%>
<%--                            <label class="form-label">${user_pass}</label>--%>
<%--                            <input type="password" name="password" class="form-control form-control-sm" placeholder="${e_password}" required pattern="^[A-Za-zА-Яа-я0-9\.]{5,40}$">--%>
<%--                            <div id="passHelp" class="form-text"><fmt:message key="registration.correct_password"></fmt:message></div>--%>
<%--                            <c:if test="${!empty invalid_password}">--%>
<%--                                <div class="invalid-feedback-backend" style="color: red">--%>
<%--                                    <fmt:message key="${invalid_password}"/>--%>
<%--                                </div>--%>
<%--                            </c:if>--%>
<%--                            <div class="invalid-feedback">--%>
<%--                                <fmt:message key="registration.invalid_password"/>--%>
<%--                            </div>--%>
<%--                        </div>--%>

                        <div class="form-group ">
                            <button type="button" class="btn btn-primary btn-lg btn-block login-button" onclick="this.form.submit()" ><fmt:message key="register"/></button>
                        </div>
                        <div class="login-register">
                            <a href="index.php">Login</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>


</body>
</html>

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


    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <title><fmt:message key="login.page"/></title>
</head>
<body>
<div class="wrapper">
    <div class="content">
        <div class="container">

            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header"><fmt:message key="login.welcome"/></div>

                        <c:if test="${!empty invalid_login_or_password_message}">

                            <input type="text" style="text-align:center;color: red"
                                   value="<fmt:message key="incorrect.login"/>"
                                   disabled="disabled">
                        </c:if>

                        <div class="card-body">
                            <form class="form-horizontal" method="post" action="${absolutePath}/controller">
                                <input type="hidden" name="command" value="login">

                                <div class="form-group">
                                    <label for="login" class="cols-sm-2 control-label">
                                        <fmt:message key="login"/>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <input type="text" class="form-control" name="login" id="login"
                                                   placeholder="<fmt:message key="enter.login"/>"
                                                   required pattern="^[A-Za-zА-Яа-я0-9_]{4,16}$"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="password" class="cols-sm-2 control-label"><fmt:message
                                            key="password"/></label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                           aria-hidden="true"></i></span>
                                            <input type="password" class="form-control" name="password" id="password"
                                                   placeholder="<fmt:message key="enter.password"/>"
                                                   required pattern="^[A-Za-zА-Яа-я0-9_!@#,\.]{6,16}$"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group ">

                                    <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                        <fmt:message
                                                key="enter.button"/></button>
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
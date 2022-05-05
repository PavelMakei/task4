<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="field.email" var="email"/>
<fmt:message key="field.password" var="password"/>
<fmt:message key="message.email_rules" var="email_rules"/>
<fmt:message key="message.incorrect_login_or_password" var="incorrect_login_or_password"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="reference.back_to_main" var="back_to_main"/>
<fmt:message key="reference.hide_password" var="hide_password"/>
<fmt:message key="reference.show_password" var="show_password"/>
<fmt:message key="title.sing_in" var="title"/>


<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${title}
    </title>
</head>
<body>
<div class="wrapper">
    <%--    <div class="header">--%>
    <%--        <%@include file="header.jsp" %>--%>
    <%--    </div> <!-- end of header -->--%>
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card">
                        <div class="card-header bg-light" style="text-align:center; color: black; font-size: large">
                            <fmt:message
                                    key="login.welcome"/></div>

                        <c:if test="${!empty invalid_login_or_password_message}">
                            <div class="bg-dark bg-opacity-75"
                                 type="text" style="text-align:center;color: red"
                            ><fmt:message key="incorrect.login"/>
                            </div>
                        </c:if>

                        <div class="card-body bg-dark bg-opacity-75">
                            <form class="form-horizontal needs-validation" novalidate method="post"
                                  action="${path}/controller">
                                <input type="hidden" name="command" value="login">

                                <div class="form-group">
                                    <label for="login" class="cols-sm-2 control-label" style="color: white">
                                        <fmt:message key="login"/>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3 ">
                                                <input type="text" class="form-control"  name="login" id="login"
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
                                                <input type="password" class="form-control" name="password"
                                                       id="password"
                                                       placeholder="<fmt:message key="enter.password"/>"
                                                       required pattern="^[A-Za-zА-Яа-я0-9_!@#,\.]{6,16}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="d-grid gap-2">

                                    <%--                                    <button class="btn btn-primary" type="button">Button</button>--%>
                                    <button type="submit" class="btn btn-primary btn-warning"
                                            style="background-color: goldenrod; color: white">
                                        <fmt:message
                                                key="enter.button"/></button>
                                </div>

                                <%--                        //TODO куда переходить?--%>


                                <div class="login-register">
                                    <a href="${path}/index.jsp" style="color: goldenrod"><fmt:message
                                            key="return.main.page"/></a>
                                    <%--                                    todo через контроллер?--%>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>
<script>
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>

</body>
</html>
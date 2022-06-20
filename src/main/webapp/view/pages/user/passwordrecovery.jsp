<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 24.05.2022
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="path">${pageContext.request.contextPath}</c:set>

<c:set var="email_pattern">${validator_pattern.emailPattern}</c:set>
<c:set var="password_pattern">${validator_pattern.passwordPattern}</c:set>

<fmt:message key="password.recovery" var="password_recovery_label"/>
<fmt:message key="incorrect.he.enter" var="incorrect_he_message"/>
<fmt:message key="email" var="email_label"/>
<fmt:message key="activation.code" var="activation_code_label"/>
<fmt:message key="get.activation.code" var="get_activation_code_button"/>
<fmt:message key="enter.new.password" var="password_label"/>
<fmt:message key="password.placeholder" var="password_placeholder"/>
<fmt:message key="change.password" var="update_password_button"/>

<html>
<head>
    <script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null
        };
    </script>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/enter.css">

    <title>${password_recovery_label}</title>
</head>
<body>

<div class="wrapper">
    <div class="header">
        <%@include file="../common/header.jsp" %>
    </div>
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold" style="text-align:center;"
                        >
                            ${password_recovery_label}
                        </div>
                        <div class="card-body bg-dark bg-opacity-75">

                            <form class="form-horizontal needs-validation" method="post"
                                  action="${path}/controller" novalidate>
                                <input id="command_to_send" type="hidden" name="command" value="update_password">
                                <%--                                ---------------------------email----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="email" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_email ||!empty busy_email }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${email_label}
                                        <c:if test="${!empty invalid_email}">
                                            ${incorrect_he_message}
                                        </c:if>

                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-envelope fa"
                                                                                   aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="email" class="form-control
                                                 <c:if test="${!empty invalid_email ||!empty busy_email}">
                                                     is-invalid
                                                 </c:if>
                                                " name="email" id="email"
                                                       placeholder="example@mail.com"
                                                        <c:if test="${!empty email}">
                                                            value="${email}"
                                                        </c:if>
                                                       required
                                                       pattern="${email_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------activation code----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="activation_code" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_activation_code}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${activation_code_label}
                                        <c:if test="${!empty invalid_activation_code}">
                                            ${incorrect_he_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-envelope fa"
                                                                                   aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_activation_code}">
                                                     is-invalid
                                                 </c:if>
                                                " name="activation_code" id="activation_code"
                                                       required
                                                       minlength="10"
                                                       maxlength="10"
                                                />
                                                <button class="btn btn-warning" style="color: white" type="submit"
                                                        onclick="document.getElementById('command_to_send').value ='recovery_send_activation_code';
                                                                   document.getElementById('activation_code').removeAttribute('required');
                                                                   document.getElementById('password').removeAttribute('required');"
                                                >
                                                    <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" id="btn_spinner" style="visibility: hidden"></span>
                                                    ${get_activation_code_button}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------password-------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="password" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_password}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${password_label}
                                        <c:if test="${!empty invalid_password}">
                                            ${incorrect_he_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                               aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="password" class="form-control
                                                 <c:if test="${!empty invalid_password}">
                                                     is-invalid
                                                 </c:if>
                                                       " name="password"
                                                       id="password"
                                                       placeholder="${password_placeholder}"
                                                       required pattern="${password_pattern}"
                                                        <c:if test="${!empty password}">
                                                            value="${password}"
                                                        </c:if>
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------button---------------------------------%>
                                <div class="d-grid gap-1">
                                    <button type="submit" class="btn btn-primary btn-warning "
                                            style="color: white">
                                        ${update_password_button}</button>
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
<%@include file="../parts/modalwindow.jsp" %>

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
                    }else {
                        document.getElementById('btn_spinner').style.visibility = 'visible';
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>

</body>
</html>

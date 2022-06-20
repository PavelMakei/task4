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
<c:set var="path">${pageContext.request.contextPath}</c:set>

<c:set var="name_pattern">${validator_pattern.namePattern}</c:set>
<c:set var="login_pattern">${validator_pattern.loginPattern}</c:set>
<c:set var="email_pattern">${validator_pattern.emailPattern}</c:set>
<c:set var="password_pattern">${validator_pattern.passwordPattern}</c:set>
<c:set var="phone_pattern">${validator_pattern.phonePattern}</c:set>


<fmt:message key="registration.user" var="registration_label"/>
<fmt:message key="first.name" var="first_name_label"/>
<fmt:message key="incorrect.he.enter" var="incorrect_he_message"/>
<fmt:message key="incorrect.she.enter" var="incorrect_she_message"/>
<fmt:message key="incorrect.it.enter" var="incorrect_it_message"/>
<fmt:message key="name.placeholder" var="name_placeholder"/>
<fmt:message key="last.name" var="last_name_label"/>
<fmt:message key="login" var="login_label"/>
<fmt:message key="exists.choose.other" var="exists_choose_other"/>
<fmt:message key="login.placeholder" var="login_placeholder"/>
<fmt:message key="email" var="email_label"/>
<fmt:message key="password" var="password_label"/>
<fmt:message key="password.placeholder" var="password_placeholder"/>
<fmt:message key="mobile.phone" var="phone_label"/>
<fmt:message key="accept.rules" var="accept_rules_label"/>
<fmt:message key="register" var="update_button"/>
<fmt:message key="return.main.page" var="return_main_page"/>
<fmt:message key="activation.code" var="activation_code_label"/>
<fmt:message key="get.activation.code" var="get_activation_code_button"/>

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

    <title>${registration_label}</title>
</head>
<body>

<div class="wrapper">
    <div class="header">
        <%@include file="header.jsp" %>
    </div>
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold" style="text-align:center; color: black;"
                        >
                            ${registration_label}
                        </div>
                        <div class="card-body bg-dark bg-opacity-75">

                            <form class="form-horizontal needs-validation" method="post"
                                  action="${path}/controller" novalidate>
                                <input id="command_to_send" type="hidden" name="command" value="registration">
                                <%--                                ---------------------------first name-----------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="first_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_first_name}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${first_name_label}
                                        <c:if test="${!empty invalid_first_name}">
                                            ${incorrect_it_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_first_name}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="first_name"
                                                       id="first_name"
                                                        <c:if test="${!empty first_name}">
                                                            value="${first_name}"
                                                        </c:if>
                                                       placeholder="${name_placeholder}"
                                                       required pattern="${name_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------last name------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="last_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_last_name}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${last_name_label}
                                        <c:if test="${!empty invalid_last_name}">
                                            ${incorrect_she_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_last_name}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="last_name" id="last_name"
                                                       placeholder="${name_placeholder}"
                                                        <c:if test="${!empty last_name}">
                                                            value="${last_name}"
                                                        </c:if>
                                                       required pattern="${name_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------login----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="login" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_login ||!empty busy_login }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${login_label}
                                        <c:if test="${!empty invalid_login}">
                                            ${incorrect_he_message}
                                        </c:if>
                                        <c:if test="${!empty busy_login}">
                                            ${exists_choose_other}
                                        </c:if>
                                    </label>

                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_login ||!empty busy_login}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="login" id="login"
                                                       placeholder="${login_placeholder}"
                                                        <c:if test="${!empty login}">
                                                            value="${login}"
                                                        </c:if>
                                                       required pattern="${login_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
                                        <c:if test="${!empty busy_email}">
                                            ${exists_choose_other}
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
                                <%--                                ---------------------------phone----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="phone" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_phone ||!empty busy_phone }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${phone_label}
                                        <c:if test="${!empty invalid_phone}">
                                            ${incorrect_he_message}
                                        </c:if>
                                        <c:if test="${!empty busy_phone}">
                                            ${exists_choose_other}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                               aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="tel" class="form-control
                                                 <c:if test="${!empty invalid_phone ||!empty busy_phone}">
                                                     is-invalid
                                                 </c:if>
                                                       " name="phone" id="phone"
                                                       placeholder="(025,029,044)1234567"
                                                        <c:if test="${!empty phone}">
                                                            value="${phone}"
                                                        </c:if>
                                                       required pattern="${phone_pattern}"/>
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
                                                        onclick="document.getElementById('command_to_send').value ='registration_send_activation_code';
                                                                   document.getElementById('activation_code').removeAttribute('required');
                                                                   document.getElementById('accept_check').removeAttribute('required')
                                                                   document.getElementById('btn_update_spinner').remove();">
                                                    <span class="spinner-border spinner-border-sm" role="status"
                                                          aria-hidden="true" id="btn_get_code_spinner"
                                                          style="visibility: hidden"></span>
                                                    ${get_activation_code_button}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------check box------------------------------%>
                                <div class="form-group" style="color: white">
                                    <div class="input-group mb-3">
                                        <div class="form-check">
                                            <input class="form-check-input no-validate" type="checkbox" value=""
                                                   id="accept_check"
                                                   required>
                                            <label class="form-check-label" for="accept_check">
                                                ${accept_rules_label}
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------button---------------------------------%>
                                <div class="d-grid gap-1">
                                    <button type="submit" class="btn btn-primary btn-warning "
                                            style="color: white"
                                            onclick=" document.getElementById('btn_get_code_spinner').remove();"
                                    >
                                        ${update_button}
                                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"
                                              id="btn_update_spinner" style="visibility: hidden"></span>
                                    </button>
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
                    } else {
                        if (document.getElementById('btn_update_spinner')) {
                            document.getElementById('btn_update_spinner').style.visibility = 'visible';
                        }
                        if (document.getElementById('btn_get_code_spinner')) {
                            document.getElementById('btn_get_code_spinner').style.visibility = 'visible';
                        }
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>

</body>
</html>

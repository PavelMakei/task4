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
<c:set var="path" value="${pageContext.request.contextPath}"/>


<c:set var="login_pattern" value="^[A-Za-zА-Яа-я0-9_]{4,16}$"/>
<c:set var="password_pattern" value="^[A-Za-zА-Яа-я0-9_!@#,\.]{6,16}$"/>

<fmt:message key="login" var="login_label"/>
<fmt:message key="enter.login" var="login_placeholder"/>
<fmt:message key="login.welcome" var="login_page_welcome"/>
<fmt:message key="incorrect.login" var="incorrect_login_message"/>
<fmt:message key="password" var="password_label"/>
<fmt:message key="enter.password" var="password_placeholder"/>
<fmt:message key="return.main.page" var="return_link_label"/>

<head>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/css/enter.css" rel="stylesheet">
    <title>${login_label}</title>

<%-----------------Prevent to return to previous page---------------%>
    <script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null
        };
        history.pushState(null, null, document.URL);
    </script>
    <%------------------------------------------%>

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
                        <div class="card-header bg-light" style="text-align:center; color: black; font-size: large">
                            ${login_page_welcome}</div>

                        <c:if test="${!empty invalid_login_or_password_message}">
                            <div class="bg-dark bg-opacity-75"
                                 type="text" style="text-align:center;color: red"
                            >${incorrect_login_message}
                            </div>
                        </c:if>

                        <div class="card-body bg-dark bg-opacity-75">
                            <form class="form-horizontal needs-validation" novalidate method="post"
                                  action="${path}/controller">
                                <input type="hidden" name="command" value="login">
<%------------------------------------------------login---------------------------------%>
                                <div class="form-group">
                                    <label for="login" class="cols-sm-2 control-label" style="color: white">
                                        ${login_label}
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3 ">
                                                <input type="text" class="form-control"  name="login" id="login"
                                                       placeholder="${login_placeholder}"
                                                       required pattern="${login_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
<%------------------------------------------------password--------------------------------%>
                                <div class="form-group">
                                    <label for="password" class="cols-sm-2 control-label"
                                           style="color: white">${password_label}</label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                           aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="password" class="form-control" name="password"
                                                       id="password"
                                                       placeholder="${password_placeholder}"
                                                       required pattern="${password_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
<%------------------------------------------------button------------------------------%>
                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary btn-warning"
                                            style="background-color: goldenrod; color: white">
                                        <fmt:message
                                                key="enter.button"/></button>
                                </div>

                                <%--                        //TODO куда переходить?--%>

                                <div class="login-register">
                                    <a href="${path}/index.jsp" style="color: goldenrod">${return_link_label}</a>
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
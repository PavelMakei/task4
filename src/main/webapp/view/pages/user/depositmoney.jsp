<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 27.05.2022
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="path">${pageContext.request.contextPath}</c:set>

<c:set var="card_number_pattern">${validator_pattern.cardNumberPattern}</c:set>
<c:set var="card_exp_date_pattern">${validator_pattern.cardExpDatePattern}</c:set>
<c:set var="card_cvc_pattern">${validator_pattern.integer3StringPattern}</c:set>
<c:set var="card_holder_pattern">${validator_pattern.cardHolderPattern}</c:set>
<c:set var="amount_to_deposit_pattern">${validator_pattern.decimalStringPattern}</c:set>


<fmt:message key="incorrect.he.enter" var="incorrect_he_message"/>
<fmt:message key="incorrect.she.enter" var="incorrect_she_message"/>
<fmt:message key="incorrect.it.enter" var="incorrect_it_message"/>
<fmt:message key="deposit.money.label" var="deposit_money_label"/>
<fmt:message key="card_number.placeholder" var="card_number_placeholder"/>
<fmt:message key="card.number.label" var="card_number_label"/>
<fmt:message key="card.exp.date.label" var="card_exp_date_label"/>
<fmt:message key="card.exp.date.placeholder" var="card_exp_date_placeholder"/>
<fmt:message key="card.cvc.label" var="card_cvc_label"/>
<fmt:message key="card.cvc.placeholder" var="card_cvc_placeholder"/>
<fmt:message key="card.holder.label" var="card_holder_label"/>
<fmt:message key="card.holder.placeholder" var="card_holder_placeholder"/>
<fmt:message key="amount.to.deposit.label" var="amount_to_deposit_label"/>
<fmt:message key="amount.to.deposit.placeholder" var="amount_to_deposit_placeholder"/>
<fmt:message key="deposit.button" var="deposit_button"/>
<fmt:message key="current.money.amount.label" var="current_money_amount_label"/>


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

    <title>${deposit_money_label}</title>
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
                        <div class="card-header bg-light fw-bold" style="text-align:center; color: black;"
                        >
                            <c:if test="${!empty message}">
                                <p style="color: goldenrod">
                                    <fmt:message key="${message}"></fmt:message>
                                </p>
                            </c:if>
                            ${deposit_money_label}
                        </div>
                        <div class="card-body bg-dark bg-opacity-75">

                            <form class="form-horizontal needs-validation" method="post"
                                  action="${path}/controller" novalidate>
                                <input id="command_to_send" type="hidden" name="command" value="deposit_money">
                                <%--                                ---------------------------Current balance-----------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="current_balance" class="cols-sm-2 control-label">
                                        ${current_money_amount_label}
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text"
                                                       id="current_balance"
                                                       class="form-control"
                                                       value="${user.amount}"
                                                       disabled
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------Card number-----------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="card_number" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_card_namber}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${card_number_label}
                                        <c:if test="${!empty invalid_card_number}">
                                            ${incorrect_he_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text"
                                                       class="form-control
                                                 <c:if test="${!empty invalid_card_number}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="card_number"
                                                       id="card_number"
                                                        <c:if test="${!empty card_number}">
                                                            value="${card_number}"
                                                        </c:if>
                                                       minlength="16"
                                                       maxlength="16"
                                                       placeholder="${card_number_placeholder}"
                                                       required pattern="${card_number_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------Card exp.date------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="card_exp_date" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_card_exp_date}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${card_exp_date_label}
                                        <c:if test="${!empty invalid_card_exp_date}">
                                            ${incorrect_she_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_card_exp_date}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="card_exp_date" id="card_exp_date"
                                                       placeholder="${card_exp_date_placeholder}"
                                                       minlength="7"
                                                       maxlength="7"
                                                        <c:if test="${!empty card_exp_date}">
                                                            value="${card_exp_date}"
                                                        </c:if>
                                                       required pattern="${card_exp_date_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------Card cvc----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="card_cvc" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_card_cvc}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${card_cvc_label}
                                        <c:if test="${!empty invalid_card_cvc}">
                                            ${incorrect_he_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_card_cvc}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="card_cvc" id="card_cvc"
                                                       minlength="3"
                                                       maxlength="3"
                                                       placeholder="${card_cvc_placeholder}"
                                                        <c:if test="${!empty card_cvc}">
                                                            value="${card_cvc}"
                                                        </c:if>
                                                       required pattern="${card_cvc_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------Card holder----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="card_holder" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_card_holder}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${card_holder_label}
                                        <c:if test="${!empty invalid_card_holder}">
                                            ${incorrect_he_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_card_holder}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="card_holder" id="card_holder"
                                                       placeholder="${card_holder_placeholder}"
                                                        <c:if test="${!empty card_holder}">
                                                            value="${card_holder}"
                                                        </c:if>
                                                       onchange="let str = this.value; this.value = str.toUpperCase();"
                                                       required pattern="${card_holder_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------Amount to deposit----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="amount_to_deposit" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_amount_to_deposit}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${amount_to_deposit_label}
                                        <c:if test="${!empty invalid_amount_to_deposit}">
                                            ${incorrect_she_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_amount_to_deposit}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="amount_to_deposit" id="amount_to_deposit"
                                                       placeholder="${amount_to_deposit_placeholder}"
                                                        <c:if test="${!empty amount_to_deposit}">
                                                            value="${amount_to_deposit}"
                                                        </c:if>
                                                       required pattern="${amount_to_deposit_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ---------------------------button----------------------------------%>

                                <div class="d-grid gap-1">
                                    <button type="submit" class="btn btn-primary btn-warning "
                                            style="color: white">
                                        ${deposit_button}</button>
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

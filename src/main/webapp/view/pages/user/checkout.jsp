<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 08.06.2022
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>

<fmt:message key="product.name" var="product_name_label"/>
<fmt:message key="price.name" var="price_label"/>
<fmt:message key="quantity.name" var="quantity_label"/>
<fmt:message key="sum.label" var="sum_label"/>
<fmt:message key="continue.shopping" var="continue_shopping_button"/>
<fmt:message key="go.to.checkout" var="go_to_checkout_button"/>
<fmt:message key="show.cart.label" var="show_cart_label"/>
<fmt:message key="total.label" var="total_label"/>
<fmt:message key="current.money.amount.label" var="user_balance"/>
<fmt:message key="not.enough.money" var="not_enough_money"/>
<fmt:message key="placing.order" var="placing_order_label"/>
<fmt:message key="contact.phone" var="contact_phone_label"/>
<fmt:message key="additional.order.details" var="order_details_label"/>
<fmt:message key="order.details.placeholder" var="detail_name_placeholder"/>
<fmt:message key="address.placeholder" var="address_placeholder"/>
<fmt:message key="delivery.address" var="address_label"/>
<fmt:message key="place.your.order" var="place_your_order_button"/>
<fmt:message key="deposit.button" var="deposit_money_button"/>
<fmt:message key="product.photo" var="photo_label"/>

<fmt:message key="incorrect.he.enter" var="incorrect_he_enter_message"/>
<fmt:message key="incorrect.she.enter" var="incorrect_she_enter_message"/>
<fmt:message key="incorrect.it.enter" var="incorrect_it_enter_message"/>

<c:set var="phone_pattern">${validator_pattern.phonePattern}</c:set>

<c:forEach var="thisProductQuantity" items="${sess_cart.productQuantity}">
    <c:set var="thisCurrentProductSumm"
           value="${thisProductQuantity.key.price * thisProductQuantity.value}"/>
    <c:set var="thisTotalSum" value="${thisTotalSum + thisCurrentProductSumm}"/>
</c:forEach>

<head>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/css/enter.css" rel="stylesheet">

    <title>${placing_order_label}</title>

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
        <%@include file="../common/header.jsp" %>
    </div>
    <!-- end of header -->
    <div class="content">
        <table class="table table-borderless">
                <th scope="col" class="col-lg-3" style="padding-left: 0px;padding-top: 0px">
                    <nav id="sidebarMenu" class="col-11 d-md-block sidebar">
                        <div class="position-sticky pt-3">
                            <div class="card" style="border-color: goldenrod">
                                <div class="card-header bg-light fw-bold"
                                     style="text-align:center; color: black;">${placing_order_label}
                                </div>
                                <div class="card-body bg-dark bg-opacity-75">
                                    <form class="form-horizontal needs-validation" novalidate method="get"
                                          action="${path}/controller"
                                          enctype="multipart/form-data">
                                        <input type="hidden" name="command" value="create_order" id="command">

                                        <%------------------------------------------------------------------------------------------------%>

                                        <%----------------------------------------------------- Address description (text)-----------------------------------%>
                                        <div class="form-group" style="color: white">
                                            <label for="address" class="cols-sm-2 control-label"
                                                    <c:if test="${!empty invalid_address}">
                                                        style="color: red"
                                                    </c:if>
                                            >
                                                ${address_label}
                                                <c:if test="${!empty invalid_address}">
                                                    ${incorrect_he_enter_message}
                                                </c:if>
                                            </label>
                                            <div class="cols-sm-10">
                                                <div class="input-group">
                                    <span class="input-group-sm"><i class="fa fa-user fa"
                                                                    aria-hidden="true"></i></span>
                                                    <div class="input-group mb-3">
                                                <textarea class="form-control
                                                 <c:if test="${!empty invalid_address}">
                                                     is-invalid
                                                 </c:if>"
                                                          name="address"
                                                          id="address"
                                                          required
                                                          placeholder="${address_placeholder}"
                                                ><c:if test="${!empty address}">${address}</c:if></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%----------------------------------------------------- Order details (text)-----------------------------------%>
                                        <div class="form-group" style="color: white">
                                            <label for="detail" class="cols-sm-2 control-label"
                                                    <c:if test="${!empty invalid_detail}">
                                                        style="color: red"
                                                    </c:if>
                                            >
                                                ${order_details_label}
                                                <c:if test="${!empty invalid_detail}">
                                                    ${incorrect_it_enter_message}
                                                </c:if>
                                            </label>
                                            <div class="cols-sm-10">
                                                <div class="input-group">
                                    <span class="input-group-sm"><i class="fa fa-user fa"
                                                                    aria-hidden="true"></i></span>
                                                    <div class="input-group mb-3">
                                                <textarea class="form-control
                                                 <c:if test="${!empty invalid_detail}">
                                                     is-invalid
                                                 </c:if>
                                                          " name="detail"
                                                          id="detail"
                                                          placeholder="${detail_name_placeholder}"
                                                ><c:if test="${!empty detail}">${detail}</c:if></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%--                                ---------------------------phone----------------------------------%>
                                        <div class="form-group" style="color: white">
                                            <label for="phone" class="cols-sm-2 control-label"
                                                    <c:if test="${!empty invalid_phone}">
                                                        style="color: red"
                                                    </c:if>
                                            >
                                                ${contact_phone_label}
                                                <c:if test="${!empty invalid_phone}">
                                                    ${incorrect_he_message}
                                                </c:if>
                                            </label>
                                            <div class="cols-sm-10">
                                                <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock fa-lg"
                                                                               aria-hidden="true"></i></span>
                                                    <div class="input-group mb-3">
                                                        <input type="tel" class="form-control
                                                 <c:if test="${!empty invalid_phone}">
                                                     is-invalid
                                                 </c:if>
                                                       " name="phone" id="phone"
                                                               placeholder="(025,029,044)1234567"
                                                                <c:if test="${!empty phone}">
                                                                    value="${phone}"
                                                                </c:if>
                                                                <c:if test="${empty phone}">
                                                                    value="${user.phone}"
                                                                </c:if>
                                                               required
                                                                    pattern="${phone_pattern}"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%------------------------------------------------------Button-----------------------------------------------%>
                                        <div class="d-grid gap-1">
                                            <button type="submit" class="btn btn-primary btn-warning"
                                                    id="butt"
                                                    style=" color: white"
                                                    <c:if test="${user.amount<thisTotalSum}">
                                                        title="${not_enough_money}"
                                                        onclick="
                                                        document.getElementById('command').value = 'go_to_deposit_money'
                                                        "
                                                        >
                                                        ${deposit_money_button}
                                                    </c:if>
                                                    <c:if test="${user.amount>=thisTotalSum}">
                                                        >
                                                        ${place_your_order_button}
                                                    </c:if>
                                             </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </nav>
                </th>
                <%-----------------------------------------------------main part----------------------------------------------------%>

                <th scope="col" class="col-lg-9" style="padding-top:16px">
                    <div class="form-group bg-white" style="color: white">
                        <%--                -------------Table of products-----------%>
                        <%@include file="../parts/productsincart.jsp" %>
                </th>

            </tr>
        </table>
    </div>
    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>

<%-----------------------Modal window------------------------------%>

<%@include file="../parts/modalwindow.jsp" %>

<script>
    (function () {
        'use strict'
        let forms = document.querySelectorAll('.needs-validation')
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



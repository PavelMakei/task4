<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 30.05.2022
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>

<c:set var="path">${pageContext.request.contextPath}</c:set>

<fmt:message key="product.name" var="product_name_label"/>
<fmt:message key="price.name" var="price_label"/>
<fmt:message key="quantity.name" var="quantity_label"/>
<fmt:message key="sum.label" var="sum_label"/>
<fmt:message key="proceed.to.checkout" var="proceed_to_checkout"/>
<fmt:message key="continue.shopping" var="continue_shopping_button"/>
<fmt:message key="go.to.checkout" var="go_to_checkout_button"/>
<fmt:message key="show.cart.label" var="show_cart_label"/>
<fmt:message key="total.label" var="total_label"/>
<fmt:message key="clear.cart" var="clear_cart_button"/>
<fmt:message key="current.money.amount.label" var="user_balance"/>
<fmt:message key="log.in.before" var="log_in_before"/>
<fmt:message key="not.enough.money" var="not_enough_money"/>
<fmt:message key="deposit.button" var="deposit_money_button"/>
<fmt:message key="enter.button" var="enter_button"/>
<fmt:message key="product.photo" var="photo_label"/>


<html>
<head>
    <title>${show_cart_label}</title>
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/enter.css">
    <script src="${path}/bootstrap/js/bootstrap.bundle.min.js"></script>

<%--    ---------------Prevent to return to previous page---------------%>
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
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large">
                            ${show_cart_label}
                        </div>
                        <div class="card-body bg-dark bg-opacity-75">
                            <%------------------------------------------------------Products-----------------------------------------------%>
                            <div class="form-group bg-white" style="color: white">
                                <%@include file="../parts/productsincart.jsp" %>
                                <tr class="border-2 border-dark">
                                    <div class="justify-content-center" style="color: white; padding-bottom: 01px;">
                                        <div class="input-group mb-0">
                                            <button class="btn btn-primary col-4"
                                                    style="margin-left: 1px;"
                                                    id="buy_button"
                                                    <c:if test="${!empty user && user.amount<totalSum}">
                                                        title="${not_enough_money}"
                                                        onclick="
                                                        if(!window.opener || window.opener.closed) {
                                                        var url = (window.location !== window.parent.location)
                                                        ? document.referrer
                                                        : document.location.href;
                                                        var domain = url.replace('http://','').replace('https://','').split(/[/?#]/)[0];
                                                        var protocol = url.split('//')[0];
                                                        window.open(protocol+'//'+domain+'/controller?command=go_to_deposit_money','_blank','location=yes,scrollbars=yes,status=yes,menubar=yes,resizable=yes,fullscreen=yes');}
                                                        else {window.opener.goToDeposit()}
                                                        window.close();"
                                                        >${deposit_money_button}
                                                    </c:if>
                                                    <c:if test="${empty user}">
                                                        title="${log_in_before}"
                                                        onclick="
                                                        if(!window.opener || window.opener.closed) {
                                                        var url = (window.location !== window.parent.location)
                                                        ? document.referrer
                                                        : document.location.href;
                                                        var domain = url.replace('http://','').replace('https://','').split(/[/?#]/)[0];
                                                        var protocol = url.split('//')[0];
                                                        window.open(protocol+'//'+domain+'/controller?command=go_to_login','_blank','location=yes,scrollbars=yes,status=yes,menubar=yes,resizable=yes,fullscreen=yes');}
                                                        else {window.opener.goToLogin()}
                                                        window.close();"
                                                        >${enter_button}
                                                    </c:if>
                                                    <c:if test="${!empty user && user.amount>=totalSum}">
                                                        onclick="
                                                        if(!window.opener || window.opener.closed) {
                                                        var url = (window.location !== window.parent.location)
                                                        ? document.referrer
                                                        : document.location.href;
                                                        var domain = url.replace('http://','').replace('https://','').split(/[/?#]/)[0];
                                                        var protocol = url.split('//')[0];
                                                        window.open(protocol+'//'+domain+'/controller?command=go_to_checkout','_blank','location=yes,scrollbars=yes,status=yes,menubar=yes,resizable=yes,fullscreen=yes');}
                                                        else {window.opener.goToCheckout()}
                                                        window.close();
                                                        "
                                                        >${proceed_to_checkout}
                                                    </c:if>
                                                    <c:if test="${!empty user && empty totalSum}">
                                                        disabled
                                                        >${proceed_to_checkout}
                                                    </c:if>
                                                        </button>
                                            <button class="btn btn-warning col-4" style="color: white"
                                                    onclick="
                                                    if(!window.opener || window.opener.closed) {
                                                        var url = (window.location !== window.parent.location)
                                                             ? document.referrer
                                                             : document.location.href;
                                                        var domain = url.replace('http://','').replace('https://','').split(/[/?#]/)[0];
                                                        var protocol = url.split('//')[0];
                                                    window.open(protocol+'//'+domain, '_blank', 'location=yes,scrollbars=yes,status=yes,menubar=yes,resizable=yes,fullscreen=yes')}
                                                    window.close()"
                                            >${continue_shopping_button}</button>
                                            <button class="btn btn-danger col-4"
                                                    onclick="
                                                        if(!window.opener || window.opener.closed) {
                                                        var url = (window.location !== window.parent.location)
                                                        ? document.referrer
                                                        : document.location.href;
                                                        var domain = url.replace('http://','').replace('https://','').split(/[/?#]/)[0];
                                                        var protocol = url.split('//')[0];
                                                        window.open(protocol+'//'+domain+'/controller?command=clear_cart','_blank','location=yes,scrollbars=yes,status=yes,menubar=yes,resizable=yes,fullscreen=yes');}
                                                        else {window.opener.clearCart()}
                                                        window.close();
                                                        ">${clear_cart_button}</button>
                                        </div>
                                    </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>
<%@include file="../parts/modalwindow.jsp" %>

</body>
</html>

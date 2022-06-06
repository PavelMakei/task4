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
<%--<fmt:message key="total.sum.label" var="total_sum_label"/>--%>
<fmt:message key="buy.button" var="buy_button"/>
<fmt:message key="continue.shopping" var="continue_shopping_button"/>
<fmt:message key="go.to.checkout" var="go_to_checkout_button"/>
<fmt:message key="show.cart.label" var="show_cart_label"/>
<fmt:message key="total.label" var="total_label"/>
<fmt:message key="clear.cart" var="clear_cart_button"/>


<html>
<head>
    <title>${show_cart_label}</title>
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/enter.css">
    <script src="${path}/bootstrap/js/bootstrap.bundle.min.js"></script>

    <%-----------------Prevent to return to previous page---------------%>
    <%--    <script>--%>
    <%--        function preventBack() {--%>
    <%--            window.history.forward();--%>
    <%--        }--%>

    <%--        setTimeout("preventBack()", 0);--%>
    <%--        window.onunload = function () {--%>
    <%--            null--%>
    <%--        };--%>
    <%--        history.pushState(null, null, document.URL);--%>
    <%--    </script>--%>
    <%--    &lt;%&ndash;--------------------------------------&ndash;%&gt;--%>
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
                                <table class="table table-responsive table-bordered" style="margin-bottom: 0px">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">photo</th>
                                        <th scope="col">${product_name_label}</th>
                                        <th scope="col">${price_label}</th>
                                        <th scope="col">${quantity_label}</th>
                                        <th scope="col">${sum_label}</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:set var="count" value="0"/>
                                    <c:forEach var="productQuantity" items="${sess_cart.productQuantity}">
                                        <c:set var="count" value="${count + 1}"/>
                                        <c:set var="currentProductSumm"
                                               value="${productQuantity.key.price * productQuantity.value}"/>
                                        <c:set var="totalSum" value="${totalSum + currentProductSumm}"/>
                                        <c:set var="total_quantity" value="${total_quantity + productQuantity.value}"/>
                                        <tr
                                        >
                                            <th scope="row">${count}</th>

                                            <td scope="image" class="col-3"
                                                style="background-color: white; height: 50px">
                                                <div class="card border-0 d-block">
                                                    <c:if test="${empty productQuantity.key.photoString}">
                                                        <img src="${path}/images/nophoto.jpg"
                                                             class="img-fluid mx-auto d-block"
                                                             alt="img"
                                                        >
                                                    </c:if>
                                                    <c:if test="${!empty productQuantity.key.photoString}">
                                                        <img src="data:image/jpeg;base64,${productQuantity.key.photoString}"
                                                             alt="img"
                                                             class="img-fluid mx-auto"
                                                             style="max-height: 50px">
                                                    </c:if>
                                                    <a href="javascript:void(0);"
                                                       title="Посмотреть продукт!!!"
                                                       class="stretched-link"
                                                       onClick=window.open("${path}/controller?command=show_product&id=${productQuantity.key.id}","Product","width=1400,height=650,left=300,toolbar=no,status=no,resizable=no,location=no,directories=no");></a>
                                                </div>
                                            </td>

                                            <td>${productQuantity.key.productName}</td>
                                            <td>${productQuantity.key.price}</td>
                                            <td>${productQuantity.value}</td>
                                            <td>${currentProductSumm}</td>
                                        </tr>
                                    </c:forEach>
                                    <tr class="border-2 border-dark">
                                    <tr>
                                        <th scope="col">${total_label}</th>
                                        <th class="border-0" scope="col"></th>
                                        <th class="border-0" scope="col"></th>
                                        <th class="border-0" scope="col"></th>
                                        <th scope="col">${total_quantity}</th>
                                        <th scope="col">${totalSum}</th>
                                    </tr>
                                    <tr class="border-2 border-dark">
                                    </tbody>
                                </table>
                                <div class="justify-content-center" style="color: white; padding-bottom: 01px;">

                                    <div class="input-group mb-0">
                                        <button class="btn btn-primary col-4"
                                                style="margin-left: 1px;">${buy_button}</button>
                                        <button class="btn btn-warning col-4" style="color: white"
                                                onclick="
                                                    if(!window.opener || window.opener.closed) {
                                                        var url = (window.location != window.parent.location)
                                                             ? document.referrer
                                                               : document.location.href;
                                                        var domain = url.replace('http://','').replace('https://','').split(/[/?#]/)[0];
                                                        var protocol = url.split('//')[0];
                                                    window.open(protocol+'//'+domain, '_blank', 'location=yes,scrollbars=yes,status=yes,menubar=yes,resizable=yes,fullscreen=yes')}
                                                    window.close()"
                                        >${continue_shopping_button}</button>
                                        <button class="btn btn-danger col-4">${clear_cart_button}</button>

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
<%@include file="parts/modalwindow.jsp" %>

</body>
</html>

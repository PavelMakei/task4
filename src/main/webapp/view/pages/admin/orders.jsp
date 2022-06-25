<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 11.06.2022
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>

<c:set var="path">${pageContext.request.contextPath}</c:set>

<fmt:message key="more.hint" var="more_hint"/>
<fmt:message key="order.management" var="orders_manage_label"/>
<fmt:message key="user.id" var="user_id_label"/>
<fmt:message key="mobile.phone" var="phone_label"/>
<fmt:message key="delivery.address" var="address_label"/>
<fmt:message key="additional.order.details" var="details_label"/>
<fmt:message key="date.open" var="date_open_label"/>
<fmt:message key="date.close" var="date_close_label"/>
<fmt:message key="cancel.order" var="cancel_order_button"/>
<fmt:message key="deliver.order" var="deliver_order_button"/>
<fmt:message key="order.status" var="order_status_label"/>
<fmt:message key="product.id" var="product_id_label"/>
<fmt:message key="order.id" var="order_id_label"/>
<fmt:message key="quantity.name" var="quantity_label"/>
<fmt:message key="login" var="login_label"/>
<fmt:message key="sum.label" var="sum_label"/>


<html>
<head>
    <title>${orders_manage_label}</title>
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/enter.css">

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
    <div class="header">
        <%@include file="../common/header.jsp" %>
    </div>
    <!-- end of header -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large">
                            ${orders_manage_label}
                        </div>
                        <%------------------------------------------------------Orders-----------------------------------------------%>
                        <c:forEach var="order" items="${order_map}">
                            <div class="card-body bg-dark bg-opacity-75" style="padding-bottom: 0">
                                <div class="form-group bg-white" style="color: white; border-style: solid; border-width: 1px; border-radius: .25rem">

                                    <table class="table table-responsive table-bordered" style="margin-bottom: 0px">
                                        <thead>
                                        <tr>
                                            <th scope="col">${order_id_label}</th>
                                            <th scope="col">${user_id_label}</th>
                                            <th scope="col">${login_label}</th>
                                            <th scope="col">${phone_label}</th>
                                            <th scope="col">${date_open_label}</th>
                                            <th scope="col">${order_status_label}</th>
                                            <th scope="col">${date_close_label}</th>
                                            <th scope="col">${sum_label}</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>${order.key.id}</td>
                                            <td>${order.key.userId}</td>
                                            <td>${order.value[0]}</td>
                                            <td>${order.key.phone}</td>
                                            <td>${order.key.openDate}</td>
                                            <td><fmt:message key="${order.key.status}"/></td>
                                            <td>${order.key.closeDate}</td>
                                            <td>${order.value[1]}</td>

                                        </tr>
                                        <tr class="border-2 border-dark">
                                        </tbody>
                                    </table>

                                    <p style="padding-left: 8px; color: black ">
                                        <strong>${address_label}:</strong> ${order.key.address}</p>
                                    <p style="padding-left: 8px; color: black">
                                        <strong>${details_label}:</strong> ${order.key.detail}</p>

                                    <table class="table table-responsive table-bordered" style="margin-bottom: 0px">

                                        <thead>
                                        <tr class="border-2 border-dark">
                                        <tr>
                                            <th scope="col" class="col-6">${product_id_label}</th>
                                            <th scope="col" class="col-6">${quantity_label}</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="productIdQuantityMap" value="${order.key.prodIdQuantity}"/>
                                        <c:forEach var="elem" items="${productIdQuantityMap}">
                                            <tr>
                                                <td>
                                                    <a
                                                        class="pe-auto"
                                                        href="javascript:void(0);"
                                                        title="${more_hint}"
                                                        onClick=window.open("${path}/controller?command=show_product&id=${elem.key}","Product","width=1400,height=650,left=300,toolbar=no,status=no,resizable=no,location=no,directories=no");
                                                    >${elem.key}</a></td>
                                                <td>${elem.value}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <c:if test="${order.key.status eq 'PAID'}">
                                        <div class="justify-content-center" style="color: white;background-color:#000000a3;">
                                            <div class="input-group col-12">
                                                <c:if test="${!empty access_level && access_level eq 'ADMIN'}">
                                                    <button class="btn btn-primary col-6"
                                                            onclick="window.location.href='${path}/controller?command=deliver_order&id=${order.key.id}'"
                                                    >${deliver_order_button}
                                                    </button>
                                                </c:if>
                                                <button class="btn btn-danger col-6"
                                                        style="margin-left: 0"
                                                        onclick="window.location.href='${path}/controller?command=cancel_order&id=${order.key.id}'"
                                                >${cancel_order_button}
                                                </button>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
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
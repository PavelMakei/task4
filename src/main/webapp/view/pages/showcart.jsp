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

<fmt:message key="product.name" var="product_name_lable"/>
<fmt:message key="price.name" var="price_lable"/>
<fmt:message key="quantity.name" var="quantity_lable"/>
<fmt:message key="sum.label" var="sum_lable"/>
<fmt:message key="total.sum.label" var="total_sum_lable"/>
<fmt:message key="buy.button" var="buy_button"/>
<fmt:message key="continue.shopping" var="continue_shopping_button"/>
<fmt:message key="go.to.checkout" var="go_to_checkout_button"/>


<html>
<head>
    <title>${show_cart_lable}</title>
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/enter.css">
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
                            ${show_cart_lable}
                            <c:if test="${!empty message}">
                                <p style="color: goldenrod">
                                    <fmt:message key="${message}"></fmt:message>
                                </p>
                            </c:if>
                            <%--                            ${head_label}--%>
                        </div>
                        <div class="card-body bg-dark bg-opacity-75">
                            <%------------------------------------------------------Products-----------------------------------------------%>
                            <div class="form-group bg-white" style="color: white">
                                <table class="table table-responsive table-bordered">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">photo</th>
                                        <th scope="col">${product_name_lable}</th>
                                        <th scope="col">${price_lable}</th>
                                        <th scope="col">${quantity_lable}</th>
                                        <th scope="col">${sum_lable}</th>
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
                                            <%--                                                <c:if test="${user_elem.accessLevel eq 'ADMIN'}">--%>
                                            <%--                                                    class="table-warning"--%>
                                            <%--                                                </c:if>--%>
                                            <%--                                                <c:if test="${user_elem.accessLevel eq 'USER'}">--%>
                                            <%--                                                    class="table-success"--%>
                                            <%--                                                </c:if>--%>
                                            <%--                                                <c:if test="${user_elem.accessLevel eq 'BLOCKED'}">--%>
                                            <%--                                                    class="table-danger"--%>
                                            <%--                                                </c:if>--%>
                                        >
                                            <th scope="row">${count}</th>

                                            <td scope="image" class="col-3" style="background-color: white; height: 50px">
                                                <div class="card border-0 d-block">
                                                    <c:if test="${empty productQuantity.key.photoString}">
                                                        <img src="${path}/images/nophoto.jpg" class="img-fluid mx-auto d-block"
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
                                                       onClick=window.open("${path}controller?command=show_product&id=${productQuantity.key.id}","Product","width=1400,height=650,left=300,toolbar=no,status=no,resizable=no,location=no,directories=no");></a>
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
                                        <th scope="col">total sum!!!</th>
                                        <th class="border-0" scope="col"></th>
                                        <th class="border-0" scope="col"></th>
                                        <th class="border-0" scope="col"></th>
                                        <th scope="col">${total_quantity}</th>
                                        <th scope="col">${totalSum}</th>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>

</body>
</html>

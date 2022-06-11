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

<fmt:message key="product.name" var="product_name_label"/>
<fmt:message key="price.name" var="price_label"/>
<fmt:message key="quantity.name" var="quantity_label"/>
<fmt:message key="sum.label" var="sum_label"/>
<fmt:message key="buy.button" var="buy_button"/>
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
    <div class="header">
        <%@include file="../header.jsp" %>
    </div>
    <!-- end of header -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large">
                            Заказы!!!!!!!!!!!!!
                        </div>
                        <%------------------------------------------------------Orders-----------------------------------------------%>
                        <c:forEach var="tests" begin="1" end="5" step="1">
                        <div class="card-body bg-dark bg-opacity-75" style="padding-bottom: 0">
                            <div class="form-group bg-white" style="color: white">

                                <table class="table table-responsive table-bordered" style="margin-bottom: 0px">
                                    <thead>
                                    <tr>
                                        <th scope="col">id</th>
                                        <th scope="col">uid</th>
                                        <th scope="col">phone</th>
                                        <th scope="col">open date</th>
                                        <th scope="col">status</th>
                                        <th scope="col">close date</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td scope="row">1!!</td>
                                        <td>УИД</td>
                                        <td>(025)1234567</td>
                                        <td>01.01.2022</td>
                                        <td>PAYED</td>
                                        <td> -</td>
                                    </tr>
                                    <tr class="border-2 border-dark">
                                    </tbody>
                                </table>

                                <p style="color: black"><strong>Address:</strong> здесь какой-то длинный адрес
                                    воооооооооооооооооооот
                                    ттттттттаааааааааккккоооооооййй или даже воооооооооооооооооооот
                                   </p>
                                <p style="color: black"><strong>Details:</strong> здесь какие-то детали, возможно тоже
                                    длинные</p>

                                <table class="table table-responsive table-bordered" style="margin-bottom: 0px">

                                    <thead>
                                    <tr class="border-2 border-dark">
                                    <tr>
                                        <th scope="col" class="col-6">prodId</th>
                                        <th scope="col" class="col-6">quantity</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>2</td>
                                    </tr>
                                    <tr>
                                        <td>12</td>
                                        <td>1</td>
                                    </tr>
                                    <tr class="border-2 border-dark">
                                    </tbody>
                                </table>

                                <div class="justify-content-center" style="color: white; padding-bottom: 01px;">
                                    <div class="input-group col-12">
                                        <button class="btn btn-primary col-6"
                                        >Выполнено!!!
                                        </button>
                                        <button class="btn btn-danger col-6"
                                        >Отменить!!!
                                        </button>

                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                        <%--                        ----------------------ВТОРАЯ------------------------%>
<%--                        <div class="card-body bg-dark bg-opacity-75">--%>
<%--                            <div class="form-group bg-white" style="color: white">--%>

<%--                                <table class="table table-responsive table-bordered" style="margin-bottom: 0px">--%>
<%--                                    <thead>--%>
<%--                                    <tr>--%>
<%--                                        <th scope="col">id</th>--%>
<%--                                        <th scope="col">uid</th>--%>
<%--                                        <th scope="col">phone</th>--%>
<%--                                        <th scope="col">open date</th>--%>
<%--                                        <th scope="col">status</th>--%>
<%--                                        <th scope="col">close date</th>--%>
<%--                                    </tr>--%>
<%--                                    </thead>--%>
<%--                                    <tbody>--%>
<%--                                    <tr>--%>
<%--                                        <td scope="row">1!!</td>--%>
<%--                                        <td>УИД</td>--%>
<%--                                        <td>(025)1234567</td>--%>
<%--                                        <td>01.01.2022</td>--%>
<%--                                        <td>PAYED</td>--%>
<%--                                        <td> -</td>--%>
<%--                                    </tr>--%>
<%--                                    <tr class="border-2 border-dark">--%>
<%--                                    </tbody>--%>
<%--                                </table>--%>

<%--                                <p style="color: black"><strong>Address:</strong> здесь какой-то длинный адрес--%>
<%--                                    воооооооооооооооооооот--%>
<%--                                    ттттттттаааааааааккккоооооооййй или даже воооооооооооооооооооот--%>
<%--                                    ттттттттаааааааааккккоооооооййй или дажевоооооооооооооооооооот--%>
<%--                                    ттттттттаааааааааккккоооооооййй или дажевоооооооооооооооооооот--%>
<%--                                    ттттттттаааааааааккккоооооооййй или даже</p>--%>
<%--                                <p style="color: black"><strong>Details:</strong> здесь какие-то детали, возможно тоже--%>
<%--                                    длинные</p>--%>

<%--                                <table class="table table-responsive table-bordered" style="margin-bottom: 0px">--%>

<%--                                    <thead>--%>
<%--                                    <tr class="border-2 border-dark">--%>
<%--                                    <tr>--%>
<%--                                        <th scope="col" class="col-6">prodId</th>--%>
<%--                                        <th scope="col" class="col-6">quantity</th>--%>
<%--                                    </tr>--%>
<%--                                    </thead>--%>
<%--                                    <tbody>--%>
<%--                                    <tr>--%>
<%--                                        <td>1</td>--%>
<%--                                        <td>2</td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>12</td>--%>
<%--                                        <td>1</td>--%>
<%--                                    </tr>--%>
<%--                                    <tr class="border-2 border-dark">--%>
<%--                                    </tbody>--%>
<%--                                </table>--%>

<%--                                <div class="justify-content-center" style="color: white; padding-bottom: 01px;">--%>
<%--                                    <div class="input-group col-12">--%>
<%--                                        <button class="btn btn-primary col-6"--%>
<%--                                        >Выполнено!!!--%>
<%--                                        </button>--%>
<%--                                        <button class="btn btn-danger col-6"--%>
<%--                                        >Отменить!!!--%>
<%--                                        </button>--%>

<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
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
<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 05.05.2022
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:message key="brands_id" var="brands_id_label"/>
<fmt:message key="incorrect.he.enter" var="incorrect_he_enter_message"/>
<fmt:message key="incorrect.she.enter" var="incorrect_she_enter_message"/>
<fmt:message key="incorrect.it.enter" var="incorrect_it_enter_message"/>
<fmt:message key="select.your.brand" var="select_your_brand_option"/>
<fmt:message key="types_id" var="types_id_label"/>
<fmt:message key="select.your.productType" var="select_your_type_option"/>
<fmt:message key="product.name" var="product_name_label"/>
<fmt:message key="description.name" var="description_name_label"/>
<fmt:message key="price.name" var="price_name_label"/>
<fmt:message key="price.placeholder" var="price_placeholder"/>
<fmt:message key="colour.name" var="colour_name_label"/>
<fmt:message key="power.name" var="power_name_label"/>
<fmt:message key="power.placeholder" var="power_placeholder"/>
<fmt:message key="size.name" var="size_name_label"/>
<fmt:message key="quantity.name" var="quantity_name_label"/>
<fmt:message key="find.products.button" var="find_products_button"/>
<fmt:message key="selection.by.parameters" var="selection_by_parameters_label"/>


<c:set var="price_pattern">^((\d{1,5}\.\d{0,2})|(\d{1,5}))$</c:set>
<c:set var="power_pattern">^[\d]{1,3}$</c:set>


<head>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/css/enter.css" rel="stylesheet">
    <title>${main_page_label}</title>

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

<div class="header">
    <%@include file="header.jsp" %>
</div> <!-- end of header -->


<div class="row">

    <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block sidebar">
        <div class="position-sticky pt-3">
            <div class="card" style="border-color: goldenrod">
                <div class="card-header bg-light fw-bold"
                     style="text-align:center; color: black;">${selection_by_parameters_label}
                </div>
                <%--        TODO translate--%>

                <div class="card-body bg-dark bg-opacity-75">
                    <form class="form-horizontal needs-validation" novalidate method="post"
                          action="${path}/controller"
                          enctype="multipart/form-data">
                        <input type="hidden" name="command" value="find_product">

                        <%----------------------------------------------------------------------------------------------            --%>
                        <%------------------------------------------------------Brands-----------------------------------------------%>
                        <div class="form-group" style="color: white">
                            <label for="brand" class="cols-sm-2 control-label"
                                    <c:if test="${!empty invalid_brand_id}">
                                        style="color: red"
                                    </c:if>
                            >
                                ${brands_id_label}
                                <c:if test="${!empty invalid_brand_id}">
                                    ${incorrect_he_enter_message}
                                </c:if>
                            </label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>
                                    <%--                                    выпадающий список--%>
                                    <div class="input-group mb-3">
                                        <select class="form-select" name="brand_id" id="brand" >
                                            <option value=""
                                                    selected>${select_your_brand_option}</option>
                                            <c:forEach var="brandEnter" items="${brands_map}">
                                                <option value=${brandEnter.value}>${brandEnter.key}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <%--                               конец     выпадающий список--%>
                                </div>
                            </div>
                        </div>
                        <%------------------------------------------------------Types------------------------------------------------%>
                        <div class="form-group" style="color: white">
                            <label for="type_id" class="cols-sm-2 control-label"
                                    <c:if test="${!empty invalid_type_id}">
                                        style="color: red"
                                    </c:if>
                            >
                                ${types_id_label}
                                <c:if test="${!empty invalid_type_id}">
                                    ${incorrect_he_enter_message}
                                </c:if>
                            </label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>

                                    <%--                                    выпадающий список--%>
                                    <div class="input-group mb-3">
                                        <select class="form-select" id="type_id" name="type_id">
                                            <option value=""
                                                    selected>${select_your_type_option}</option>
                                            <c:forEach var="typeEnter" items="${types_map}">
                                                <option value=${typeEnter.value}>${typeEnter.key}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <%--                               конец     выпадающий список--%>
                                </div>
                            </div>
                        </div>

                        <%----------------------------------------------------- price dec(10,2)--------------------------------------%>
                        <div class="form-group" style="color: white">
                            <label for="price_min" class="cols-sm-2 control-label"
                                    <c:if test="${!empty invalid_price}">
                                        style="color: red"
                                    </c:if>
                            >
                                ${price_name_label}
                                <c:if test="${!empty invalid_price}">
                                    ${incorrect_she_enter_message}
                                </c:if>
                            </label>

                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                    <div class="input-group mb-3">
                                        <input type="text" class="form-control" name="price_min"
                                               id="price_min"
                                               placeholder=${price_placeholder}
                                               <c:if test="${!empty price_min}">
                                                       value="${price_min}"
                                        </c:if>
                                               pattern="${price_pattern}"/>
                                        <a>   </a>
                                        <input type="text" class="form-control" name="price_max"
                                               id="price_max"
                                               placeholder=${price_placeholder}
                                               <c:if test="${!empty price_max}">
                                                       value="${price_max}"
                                        </c:if>
                                               pattern="${price_pattern}"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%----------------------------------------------------- power int--------------------------------------------%>
                        <div class="form-group" style="color: white">
                            <label for="power_min" class="cols-sm-2 control-label"
                                    <c:if test="${!empty invalid_power}">
                                        style="color: red"
                                    </c:if>
                            >
                                ${power_name_label}
                                <c:if test="${!empty invalid_power}">
                                    ${incorrect_it_enter_message}
                                </c:if>
                            </label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                    <div class="input-group mb-3">
                                        <input type="text" class="form-control" name="power_min"
                                               id="power_min"
                                               placeholder="${power_placeholder}"
                                                <c:if test="${!empty power_min}">
                                                    value="${power_min}"
                                                </c:if>
                                               pattern="${power_pattern}"/>
                                        <input type="text" class="form-control" name="power_max"
                                               id="power_max"
                                               placeholder="${power_placeholder}"
                                                <c:if test="${!empty power_max}">
                                                    value="${power_max}"
                                                </c:if>
                                               pattern="${power_pattern}"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%------------------------------------------------------Button-----------------------------------------------%>
                        <div class="d-grid gap-1">
                            <button type="submit" class="btn btn-primary btn-warning"
                                    id="butt"
                                    style="background-color: goldenrod; color: white"
                                    onclick="CheckPicture()">
                                ${find_products_button}</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </nav>


    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <div class="chartjs-size-monitor">
            <div class="chartjs-size-monitor-expand">
                <div class="">

                </div>
            </div>
            <div class="chartjs-size-monitor-shrink">
                <div class="">

                </div>
            </div>
        </div>

        <h2>Заголовок какой-то</h2>
        <div class="table-responsive">
            <table class="table table-striped table-sm">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">1111</th>
                    <th scope="col">22222</th>
                    <th scope="col">33333</th>
                    <th scope="col">44444</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1,001</td>
                    <td>222222</td>
                    <td>33333</td>
                    <td>44444</td>
                    <td>55555</td>
                </tr>

                </tbody>
            </table>
        </div>
    </main>
</div>


</body>
</html>


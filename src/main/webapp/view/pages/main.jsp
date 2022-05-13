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


<fmt:message key="brands_id" var="brands_id_label"/>
<fmt:message key="incorrect.he.enter" var="incorrect_he_enter_message"/>
<fmt:message key="incorrect.she.enter" var="incorrect_she_enter_message"/>
<fmt:message key="incorrect.it.enter" var="incorrect_it_enter_message"/>
<fmt:message key="any.brand" var="any_brand_option"/>
<fmt:message key="types_id" var="types_id_label"/>
<fmt:message key="any.type" var="any_type_option"/>
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
<fmt:message key="previous.page.button" var="previous_page_button"/>
<fmt:message key="next.page.button" var="next_page_button"/>
<fmt:message key="page.number" var="page_number"/>
<fmt:message key="page.of" var="page_of"/>
<fmt:message key="buy.button" var="buy_button"/>
<fmt:message key="in.stock" var="in_stock"/>


<c:set var="price_pattern">^((\d{1,5}\.\d{0,2})|(\d{1,5}))$</c:set>
<c:set var="power_pattern">^[\d]{1,3}$</c:set>
<c:set var="quantity_pattern">^([1-9]|(10))$</c:set>


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
<div class="wrapper">
    <div class="header">
        <%@include file="header.jsp" %>
    </div> <!-- end of header -->


    <table class="table table-borderless">
        <tr>
            <th scope="col" class="col-lg-3" style="padding-left: 0px;padding-top: 0px">
                <nav id="sidebarMenu" class="col-11 d-md-block sidebar">
                    <div class="position-sticky pt-3">
                        <div class="card" style="border-color: goldenrod">
                            <div class="card-header bg-light fw-bold"
                                 style="text-align:center; color: black;">${selection_by_parameters_label}
                            </div>
                            <div class="card-body bg-dark bg-opacity-75">
                                <form class="form-horizontal needs-validation" novalidate method="post"
                                      action="${path}/controller"
                                      enctype="multipart/form-data">
                                    <input type="hidden" name="command" value="go_to_main">

                                    <%------------------------------------------------------------------------------------------------%>
                                    <%------------------------------------------------------Brands-----------------------------------------------%>
                                    <div class="form-group" style="color: white">
                                        <label for="search_brand_id" class="cols-sm-2 control-label"
                                                <c:if test="${!empty invalid_search_brand_id}">
                                                    style="color: red"
                                                </c:if>
                                        >
                                            ${brands_id_label}
                                            <c:if test="${!empty invalid_search_brand_id}">
                                                ${incorrect_he_enter_message}
                                            </c:if>
                                        </label>
                                        <div class="cols-sm-10">
                                            <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>
                                                <%--                                    выпадающий список--%>
                                                <div class="input-group mb-3">
                                                    <select class="form-select" name="search_brand_id"
                                                            id="search_brand_id">

                                                                <c:if test="${!empty search_brand_id}">
                                                                    <option
                                                                            value="">
                                                                            ${any_brand_option}
                                                                    </option>
                                                                    <option
                                                                    value="${search_brand_id}"
                                                                    selected>${brands_map[search_brand_id]}
                                                                    </option>

                                                                </c:if>

                                                                <c:if test="${empty search_brand_id}">
                                                                    <option
                                                                    value=""
                                                                    selected>${any_brand_option}
                                                                    </option>
                                                                </c:if>

                                                        <c:forEach var="brandEnter" items="${brands_map}">
                                                            <c:if test="${brandEnter.key ne search_brand_id}">
                                                            <option value=${brandEnter.key}>${brandEnter.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <%--                               конец     выпадающий список--%>
                                            </div>
                                        </div>
                                    </div>
                                    <%------------------------------------------------------Types------------------------------------------------%>
                                    <div class="form-group" style="color: white">
                                        <label for="search_type_id" class="cols-sm-2 control-label"
                                                <c:if test="${!empty invalid_search_type_id}">
                                                    style="color: red"
                                                </c:if>
                                        >
                                            ${types_id_label}
                                            <c:if test="${!empty invalid_search_type_id}">
                                                ${incorrect_he_enter_message}
                                            </c:if>
                                        </label>
                                        <div class="cols-sm-10">
                                            <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>

                                                <%--                                    выпадающий список--%>
                                                <div class="input-group mb-3">
                                                    <select class="form-select" id="search_type_id"
                                                            name="search_type_id">
                                                        <c:if test="${!empty search_type_id}">
                                                            <option
                                                                    value="">
                                                                    ${any_type_option}
                                                            </option>
                                                            <option
                                                                    value="${search_type_id}"
                                                                    selected>${types_map[search_type_id]}
                                                            </option>
                                                        </c:if>
                                                        <c:if test="${empty search_type_id}">
                                                            <option
                                                                    value=""
                                                                    selected>${any_type_option}
                                                            </option>
                                                        </c:if>
                                                        <c:forEach var="typeEnter" items="${types_map}">
                                                            <c:if test="${typeEnter.key ne search_type_id}">
                                                            <option value=${typeEnter.key}>${typeEnter.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <%--                               конец     выпадающий список--%>
                                            </div>
                                        </div>
                                    </div>

                                    <%----------------------------------------------------- price dec(10,2)--------------------------------------%>
                                    <div class="form-group" style="color: white">
                                        <label for="search_min_price" class="cols-sm-2 control-label"
                                                <c:if test="${!empty invalid_search_min_price || !empty invalid_search_max_price}">
                                                    style="color: red"
                                                </c:if>
                                        >
                                            ${price_name_label}
                                            <c:if test="${!empty invalid_search_min_price || !empty invalid_search_max_price}">
                                                ${incorrect_she_enter_message}
                                            </c:if>
                                        </label>

                                        <div class="cols-sm-10">
                                            <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                                <div class="input-group mb-3">
                                                    <input type="number" class="form-control" name="search_min_price"
                                                           id="search_min_price"
                                                           min="0"
                                                           max="10000"
                                                           placeholder="0-10000"
                                                            <c:if test="${!empty search_min_price}">
                                                                value="${search_min_price}"
                                                            </c:if>
                                                           pattern="${price_pattern}"/>
                                                    <a>&nbsp</a>
                                                    <input type="number" class="form-control " name="search_max_price"
                                                           id="search_max_price"
                                                           min="0"
                                                           max="10000"
                                                           placeholder="0-10000"
                                                            <c:if test="${!empty search_max_price}">
                                                                value="${search_max_price}"
                                                            </c:if>
                                                           pattern="${price_pattern}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <%----------------------------------------------------- power int--------------------------------------------%>
                                    <div class="form-group" style="color: white">
                                        <label for="power_min" class="cols-sm-2 control-label"
                                                <c:if test="${!empty invalid_search_min_power || !empty invalid_search_max_power}">
                                                    style="color: red"
                                                </c:if>
                                        >
                                            ${power_name_label}
                                            <c:if test="${!empty invalid_search_min_power || !empty invalid_search_max_power}">
                                                ${incorrect_it_enter_message}
                                            </c:if>
                                        </label>
                                        <div class="cols-sm-10">
                                            <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                                <div class="input-group mb-3">
                                                    <input type="number" class="form-control" name="search_min_power"
                                                           id="power_min"
                                                           min="0"
                                                           max="999"
                                                           placeholder="${power_placeholder}"
                                                            <c:if test="${!empty search_min_power}">
                                                                value="${search_min_power}"
                                                            </c:if>
                                                           pattern="${power_pattern}"/>
                                                    <a>&nbsp</a>
                                                    <input type="number" class="form-control" name="search_max_power"
                                                           id="power_max"
                                                           min="0"
                                                           max="999"
                                                           placeholder="${power_placeholder}"
                                                            <c:if test="${!empty search_max_power}">
                                                                value="${search_max_power}"
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
                                        >
                                            ${find_products_button}</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </nav>
            </th>

            <th scope="col" class="col-lg-9" style="padding-top:16px">
                <%--                -------------Table of products-----------%>

                <c:forEach var="entry" items="${products_quantity_map}">
                    <c:set var="current_product" value="${entry.key}"/>
                    <c:set var="current_quantity" value="${entry.value}"/>

                    <table class="product-card table table-bordered"
                           style="background-color: white; border-color: goldenrod">
                        <tbody>
                        <tr style="border-right-width: 2px;border-left-width: 2px;border-top-width: 2px;border-bottom-width: 2px;">
                            <th scope="image" class="col-3" style="background-color: white; height: 200px">
                                <c:if test="${empty current_product.photoString}">
                                    <img src="${path}/images/nophoto.jpg" class="img-fluid mx-auto d-block" alt="img">
                                </c:if>
                                <c:if test="${!empty current_product.photoString}">
                                    <img src="data:image/jpeg;base64,${current_product.photoString}" alt="img"
                                         class="img-fluid mx-auto d-block">
                                </c:if>
                            </th>
                            <th scope="data" class="col-7">
                                <table class="product-data table-borderless">
                                    <tbody>
                                    <tr>
                                        <td>
                                            <div style="background-color: white">
                                                <dl class="row" style="margin-bottom: 0">
                                                    <dt class="col-sm-3">${product_name_label}:</dt>
                                                    <dd class="col-sm-9">
                                                            ${current_product.productName}
                                                    </dd>
                                                    <dt class="col-sm-3 ">${description_name_label}:</dt>
                                                    <dd class="col-sm-9 text-truncate" style="max-width: 600px">
                                                        <abbr title="${current_product.description}">${current_product.description}</abbr>.
                                                    </dd>
                                                    <dt class="col-sm-3">${brands_id_label}:</dt>
                                                    <dd class="col-sm-9">
                                                        <c:out value="${brands_map[current_product.brandId.toString()]}"/>
                                                    </dd>
                                                    <dt class="col-sm-3">${types_id_label}:</dt>
                                                    <dd class="col-sm-9">
                                                        <c:out value="${types_map[current_product.typeId.toString()]}"/>
                                                    </dd>
                                                    <dt class="col-sm-3">${colour_name_label}:</dt>
                                                    <dd class="col-sm-9">
                                                            ${current_product.colour}
                                                    </dd>
                                                    <dt class="col-sm-3">${size_name_label}:</dt>
                                                    <dd class="col-sm-9">
                                                            ${current_product.size}
                                                    </dd>
                                                    <dt class="col-sm-3">${power_name_label}:</dt>
                                                    <dd class="col-sm-9">
                                                            ${current_product.power}
                                                    </dd>

                                                    <dt class="col-sm-3">${price_name_label}:</dt>
                                                    <dd class="col-sm-9">
                                                            ${current_product.price}
                                                    </dd>
                                                    <dt class="col-sm-3">${in_stock}:</dt>
                                                    <dd class="col-sm-9">
                                                        <fmt:parseNumber var="i" integerOnly="true"
                                                                         type="number" value="${current_quantity}"/>
                                                        <c:if test="${i > 10}">
                                                            > 10
                                                            <c:set var="max" value="10"/>
                                                        </c:if>
                                                        <c:if test="${i < 10}">
                                                            ${i}
                                                            <c:set var="max" value="${i}"/>
                                                        </c:if>
                                                        <c:set var="min" value="${1}"/>
                                                        <c:if test="${i == 0}">
                                                            <c:set var="min" value="${0}"/>
                                                        </c:if>
                                                    </dd>
                                                </dl>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </th>
                            <th scope="data" class="col-2 bg-dark opacity-75">
                                <div class="mb-3" style="height: 120px;">
                                </div>
                                <div class="row align-items-end">

                                    <form class="buy form-horizontal needs-validation" novalidate method="post"
                                          action="${path}/controller"
                                          enctype="multipart/form-data">
                                        <input type="hidden" name="command" value="buy">

                                        <div class="mb-3">
                                            <label for="quantityInput" class="form-label"
                                                   style="color: white">${quantity_name_label}</label>
                                            <div class="col-6">
                                                <input type="number" class="form-control" id="quantityInput"
                                                       name="buy_quantity" placeholder="${min}-${max}"
                                                       required
                                                       min="${min}"
                                                       max="${max}"
                                                >
                                                <input type="hidden" name="product_id" value="${current_product.id}">
                                            </div>
                                        </div>
                                        <div class="d-grid gap-1">
                                            <button type="submit" class="btn btn-primary btn-warning"
                                                    <c:if test="${i == 0}">
                                                        disabled
                                                    </c:if>
                                                    id="buttBuy1"
                                                    style="background-color: goldenrod; color: white"
                                                    onclick="CheckBuy()">
                                                    ${buy_button}
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </th>
                        </tr>
                        </tbody>
                    </table>
                </c:forEach>
                <div class="d-grid gap-2 d-md-flex justify-content-md-center" style="color: white">

                    <div class="cols-sm-10">
                        <div class="input-group">
                            <div class="input-group mb-3">
                                <button class="btn btn-primary me-md-2" type="button" style="right: 7px;">
                                    &leftarrow;${previous_page_button}</button>
                                <a>  ${page_number} 1 ${page_of} 5 </a>
                                <button class="btn btn-primary" type="button"
                                        style="margin-left: 15px;">${next_page_button} &rightarrow;
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </th>
        </tr>


    </table>


    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>

<script>


    (function () {
        'use strict'

// Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
        var forms = document.querySelectorAll('.needs-validation')

// Зацикливайтесь на них и предотвращайте отправку
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


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


<fmt:message key="main.page.label" var="main_page_label"/>
<fmt:message key="brands.name" var="brands_id_label"/>
<fmt:message key="incorrect.he.enter" var="incorrect_he_enter_message"/>
<fmt:message key="incorrect.she.enter" var="incorrect_she_enter_message"/>
<fmt:message key="incorrect.it.enter" var="incorrect_it_enter_message"/>
<fmt:message key="any.brand" var="any_brand_option"/>
<fmt:message key="types.name" var="types_id_label"/>
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
<fmt:message key="add.to.cart.button" var="add_to_cart_button"/>
<fmt:message key="in.stock" var="in_stock"/>
<fmt:message key="nothing.found" var="nothing_found"/>
<fmt:message key="more.hint" var="more_hint"/>
<fmt:message key="product.name.placeholder" var="product_name_placeholder"/>
<fmt:message key="search.word" var="search_word_label"/>
<fmt:message key="order.by" var="order_by_label"/>
<fmt:message key="show.in.stock" var="only_in_stock_label"/>
<fmt:message key="yes" var="yes_radio_label"/>
<fmt:message key="no" var="no_radio_label"/>
<fmt:message key="update.product" var="change_product_button"/>

<c:set var="price_pattern">${validator_pattern.decimalStringPattern}</c:set>
<c:set var="power_pattern">${validator_pattern.integer3StringPattern}</c:set>
<c:set var="quantity_pattern">${validator_pattern.quantityToByPattern}</c:set>
<c:set var="product_name_pattern">${validator_pattern.productNamePattern}</c:set>

<c:set var="search_all">0</c:set>
<c:set var="search_only_in_stock">1</c:set>


<head>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
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
    </div>
    <!-- end of header -->
    <div class="content">
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
                                    <form class="form-horizontal needs-validation" novalidate method="get"
                                          action="${path}/controller"
                                          enctype="multipart/form-data">
                                        <input type="hidden" name="command" value="go_to_main">
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
                                                    <%--       Dropdown list--%>
                                                    <div class="input-group mb-3">
                                                        <select class="form-select inputFilter" name="search_brand_id"
                                                                id="search_brand_id">

                                                            <c:if test="${!empty search_brand_id && (search_brand_id != '0')}">
                                                                <option
                                                                        value="">
                                                                        ${any_brand_option}
                                                                </option>
                                                                <option
                                                                        value="${search_brand_id}"
                                                                        selected>${brands_map[search_brand_id]}
                                                                </option>
                                                            </c:if>
                                                            <c:if test="${empty search_brand_id || (search_brand_id == '0')}">
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
                                                    <%--     end  Dropdown list--%>
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
                                                    <%--       Dropdown list--%>
                                                    <div class="input-group mb-3">
                                                        <select class="form-select inputFilter" id="search_type_id"
                                                                name="search_type_id">
                                                            <c:if test="${!empty search_type_id && (search_type_id != '0')}">
                                                                <option
                                                                        value="">
                                                                        ${any_type_option}
                                                                </option>
                                                                <option
                                                                        value="${search_type_id}"
                                                                        selected>${types_map[search_type_id]}
                                                                </option>
                                                            </c:if>
                                                            <c:if test="${empty search_type_id || (search_type_id == '0')}">
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
                                                    <%--    end   Dropdown list--%>
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
                                                        <input type="number" class="form-control inputFilter"
                                                               name="search_min_price"
                                                               id="search_min_price"
                                                               min="0"
                                                               max="10000"
                                                               placeholder="0-10000"
                                                                <c:if test="${!empty search_min_price}">
                                                                    value="${search_min_price}"
                                                                </c:if>
                                                               pattern="${price_pattern}"/>
                                                        <a>&nbsp</a>
                                                        <input type="number" class="form-control inputFilter"
                                                               name="search_max_price"
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
                                                        <input type="number" class="form-control inputFilter"
                                                               name="search_min_power"
                                                               id="power_min"
                                                               min="0"
                                                               max="999"
                                                               placeholder="${power_placeholder}"
                                                                <c:if test="${!empty search_min_power}">
                                                                    value="${search_min_power}"
                                                                </c:if>
                                                               pattern="${power_pattern}"/>
                                                        <a>&nbsp</a>
                                                        <input type="number" class="form-control inputFilter"
                                                               name="search_max_power"
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
                                        <%----------------------------------------------------- words in name--------------------------------------%>
                                        <div class="form-group" style="color: white">
                                            <label for="search_min_price" class="cols-sm-2 control-label"
                                                    <c:if test="${!empty invalid_search_min_price || !empty invalid_search_max_price}">
                                                        style="color: red"
                                                    </c:if>
                                            >
                                                ${search_word_label}
                                                <c:if test="${!empty invalid_search_min_price || !empty invalid_search_max_price}">
                                                    ${incorrect_she_enter_message}
                                                </c:if>
                                            </label>

                                            <div class="cols-sm-10">
                                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                                    <div class="input-group mb-3">
                                                        <input type="text" class="form-control inputFilter"
                                                               name="search_word"
                                                               id="search_word"
                                                               maxlength="25"
                                                               pattern="${product_name_pattern}"
                                                        <c:if test="${!empty search_word}">
                                                               value="${search_word}"
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%--------------------------------------------Order by----------------------------------------------%>
                                        <div class="form-group" style="color: white">
                                            <label for="search_brand_id" class="cols-sm-2 control-label">
                                                ${order_by_label}
                                            </label>
                                            <div class="cols-sm-10">
                                                <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>
                                                    <%--       Dropdown list--%>
                                                    <div class="input-group mb-3">
                                                        <select class="form-select inputFilter" name="order_by"
                                                                id="order_by">
                                                            <option
                                                            ${power_name_label}
                                                                    value="${order_by}"
                                                                    selected>
                                                                <fmt:message key="${order_by}"
                                                                             var="order_by_elem_label"/>
                                                                ${order_by_elem_label}
                                                            </option>

                                                            <c:forEach var="order_by_elem" items="${order_array}">
                                                                <c:if test="${order_by_elem ne order_by}">
                                                                    <option value=${order_by_elem}>
                                                                        <fmt:message key="${order_by_elem}"
                                                                                     var="order_by_elem_label"/>
                                                                            ${order_by_elem_label}
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <%--     end  Dropdown list--%>
                                                </div>
                                            </div>
                                        </div>
                                        <%--------------------------------------------Exists in stock----------------------------------------------%>
                                        <div class="form-group" style="color: white">
                                            <label for="search_brand_id" class="cols-sm-2 control-label">
                                                ${only_in_stock_label}
                                            </label>
                                            <div class="cols-sm-10 ">
                                                <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>
                                                    <div class="input-group mb-3">
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input inputFilter"
                                                                   type="radio"
                                                                   name="search_in_stock" id="search_in_stock1"
                                                                   value="1"
                                                            <c:if test="${search_in_stock == search_only_in_stock}">
                                                                   checked
                                                            </c:if>
                                                            >
                                                            <label class="form-check-label" for="search_in_stock1">
                                                                ${yes_radio_label}
                                                            </label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input inputFilter" type="radio"
                                                                   name="search_in_stock" id="search_in_stock2"
                                                                   value="0"
                                                            <c:if test="${search_in_stock == search_all}">
                                                                   checked
                                                            </c:if>
                                                            >
                                                            <label class="form-check-label" for="search_in_stock2">
                                                                ${no_radio_label}
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <%------------------------------------------------------Button-----------------------------------------------%>
                                        <div class="d-grid gap-1">
                                            <button type="submit" class="btn btn-primary btn-warning"
                                                    name="page_button"
                                                    value="search_button"
                                                    id="butt"
                                                    style="color: white"
                                            >
                                                ${find_products_button}</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </nav>
                </th>
                <%-----------------------------------------------------main part----------------------------------------------------%>
                <th scope="col" class="col-lg-9" style="padding-top:16px">
                    <%--                -------------Table of products-----------%>
                    <c:if test="${!empty products_quantity_map}">
                        <c:forEach var="entry" items="${products_quantity_map}">
                            <c:set var="current_product" value="${entry.key}"/>
                            <c:set var="current_quantity" value="${entry.value}"/>
                            <table class="product-card table table-bordered"
                                   style="background-color: white; border-color: orange">
                                <tbody>
                                <tr style=" border-width: 2px">
                                        <%--                                    --------------------------image part-----------------------%>
                                    <th scope="image" class="col-3" style="background-color: white; height: 200px">
                                        <div class="card border-0 d-block">
                                            <c:if test="${empty current_product.photoString}">
                                                <img src="${path}/images/nophoto.jpg" class="img-fluid mx-auto d-block"
                                                     alt="img"
                                                >
                                            </c:if>
                                            <c:if test="${!empty current_product.photoString}">
                                                <img src="data:image/jpeg;base64,${current_product.photoString}"
                                                     alt="img"
                                                     class="img-fluid mx-auto"
                                                     style="max-height: 250px">
                                            </c:if>
                                            <a href="javascript:void(0);"
                                               title="${more_hint}"
                                               class="stretched-link"
                                               onClick=window.open("${path}/controller?command=show_product&id=${current_product.id}","Product","width=1400,height=650,left=300,toolbar=no,status=no,resizable=no,location=no,directories=no");></a>
                                        </div>
                                    </th>
                                        <%--                                    --------------------------description part------------------%>
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
                                                                <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${current_product.price}"/>
                                                            </dd>
                                                            <dt class="col-sm-3">${in_stock}:</dt>
                                                            <dd class="col-sm-9">
                                                                <fmt:parseNumber var="i" integerOnly="true"
                                                                                 type="number"
                                                                                 value="${current_quantity}"/>
                                                                <c:if test="${i > sess_cart.maxQuantityOfOneProductToBy}">
                                                                    > 10
                                                                    <c:set var="max"
                                                                           value="${sess_cart.maxQuantityOfOneProductToBy}"/>
                                                                </c:if>
                                                                <c:if test="${i <= sess_cart.maxQuantityOfOneProductToBy}">
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
                                        <%--                                    ---------------------------Add to cart part----------------------%>
                                    <th scope="data" class="col-2 bg-dark opacity-75">
                                        <div class="mb-3" style="height: 120px;">
                                            <c:if test="${access_level eq 'ADMIN'}">

                                                <form class="go-to-change_product form-horizontal">
                                                    <div class="d-grid gap-1">
                                                        <button class="btn btn-primary"
                                                                type="button"
                                                                onclick=window.open("${path}/controller?command=go_update_product&id=${current_product.id}","Changeproduct")>
                                                        ${change_product_button} id:${current_product.id}
                                                        </button>
                                                    </div>
                                                </form>
                                            </c:if>
                                        </div>
                                        <div class="row align-items-end">
                                            <form class="buy form-horizontal needs-validation" novalidate method="post"
                                                  action="${path}/controller"
                                                  enctype="multipart/form-data">
                                                <input type="hidden" name="command" value="add_to_cart">
                                                <input type="hidden" name="id"
                                                       value="${current_product.id}">
                                                <div class="mb-3">
                                                    <label for="quantityInputId${current_product.id}" class="form-label"
                                                           style="color: white">${quantity_name_label}</label>
                                                    <div class="col-6">
                                                        <input type="number" class="form-control"
                                                               id="quantityInputId${current_product.id}"
                                                               name="quantity" placeholder="${min}-${max}"
                                                               required
                                                               min="${min}"
                                                               max="${max}"
                                                        >
                                                    </div>
                                                </div>
                                                <div class="d-grid gap-1">
                                                    <button type="button" class="btn btn-warning"
                                                            <c:if test="${i == 0}">
                                                                disabled
                                                            </c:if>
                                                            id="buttBuy1"
                                                            style="color: white"
                                                            onclick="
                                                                    if (!this.form.checkValidity()) {
                                                                    this.form.className +=' was-validated';
                                                                    // window.alert('стоять!!!');
                                                                    return;
                                                                    }
                                                                    let quantityToSend = '&quantity='+document.getElementById('quantityInputId${current_product.id}').value;
                                                                    let fullPath = '${path}/controller?command=add_to_cart&id=${current_product.id}' + quantityToSend;
                                                                    window.open(fullPath,'ShowCart','width=1400,height=650,left=300,toolbar=no,status=no,resizable=no,location=no,directories=no');"
                                                    >
                                                            ${add_to_cart_button}
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </th>
                                </tr>
                                </tbody>
                            </table>
                        </c:forEach>
                        <form id="goPages" onsubmit="copyForm()" action="${path}/controller">
                            <input type="hidden" name="command" value="go_to_main">
                            <input type="hidden" name="search_page" value=${search_page}>
                            <div class="d-grid gap-2 d-md-flex justify-content-md-center" style="color: white">
                                <div class="cols-sm-10">
                                    <div class="input-group">
                                        <div class="input-group mb-3">
                                            <button class="btn btn-primary me-md-2"
                                                    type="submit"
                                                    style="right: 7px;"
                                                    name="page_button"
                                                    value="previous_page">
                                                &leftarrow;${previous_page_button}</button>
                                            <a>  ${page_number} ${search_page} ${page_of} ${total_page} </a>
                                            <button class="btn btn-primary"
                                                    type="submit"
                                                    style="margin-left: 15px;"
                                                    name="page_button"
                                                    value="next_page"
                                            >${next_page_button} &rightarrow;
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${empty products_quantity_map}">
                        <table class="product-card table table-bordered"
                               style="background-color: white; border-color: orange">
                            <tbody>
                            <tr style="border-right-width: 2px;border-left-width: 2px;border-top-width: 2px;border-bottom-width: 2px;">
                                <th scope="col" class="col-3" style="background-color: white; height: 120px">
                                    <div class="position-relative">
                                        <div class="position-absolute top-50 start-50 translate-middle"
                                             style=" margin-top: 45px;">
                                                ${nothing_found}
                                        </div>
                                    </div>
                                </th>
                            </tr>
                            </tbody>
                        </table>
                    </c:if>
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

<script>
    function copyForm() {
        let elems = document.getElementsByClassName("inputFilter")
        for (let i in elems) {
            if (!(elems[i].className === 'form-check-input inputFilter') || (elems[i].checked)) {
                let inputElement = document.createElement("input");
                inputElement.type = "hidden"
                inputElement.name = elems[i].name
                inputElement.value = elems[i].value
                document.getElementById("goPages").appendChild(inputElement);
            }
        }
        return true;
    }
</script>
</body>
</html>


<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 19.05.2022
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>

<c:set var="path">${pageContext.request.contextPath}</c:set>


<fmt:message key="update.product" var="head_label"/>
<fmt:message key="brands.name" var="brands_id_label"/>
<fmt:message key="incorrect.he.enter" var="incorrect_he_enter_message"/>
<fmt:message key="incorrect.she.enter" var="incorrect_she_enter_message"/>
<fmt:message key="incorrect.it.enter" var="incorrect_it_enter_message"/>
<fmt:message key="select.your.brand" var="select_your_brand_option"/>
<fmt:message key="types.name" var="types_id_label"/>
<fmt:message key="select.your.productType" var="select_your_type_option"/>
<fmt:message key="product.name" var="product_name_label"/>
<fmt:message key="exists.choose.other" var="exists_choose_other_message"/>
<fmt:message key="product.name.placeholder" var="product_name_placeholder"/>
<fmt:message key="description.name" var="description_name_label"/>
<fmt:message key="description.placeholder" var="description_name_placeholder"/>
<fmt:message key="price.name" var="price_name_label"/>
<fmt:message key="price.placeholder" var="price_placeholder"/>
<fmt:message key="colour.name" var="colour_name_label"/>
<fmt:message key="colour.placeholder" var="colour_placeholder"/>
<fmt:message key="power.name" var="power_name_label"/>
<fmt:message key="power.placeholder" var="power_placeholder"/>
<fmt:message key="power.placeholder" var="quantity_placeholder"/>
<fmt:message key="size.name" var="size_name_label"/>
<fmt:message key="size.placeholder" var="size_placeholder"/>
<fmt:message key="quantity.name" var="quantity_name_label"/>
<fmt:message key="photo.name" var="photo_name_label"/>
<fmt:message key="choose.picture" var="choose_picture_label"/>
<fmt:message key="update.photo" var="update_photo_button"/>
<fmt:message key="update.data" var="update_date_button"/>
<fmt:message key="close.window" var="close_button"/>
<fmt:message key="photo.of.product" var="photo_of_product_label"/>
<fmt:message key="data.of.product" var="data_of_product_label"/>


<c:set var="product_name_pattern">${validator_pattern.productNamePattern}</c:set>
<c:set var="description_name_pattern">${validator_pattern.descriptionPattern}</c:set>
<c:set var="colour_pattern">${validator_pattern.colourPattern}</c:set>
<c:set var="size_pattern">${validator_pattern.sizePattern}</c:set>
<c:set var="price_pattern">${validator_pattern.decimalStringPattern}</c:set>
<c:set var="power_pattern">${validator_pattern.integer3StringPattern}</c:set>
<c:set var="quantity_pattern">${validator_pattern.integer3StringPattern}</c:set>

<c:if test="${!empty product}">
    <c:set var="local_product_id">${product.id}</c:set>
    <c:set var="local_brand_id">${product.brandId}</c:set>
    <c:set var="local_type_id">${product.typeId}</c:set>
    <c:set var="local_photo_string">${product.photoString}</c:set>
</c:if>
<c:if test="${empty product}">
    <c:set var="local_product_id">${id}</c:set>
    <c:set var="local_brand_id">${brand_id}</c:set>
    <c:set var="local_type_id">${type_id}</c:set>
    <c:set var="local_photo_string">${photo_string}</c:set>
</c:if>

<head>
    <title>${head_label}</title>
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/enter.css">
    <script src="${path}/bootstrap/js/bootstrap.min.js"></script>

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
    <%--    <div class="header">--%>
    <%--        <%@include file="../header.jsp" %>--%>
    <%--    </div> <!-- end of header -->--%>
    <div class="content">
        <div class="container" style="max-width: none">
            <div class="row justify-content-center">
                <%-----------------------------------------Photo part---------------------------------------                --%>
                <div class="col-md-5">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large">
                            ${photo_of_product_label}
                            ${local_product_id}
                        </div>
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large"></div>
                        <div class="card-body bg-dark bg-opacity-75">
                            <form class="form-horizontal needs-validation1" novalidate method="post"
                                  action="${path}/controller"
                                  enctype="multipart/form-data">
                                <input type="hidden" name="command" value="update_photo">
                                <input type="hidden" name="id" value="${local_product_id}">

                                <div class="card border-0 d-block">
                                    <c:if test="${empty local_photo_string}">
                                        <img src="${path}/images/nophoto.jpg" class="img-fluid mx-auto d-block"
                                             alt="img">
                                    </c:if>
                                    <c:if test="${!empty local_photo_string}">
                                        <img src="data:image/jpeg;base64,${local_photo_string}"
                                             alt="img"
                                             class="img-fluid mx-auto ">
                                    </c:if>
                                </div>
                                <%----------------------------------------------------- photo blob input-------------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="photo" class="cols-sm-2 control-label"
                                            <c:if test="${(!empty photo_message) && (photo_message == 'update.fail') }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${photo_name_label}
                                        <c:if test="${(!empty photo_message) && (photo_message == 'update.fail') }">
                                            <fmt:message key="incorrect.she.enter"/>
                                        </c:if>
                                    </label>

                                    <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                        <div class="input-group mb-3">
                                            <input type="file" class="visually-hidden" name="photo"
                                                   id="photo"
                                                   size="4194304"
                                                   required
                                                   accept=".jpg, .jpeg"/>
                                            <label for="photo" class="btn btn-primary btn-light border-warning"
                                                   id="photolabel">${choose_picture_label}</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <button type="submit" class="btn btn-warning col-6"
                                            id="butt_photo"
                                            style="color: white"
                                            onclick="CheckPicture()">
                                        ${update_photo_button}</button>
                                    <button type="button" class="btn btn-primary col-6"
                                            id="close_window"
                                            onclick="window.close()">
                                        ${close_button}</button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
                <%------------------------------------------Data part------------------------------------------------------%>
                <div class="col-md-7">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large">
                            ${data_of_product_label}
                            ${local_product_id}
                        </div>
                        <div class="card-header bg-light fw-bold"
                             style="text-align:center; color: black; font-size: large"></div>
                        <div class="card-body bg-dark bg-opacity-75">
                            <form class="form-horizontal needs-validation" novalidate method="post"
                                  action="${path}/controller"
                                  enctype="multipart/form-data">
                                <input type="hidden" name="command" value="update_product_data">
                                <input type="hidden" name="id" value="${local_product_id}">
                                <input type="hidden" name="photo_string" value="${local_photo_string}">
                                <%------------------------------------------------------ProductName------------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="product_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_product_name || !empty busy_product_name }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${product_name_label}
                                        <c:if test="${!empty invalid_product_name}">
                                            ${incorrect_it_enter_message}
                                        </c:if>

                                        <c:if test="${!empty busy_product_name}">
                                            ${exists_choose_other_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="fa fa-user fa"
                                                                    aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                       <c:if test="${!empty invalid_product_name}">
                                                            is-invalid
                                                       </c:if>
                                                       " name="product_name"
                                                       id="product_name"
                                                       placeholder="${product_name_placeholder}"
                                                        <c:if test="${!empty product_name}">
                                                            value="${product_name}"
                                                        </c:if>
                                                        <c:if test="${empty product_name}">
                                                            value="${product.productName}"
                                                        </c:if>
                                                       required pattern="${product_name_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%------------------------------------------------------Brands-----------------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="brand_id" class="cols-sm-2 control-label"
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
                                            <%--                                    Dropdown list--%>
                                            <div class="input-group mb-3">
                                                <select class="form-select inputFilter" name="brand_id"
                                                        id="brand_id">
                                                    <option
                                                            value="${local_brand_id}"
                                                            <c:set var="selected_brand_id">${local_brand_id}</c:set>
                                                            selected>${brands_map[selected_brand_id]}
                                                    </option>
                                                    <c:forEach var="brandEnter" items="${brands_map}">
                                                        <c:if test="${brandEnter.key ne local_brand_id}">
                                                            <option value=${brandEnter.key}>${brandEnter.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <%--                               end Dropdown list--%>
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
                                            <%--                                    Dropdown list--%>
                                            <div class="input-group mb-3">
                                                <select class="form-select inputFilter" name="type_id"
                                                        id="type_id">
                                                    <option
                                                            value="${local_type_id}"
                                                            <c:set var="selected_type_id">${local_type_id}</c:set>
                                                            selected>${types_map[selected_type_id]}
                                                    </option>
                                                    <c:forEach var="typeEnter" items="${types_map}">
                                                        <c:if test="${typeEnter.key ne local_type_id}">
                                                            <option value=${typeEnter.key}>${typeEnter.value}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <%--                              end Dropdown list--%>
                                        </div>
                                    </div>
                                </div>
                                <%----------------------------------------------------- description (text)-----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="description" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_description}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${description_name_label}
                                        <c:if test="${!empty invalid_description}">
                                            ${incorrect_it_enter_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="fa fa-user fa"
                                                                    aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <textarea class="form-control
                                                 <c:if test="${!empty invalid_description}">
                                                     is-invalid
                                                 </c:if>
                                                          " name="description"
                                                          id="description"
                                                          placeholder="${description_name_placeholder}"
                                                          required
                                                        <c:if test="${!empty description}">
                                                            <c:set var="description_value">${description}</c:set>
                                                        </c:if>
                                                        <c:if test="${empty description}">
                                                            <c:set var="description_value">${product.description}</c:set>
                                                        </c:if>
                                                >${description_value}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%----------------------------------------------------- price dec(10,2)--------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="price" class="cols-sm-2 control-label"
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
                                                <input type="text" class="form-control
                                                <c:if test="${!empty invalid_price}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="price"
                                                       id="price"
                                                       placeholder=${price_placeholder}
                                                       <c:if test="${!empty price}">
                                                               value="${price}"
                                                </c:if>
                                                        <c:if test="${empty price}">
                                                            value="${product.price}"
                                                        </c:if>
                                                       required pattern="${price_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%----------------------------------------------------- colour varchar(45)-----------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="colour" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_colour}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${colour_name_label}
                                        <c:if test="${!empty invalid_colour}">
                                            ${incorrect_he_enter_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_colour}">
                                                     is-invalid
                                                 </c:if>
                                                       " name="colour"
                                                       id="colour"
                                                       placeholder="${colour_placeholder}"
                                                        <c:if test="${!empty colour}">
                                                            value="${colour}"
                                                        </c:if>
                                                        <c:if test="${empty colour}">
                                                            value="${product.colour}"
                                                        </c:if>
                                                       required pattern="${colour_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%----------------------------------------------------- power int--------------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="power" class="cols-sm-2 control-label"
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
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_power}">
                                                     is-invalid
                                                 </c:if>
                                                       " name="power"
                                                       id="power"
                                                       placeholder="${power_placeholder}"
                                                        <c:if test="${!empty power}">
                                                            value="${power}"
                                                        </c:if>
                                                        <c:if test="${empty power}">
                                                            value="${product.power}"
                                                        </c:if>
                                                       required pattern="${power_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%----------------------------------------------------- size varchar (45)------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="size" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_size}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${size_name_label}
                                        <c:if test="${!empty invalid_size}">
                                            ${incorrect_he_enter_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_size}">
                                                     is-invalid
                                                 </c:if>
                                                       " name="size"
                                                       id="size"
                                                       placeholder="${size_placeholder}"
                                                        <c:if test="${!empty size}">
                                                            value="${size}"
                                                        </c:if>
                                                        <c:if test="${empty size}">
                                                            value="${product.size}"
                                                        </c:if>
                                                       required pattern="${size_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%----------------------------------------------------- quantity_in_stock int--------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="quantity_in_stock" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_quantity}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        ${quantity_name_label}
                                        <c:if test="${!empty invalid_quantity_in_stock}">
                                            ${incorrect_it_enter_message}
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control
                                                 <c:if test="${!empty invalid_quantity_in_stock}">
                                                     is-invalid
                                                 </c:if>
                                                        " name="quantity_in_stock"
                                                       id="quantity_in_stock"
                                                       placeholder=${quantity_placeholder}
                                                       <c:if test="${!empty quantity_in_stock}">
                                                               value="${quantity_in_stock}"
                                                </c:if>
                                                        <c:if test="${empty quantity_in_stock}">
                                                            value="${quantity}"
                                                        </c:if>
                                                       required pattern="${quantity_pattern}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%------------------------------------------------------Button-----------------------------------------------%>
                                <div class="d-grid gap-1">
                                    <button type="submit" class="btn btn-primary btn-warning"
                                            id="butt_data"
                                            style="color: white"
                                    >
                                        ${update_date_button}</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container text-center">
        <div class="footer" style="color: white"><ft:footerTag/></div>
    </div>
</div>
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
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation1')
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
    const elem = document.getElementById('photo');
    const photolbl = document.getElementById('photolabel')
    const buttAdd = document.getElementById('butt_photo')

    elem.addEventListener('change', CheckPicture);
    buttAdd.addEventListener('listener', CheckPicture);

    function CheckPicture() {
        let subname = elem.value.substring(elem.value.lastIndexOf('\\') + 1);
        const choice = elem.value;
        if (choice === '') {
            photolbl.style.color = 'red';
            photolbl.style.borderColor = 'red';

        } else {
            photolbl.style.color = 'green';
            photolbl.style.borderColor = 'green';
            photolbl.textContent = subname;
        }
    }
</script>
</body>


<%@ page import="java.util.HashMap" %>


<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 19.04.2022
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>


<div> <%--TODO only for tests!!!!!!!!!--%>

<%--    <%--%>
<%--        HashMap brands_map = new HashMap();--%>
<%--        brands_map.put("Belsvet", "1");--%>
<%--        brands_map.put("Beltma", "2");--%>
<%--        brands_map.put("SunLight", "7");--%>
<%--        pageContext.setAttribute("brands_map", brands_map);--%>
<%--    %>--%>
<%--    <%--%>
<%--        HashMap types_map = new HashMap();--%>
<%--        types_map.put("Wall", "1");--%>
<%--        types_map.put("Ceiling", "2");--%>
<%--        types_map.put("Outdoor", "200");--%>
<%--        pageContext.setAttribute("types_map", types_map);--%>
<%--    %>--%>

</div><%--TODO end for tests--%>


<link rel="stylesheet" href="${absolutePath}/css/enter.css">

<!DOCTYPE html>

<head>
    <title><title><fmt:message key="add.new.product"/></title>
    <link href="${absolutePath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <%--    TODO Check if Admin?!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--%>
    <%--    TODO Not ready!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--%>

</head>
<body>
<div class="wrapper">
    <div class="header">
        <%@include file="../header.jsp" %>
    </div> <!-- end of header -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card">

                        <div class="card-header bg-light" style="text-align:center; color: black; font-size: large"><fmt:message key="add.new.product"/></div>
                        <div class="card-body bg-dark bg-opacity-75">

                            <form class="form-horizontal" method="post" action="${absolutePath}/controller"
                                  enctype="multipart/form-data">
                                <input type="hidden" name="command" value="add_new_product">

                                <%------------------------------------------------------Brands---------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="brand" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_brand_id}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="brands_id"/>
                                        <c:if test="${!empty invalid_brand_id}">
                                            <fmt:message key="incorrect.he.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>
                                            <%--                                    выпадающий список--%>
                                            <div class="input-group mb-3">
                                                <select class="form-select" name="brand_id" id="brand" required>
                                                    <option value="" disabled selected><fmt:message
                                                            key="select.your.brand"/></option>
                                                    <c:forEach var="brandEnter" items="${brands_map}">
                                                        <option value=${brandEnter.value}>${brandEnter.key}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <%--                               конец     выпадающий список--%>
                                        </div>
                                    </div>
                                </div>
                                <%--------------------------------------------------Types------------------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="type_id" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_type_id}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="types_id"/>
                                        <c:if test="${!empty invalid_type_id}">
                                            <fmt:message key="incorrect.he.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>

                                            <%--                                    выпадающий список--%>
                                            <div class="input-group mb-3">
                                                <select class="form-select" id="type_id" name="type_id" required>
                                                    <option value="" disabled selected><fmt:message
                                                            key="select.your.productType"/></option>
                                                    <c:forEach var="typeEnter" items="${types_map}">
                                                        <option value=${typeEnter.value}>${typeEnter.key}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <%--                               конец     выпадающий список--%>
                                        </div>
                                    </div>
                                </div>
                                <%------------------------------------------------------ProductName---------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="product_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_product_name || !empty busy_product_name }">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="product.name"/>
                                        <c:if test="${!empty invalid_product_name}">
                                            <fmt:message key="incorrect.it.enter"/>
                                        </c:if>
                                        <c:if test="${!empty busy_product_name}">
                                            <fmt:message key="exists.choose.other"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="fa fa-user fa"
                                                                    aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="product_name"
                                                       id="product_name"
                                                       placeholder="<fmt:message key="product.name.pattern"/>"
                                                        <c:if test="${!empty product_name}">
                                                            value="${product_name}"
                                                        </c:if>
                                                       required pattern="^[A-Za-zА-Яа-я0-9_ //-]{3,60}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- description (text)--------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="description" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_description}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="description.name"/>
                                        <c:if test="${!empty invalid_description}">
                                            <fmt:message key="incorrect.it.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="fa fa-user fa"
                                                                    aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="description"
                                                       id="description"
                                                       placeholder="<fmt:message key="description.pattern"/>"
                                                        <c:if test="${!empty description}">
                                                            value="${description}"
                                                        </c:if>
                                                       required pattern="^[A-Za-zА-Яа-я0-9_ //.;,//(//)]+$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                    ------------------------------------------- price dec(10,2)--------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="price" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_price}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="price.name"/>
                                        <c:if test="${!empty invalid_price}">
                                            <fmt:message key="incorrect.she.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="price"
                                                       id="price"
                                                       placeholder="<fmt:message key="price.pattern"/>"
                                                        <c:if test="${!empty price}">
                                                            value="${price}"
                                                        </c:if>
                                                       required pattern="^((\d{1,5}\.\d{0,2})|(\d{1,5}))$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- colour varchar(45)----------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="colour" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_colour}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="colour.name"/>
                                        <c:if test="${!empty invalid_colour}">
                                            <fmt:message key="incorrect.he.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="colour"
                                                       id="colour"
                                                       placeholder="<fmt:message key="colour.pattern"/>"
                                                        <c:if test="${!empty colour}">
                                                            value="${colour}"
                                                        </c:if>
                                                       required pattern="^[A-Za-zА-Яа-я0-9_ //-]{3,60}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- power int--------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="power" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_power}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="power.name"/>
                                        <c:if test="${!empty invalid_power}">
                                            <fmt:message key="incorrect.it.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="power"
                                                       id="power"
                                                       placeholder="<fmt:message key="power.pattern"/>"
                                                        <c:if test="${!empty power}">
                                                            value="${power}"
                                                        </c:if>
                                                       required pattern="^[0-9]{1,3}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- size varchar (45)--------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="size" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_size}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="size.name"/>
                                        <c:if test="${!empty invalid_size}">
                                            <fmt:message key="incorrect.he.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="size"
                                                       id="size"
                                                       placeholder="<fmt:message key="size.pattern"/>"
                                                        <c:if test="${!empty size}">
                                                            value="${size}"
                                                        </c:if>
                                                       required pattern="^[A-Za-zА-Яа-я0-9_* ]{3,45}$"/>
                                                <%--                                            TODO * в паттерне?--%>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- quantity_in_stock int--------------------------------------%>
                                <div class="form-group" style="color: white">
                                    <label for="quantity" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_quantity}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="quantity.name"/>
                                        <c:if test="${!empty invalid_quantity}">
                                            <fmt:message key="incorrect.it.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="quantity"
                                                       id="quantity"
                                                       placeholder="<fmt:message key="power.pattern"/>"
                                                        <c:if test="${!empty quantity}">
                                                            value="${quantity}"
                                                        </c:if>
                                                       required pattern="^[0-9]{1,3}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ------------------------------------------- photo blob--------------------------------------%>

                                <div class="form-group" style="color: white">
                                    <label for="photo" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_photo}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="photo.name"/>
                                        <c:if test="${!empty invalid_photo}">
                                            <fmt:message key="incorrect.she.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="file" class="form-control" name="photo"
                                                       id="photo"
                                                       size="4194304"
                                                       required
                                                       accept=".jpg, .jpeg"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%------------------------------------------Button--------------------------------------------%>
                                <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                    <fmt:message
                                            key="add.new.product.button"/></button>

                                <%--                        //TODO куда переходить?--%>


                                <div class="login-register">
                                    <a href="${absolutePath}/index.jsp"><fmt:message key="return.main.page"/></a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer"><ft:footerTag/></div>
</div>

</body>
</html>

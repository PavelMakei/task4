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

    <%
        HashMap brands = new HashMap();
        brands.put("Belsvet", "1");
        brands.put("Beltma", "2");
        brands.put("SunLight", "3");
        pageContext.setAttribute("brands", brands);
    %>
    <%
        HashMap types = new HashMap();
        types.put("Wall", "1");
        types.put("Ceiling", "2");
        types.put("Outdoor", "3");
        pageContext.setAttribute("types", types);
    %>

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
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card">

                        <div class="card-header" style="text-align:center;"><fmt:message key="add.new.product"/></div>
                        <div class="card-body">

                            <form class="form-horizontal" method="post" action="${absolutePath}/controller"
                                  enctype="multipart/form-data">
                                <input type="hidden" name="command" value="addnewproduct">

                                <%------------------------------------------------------Brands---------------------------%>
                                <div class="form-group">
                                    <label for="brand" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_brand}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="brands_id"/>
                                        <c:if test="${!empty invalid_brand}">
                                            <fmt:message key="incorrect.enter"/>
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
                                                    <c:forEach var="brandEnter" items="${brands}">
                                                        <option value=${brandEnter.value}>${brandEnter.key}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <%--                               конец     выпадающий список--%>
                                        </div>
                                    </div>
                                </div>
                                <%--------------------------------------------------Types------------------------------------------------%>
                                <div class="form-group">
                                    <label for="inputGroupSelect02" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_type_id}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="types_id"/>
                                        <c:if test="${!empty invalid_type_id}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="user-select-auto"
                                                                    aria-hidden="true"></i></span>

                                            <%--                                    выпадающий список--%>
                                            <div class="input-group mb-3">
                                                <select class="form-select" id="inputGroupSelect02" name="tupe_id">
                                                    <option value="" disabled selected><fmt:message
                                                            key="select.your.type"/></option>
                                                    <c:forEach var="typeEnter" items="${types}">
                                                        <option value=${typeEnter.value}>${typeEnter.key}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <%--                               конец     выпадающий список--%>
                                        </div>
                                    </div>
                                </div>
                                <%------------------------------------------------------ProductName---------------------------------------%>
                                <div class="form-group">
                                    <label for="product_name" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_product_name}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="product.name"/>
                                        <c:if test="${!empty invalid_product_name}">
                                            <fmt:message key="incorrect.enter"/>
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
                                                       required pattern="^[A-Za-zА-Яа-я0-9_]{3,60}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- details (text)--------------------------------------%>
                                <div class="form-group">
                                    <label for="details" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_details}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="details.name"/>
                                        <c:if test="${!empty invalid_details}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-sm"><i class="fa fa-user fa"
                                                                    aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="details_name"
                                                       id="details"
                                                       placeholder="<fmt:message key="details.name.pattern"/>"
                                                        <c:if test="${!empty details_name}">
                                                            value="${details_name}"
                                                        </c:if>
                                                       required pattern="^[A-Za-zА-Яа-я0-9_//.;,//(//)]{1,}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                    ------------------------------------------- price dec(10,2)--------------------------------------%>
                                <div class="form-group">
                                    <label for="price" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_price}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="price.name"/>
                                        <c:if test="${!empty invalid_price}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="price_name"
                                                       id="price"
                                                       placeholder="<fmt:message key="price.pattern"/>"
                                                        <c:if test="${!empty price_name}">
                                                            value="${price_name}"
                                                        </c:if>
                                                       required pattern="^((\d{1,5}\.\d{0,2})|(\d{1,5}))$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- colour varchar(45)----------------------------------------%>
                                <div class="form-group">
                                    <label for="colour" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_colour}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="colour.name"/>
                                        <c:if test="${!empty invalid_colour}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="colour_name"
                                                       id="colour"
                                                       placeholder="<fmt:message key="colour.pattern"/>"
                                                        <c:if test="${!empty colour_name}">
                                                            value="${colour_name}"
                                                        </c:if>
                                                       required pattern="^[A-Za-zА-Яа-я0-9_]{3,60}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- power int--------------------------------------%>
                                <div class="form-group">
                                    <label for="power" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_power}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="power.name"/>
                                        <c:if test="${!empty invalid_power}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="power_name"
                                                       id="power"
                                                       placeholder="<fmt:message key="power.pattern"/>"
                                                        <c:if test="${!empty power_name}">
                                                            value="${power_name}"
                                                        </c:if>
                                                       required pattern="^[0-9]{1,3}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- size varchar (45)--------------------------------------%>
                                <div class="form-group">
                                    <label for="size" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_size}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="size.name"/>
                                        <c:if test="${!empty invalid_size}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="size_name"
                                                       id="size"
                                                       placeholder="<fmt:message key="size.pattern"/>"
                                                        <c:if test="${!empty size_name}">
                                                            value="${size_name}"
                                                        </c:if>
                                                       required pattern="^[A-Za-zА-Яа-я0-9_*]{3,45}$"/>
                                                <%--                                            TODO * в паттерне?--%>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%--                                ------------------------------------------- quantity_in_stock int--------------------------------------%>
                                <div class="form-group">
                                    <label for="quantity" class="cols-sm-2 control-label"
                                            <c:if test="${!empty invalid_quantity}">
                                                style="color: red"
                                            </c:if>
                                    >
                                        <fmt:message key="quantity.name"/>
                                        <c:if test="${!empty invalid_quantity}">
                                            <fmt:message key="incorrect.enter"/>
                                        </c:if>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="quantity_name"
                                                       id="quantity"
                                                       placeholder="<fmt:message key="power.pattern"/>"
                                                        <c:if test="${!empty quantity_name}">
                                                            value="${quantity_name}"
                                                        </c:if>
                                                       required pattern="^[0-9]{1,3}$"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--                                ------------------------------------------- photo blob--------------------------------------%>

                                <div class="form-group">
                                    <label for="photo" class="cols-sm-2 control-label"
                                    >
                                        <fmt:message key="photo.name"/>
                                    </label>
                                    <div class="cols-sm-10">
                                        <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa"
                                                                       aria-hidden="true"></i></span>
                                            <div class="input-group mb-3">
                                                <input type="file" class="form-control" name="file_name"
                                                       id="file"
<%--                                                       size="5242880" &lt;%&ndash;1024*1024*5 bytes&ndash;%&gt;--%>
                                                       required
                                                       accept=".jpg"/>
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

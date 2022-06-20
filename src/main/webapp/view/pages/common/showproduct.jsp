<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 15.05.2022
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:message key="description.name" var="description_name_label"/>
<fmt:message key="price.name" var="price_name_label"/>
<fmt:message key="colour.name" var="colour_name_label"/>
<fmt:message key="power.name" var="power_name_label"/>
<fmt:message key="size.name" var="size_name_label"/>
<fmt:message key="brands.name" var="brands_id_label"/>
<fmt:message key="types.name" var="types_id_label"/>
<fmt:message key="close.window" var="close_hint"/>

<html>

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon"${path}/icons/favicon.ico" type="image/x-icon" />
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon" />
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/css/enter.css" rel="stylesheet">
    <title>${login_label}</title>

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
    <div class="content">
        <div class="container" style="margin: 0; min-width: 100%">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="card border-warning">
                        <div class="card-header bg-light" style="text-align:center; color: black; font-size: large">
                            ${product.productName}
                        </div>
                        <div class="card-body bg-dark bg-opacity-75">
                            <div class="container" style="margin: 0; min-width: 100%">
                                <div class="row">
                                    <div class="col-6 bg-white" style=" padding-left: 0px;">
                                        <c:if test="${empty product.photoString}">
                                            <img src="${path}/images/nophoto.jpg" class="img-fluid mx-auto d-block"
                                                 alt="img"
                                                 style=" min-width: 100%;height: auto;">
                                        </c:if>
                                        <c:if test="${!empty product.photoString}">
                                            <img src="data:image/jpeg;base64,${product.photoString}" alt="img"
                                                 class="img-fluid mx-auto d-block"
                                                 style=" min-width: 100%;height: auto;">
                                        </c:if>
                                    </div>
                                    <div class="col-6 bg-white">
                                        <dl class="row" style="margin-bottom: 0">

                                            <dt class="col-sm-3">${brands_id_label}:</dt>
                                            <dd class="col-sm-9">
                                                ${brand_name}
                                            </dd>
                                            <dt class="col-sm-3">${types_id_label}:</dt>
                                            <dd class="col-sm-9">
                                                ${type_name}
                                            </dd>
                                            <dt class="col-sm-3">${colour_name_label}:</dt>
                                            <dd class="col-sm-9">
                                                ${product.colour}
                                            </dd>
                                            <dt class="col-sm-3">${size_name_label}:</dt>
                                            <dd class="col-sm-9">
                                                ${product.size}
                                            </dd>
                                            <dt class="col-sm-3">${power_name_label}:</dt>
                                            <dd class="col-sm-9">
                                                ${product.power}
                                            </dd>
                                            <dt class="col-sm-3">${price_name_label}:</dt>
                                            <dd class="col-sm-9">
                                                ${product.price}
                                            </dd>
                                            <dt class="col-sm-3 ">${description_name_label}:</dt>
                                            <dd class="col-sm-9 text">
                                                ${product.description}
                                            </dd>
                                        </dl>
                                        <a href="javascript:void(0);"
                                           title="${close_hint}"
                                           class="stretched-link"
                                           onClick=window.close()
                                        >
                                        </a>
                                    </div>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>
<script>
    function closeMe(){
        window.close();
    }
</script>

</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 19.04.2022
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="ctg" uri="customtags" %>--%>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<html>
<head>

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


    <title><fmt:message key="menu.title"/> </title>
    <script>
        function preventBack() {
            window.history.forward();
        }

        setTimeout("preventBack()", 0);
        window.onunload = function() {
            null
        };

    </script>
</head>
<body>
<div class="page">
    <header>
<%--        <%@include file="../header/header.jsp"%>--%>
    </header>
    <div class="container justify-content-center">
        <h3 class="text-center p-3"><fmt:message key="menu.new_product"/></h3>
        </br>
        <form name="AddProductFord" method="post" action="${absolutePath}/controller" novalidate>
            <input type="hidden" name="command" value="insert_new_product"/>
            </br>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_name"/> </label>
                <input type="text" name="product_name" class="form-control">
            </div>
            </br>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_composition"/></label>
                <input type="text" name="product_composition" class="form-control">
            </div>
            </br>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_weight"/></label>
                <input type="text" name="product_weight" class="form-control">
            </div>
            </br>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_calories"/></label>
                <input type="text" name="product_calories" class="form-control">
            </div>
            </br>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_time"/></label>
                <input type="time" name="product_time" class="form-control">
            </div>
            </br>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_discount"/></label>
                <input type="text" name="product_discount" class="form-control">
            </div>
            </br>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_cost"/></label>
                <input type="text" name="product_price" class="form-control">
            </div>
            </br>
            <select class="form-select" aria-label="Default select example" name="product_section">
                <option selected><fmt:message key="menu.product_section"/></option>
                <option value="1"><fmt:message key="section.pizza"/> </option>
            </select>
            </br>
            <div class="text-center">
                <button type="submit" class="btn btn-primary"><fmt:message key="menu.insert_menu"/> </button>
            </div>
        </form>
    </div>

    <div class="text-center">
<%--        <ctg:footertag/>--%>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 03.04.2022
  Time: 4:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<%--<fmt:setLocale value="ru_RU"/>--%>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login.page"/></title>
</head>
<body>
<center>
    <br><h2><fmt:message key="login.page"/></h2>
    <br><fmt:message key="login.welcome"/>
    <br>
    <br><form action="controller" method="post">
    <table>
        <tbody><tr>
            <input type="hidden" name="command" value="login">
            <td><fmt:message key="enter.login"/></td>
            <td><input type="text" placeholder=<fmt:message key="login"/> required="true" name="login">
                pattern="^[a-zAZА-ЯЁа-яё\d]{2,16}$"></td>
        </tr>
        <tr>
<%--            TODO correct pattern!!!!!!!!!! --%>
            <td><fmt:message key="enter.password"/></td>
            <td><input type="password" placeholder=<fmt:message key="password"/>  required="true" name="password"
                       pattern="[a-zAZА-ЯЁа-яЁ]{2,16}"></td>
        </tr>

        <tr>
            <td><input type="reset" value=<fmt:message key="reset.button"/> ></td>
            <td><input type="submit" value=<fmt:message key="enter.button"/> </td>
        </tr>
        </tbody></table>
</form>


</center>

</body>

</html>
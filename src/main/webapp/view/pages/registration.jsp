<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 09.04.2022
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="registration.user"/></title>
</head>
<center>
    <body>
    <br>
    <h2><fmt:message key="registration.user"/></h2>
    <br>Please enter the user details.
    <br>
    <br>
    <form action="controller" method="post">
        <table>
            <tbody>
            <tr>
                <input type="hidden" name="command" value="registration">
                <td>Введите имя</td>
                <td><input type="text" placeholder="Name" required="true" name="first_name"
                           pattern="^[a-zAZА-ЯЁа-яё]{2,16}$"></td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><input type="text" placeholder="Surname" required="true" name="last_name"
                           pattern="^[a-zAZА-ЯЁа-яё]{2,16}$"></td>
            </tr>
            <tr>
                <td>Login</td>
                <td><input type="text" placeholder="Login" required="true" name="login"
                           pattern="^[a-zAZА-ЯЁа-яё\d]{2,16}$"></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" placeholder="Password" required="true" name="password"></td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td><input type="email" placeholder="example@asd.wer" required="true" name="email"></td>
            </tr>
            <tr>
                <td>Phone number</td>
                <td><input type="tel" required="true" placeholder="(029)1234567" pattern="^\(\d{3}\)\d{7}$"
                           name="phone"></td>
            </tr>
            <tr>
                <td><input type="reset"></td>
                <td><input type="submit"></td>
            </tr>
            </tbody>
        </table>
    </form>
    <jsp:forward page="/view/pages/login.jsp"/>
    </body>
</center>

</html>

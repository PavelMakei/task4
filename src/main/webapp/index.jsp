<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.account"/></title>
</head>
<center>
    <body>
    <br>
    <h2><fmt:message key="label.account"/></h2>
    <br>Please enter the user details.
    <br>
    <br>
    <form action="controller" method="post">
        <table>
            <tbody>
            <tr>
                <input type="hidden" name="command" value="login">
                <td>Введите имя</td>
                <td><input type="text" placeholder="Name" required="true" name="first_name"
                           pattern="[a-zAZА-ЯЁа-яЁ]{2,16}"></td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><input type="text" placeholder="Surname" required="true" name="last_name"></td>
            </tr>
            <tr>
                <td>Login</td>
                <td><input type="text" placeholder="Login" required="true" name="login"></td>
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
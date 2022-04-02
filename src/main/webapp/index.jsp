<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<center>
    <br><h2>Registration Page</h2>
    <br>Please enter the user details.
    <br>
    <br><form action="controller" method="post">
    <table>
        <tbody><tr>
            <td>Введите имя</td>
            <td><input type="text" placeholder="Name" required="true" name="first_name" pattern="[a-zAZА-ЯЁа-яЁ]{2,16}"></td>
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
            <td><input type="tel" required="true" placeholder="(029)1234567" pattern="^\(\d{3}\)\d{7}$" name="phone"></td>
        </tr>
        <tr>
            <td><input type="reset"></td>
            <td><input type="submit"></td>
        </tr>
        </tbody></table>
</form>
    <br>
    <br>
</center>

</body>

</html>
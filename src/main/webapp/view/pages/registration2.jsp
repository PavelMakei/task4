<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 28.04.2022
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>

<link rel="stylesheet" href="${absolutePath}/css/enter.css">

<!DOCTYPE html>
<html>
<head>
    <link href="${absolutePath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">


    <title>Title</title>
</head>
<body>
<form class="row g-3 needs-validation" novalidate>
    <div class="col-md-4">
        <label for="validationCustom01" class="form-label">Имя</label>
        <input type="text" class="form-control" id="validationCustom01" value="Иван" required>
        <div class="valid-feedback">
            Все хорошо!
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustom02" class="form-label">Фамилия</label>
        <input type="text" class="form-control" id="validationCustom02" value="Петров" required>
        <div class="valid-feedback">
            Все хорошо!
        </div>
    </div>
    <div class="col-md-4">
        <label for="validationCustomUsername" class="form-label">Имя пользователя</label>
        <div class="input-group has-validation">
            <span class="input-group-text" id="inputGroupPrepend">@</span>
            <input type="text" class="form-control" id="validationCustomUsername" aria-describedby="inputGroupPrepend" required>
            <div class="invalid-feedback">
                Пожалуйста, выберите имя пользователя.
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <label for="validationCustom03" class="form-label">Город</label>
        <input type="text" class="form-control" id="validationCustom03" required>
        <div class="invalid-feedback">
            Укажите действующий город.
        </div>
    </div>
    <div class="col-md-3">
        <label for="validationCustom04" class="form-label">Область</label>
        <select class="form-select" id="validationCustom04" required>
            <option selected disabled value="">Выберите...</option>
            <option>...</option>
        </select>
        <div class="invalid-feedback">
            Пожалуйста, выберите корректный город.
        </div>
    </div>
    <div class="col-md-3">
        <label for="validationCustom05" class="form-label">Индекс</label>
        <input type="text" class="form-control" id="validationCustom05" required>
        <div class="invalid-feedback">
            Пожалуйста, предоставьте действующий почтовый индекс.
        </div>
    </div>
    <div class="col-12">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="invalidCheck" required>
            <label class="form-check-label" for="invalidCheck">
                Примите условия и соглашения
            </label>
            <div class="invalid-feedback">
                Вы должны принять перед отправкой.
            </div>
        </div>
    </div>
    <div class="col-12">
        <button class="btn btn-primary" type="submit">Отправить форму</button>
    </div>
</form>
<script>
// Пример стартового JavaScript для отключения отправки форм при наличии недопустимых полей
(function () {
'use strict'

// Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
var forms = document.querySelectorAll('.needs-validation')

// Зацикливайтесь на них и предотвращайте отправку
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
</body>
</html>

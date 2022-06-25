<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 08.06.2022
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<table class="table table-responsive table-bordered" style="margin-bottom: 0px">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">${photo_label}</th>
        <th scope="col">${product_name_label}</th>
        <th scope="col">${price_label}</th>
        <th scope="col">${quantity_label}</th>
        <th scope="col">${sum_label}</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="count" value="0"/>
    <c:forEach var="productQuantity" items="${sess_cart.productQuantity}">
        <c:set var="count" value="${count + 1}"/>
        <c:set var="currentProductSumm"
               value="${productQuantity.key.price * productQuantity.value}"/>
        <c:set var="totalSum" value="${totalSum + currentProductSumm}"/>
        <c:set var="total_quantity" value="${total_quantity + productQuantity.value}"/>
        <tr
        >
            <th scope="row">${count}</th>

            <td scope="image" class="col-3"
                style="background-color: white; height: 50px">
                <div class="card border-0 d-block">
                    <c:if test="${empty productQuantity.key.photoString}">
                        <img src="${path}/images/nophoto.jpg"
                             class="img-fluid mx-auto d-block"
                             alt="img"
                        >
                    </c:if>
                    <c:if test="${!empty productQuantity.key.photoString}">
                        <img src="data:image/jpeg;base64,${productQuantity.key.photoString}"
                             alt="img"
                             class="img-fluid mx-auto"
                             style="max-height: 50px">
                    </c:if>
                    <a href="javascript:void(0);"
                       title="Посмотреть продукт!!!"
                       class="stretched-link"
                       onClick=window.open("${path}/controller?command=show_product&id=${productQuantity.key.id}","Product","width=1400,height=650,left=300,toolbar=no,status=no,resizable=no,location=no,directories=no");></a>
                </div>
            </td>

            <td>${productQuantity.key.productName}</td>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${productQuantity.key.price}"/></td>
            <td>${productQuantity.value}</td>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${currentProductSumm}"/></td>
        </tr>
    </c:forEach>
    <tr class="border-2 border-dark">
    <tr>
        <th scope="col">${total_label}</th>
        <th class="border-0" scope="col"></th>
        <th class="border-0" scope="col"></th>
        <th class="border-0" scope="col"></th>
        <th scope="col">${total_quantity}</th>
        <th scope="col"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${totalSum}"/></th>
    </tr>

    <c:if test="${!empty user}">
        <tr
                <c:if test="${user.amount<totalSum}">
                    class="table-danger"
                </c:if>
        >
            <th scope="col">${user_balance}</th>
            <th class="border-0" scope="col"></th>
            <th class="border-0" scope="col"></th>
            <th class="border-0" scope="col"></th>
            <th class="border-0" scope="col"></th>
            <th scope="col"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${user.amount}"/></th>
        </tr>
    </c:if>

    <tr class="border-2 border-dark">
    </tbody>
</table>

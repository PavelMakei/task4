<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 21.06.2022
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ft" uri="/WEB-INF/tld/footertaglib.tld" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="language_text"/>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:message key="about.message" var="about_message"/>


<head>

    <meta charset="UTF-8"/>
    <title>Light Bulb On/Off With Sound</title>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/css/enter.css" rel="stylesheet">
    <link href="${path}/css/about.css" rel="stylesheet">


</head>
<body>

<div class="wrapper" id="wrapper" style="background: rgba(0,0,0,100)">
    <div class="header" id="header" style="background: rgba(0,0,0,100)">
        <%@include file="header.jsp" %>
    </div> <!-- end of header -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center" id="box" style="opacity: 0">
                <%--                <div class="row align-items-center">--%>
                <%--                    <div class="col" style=" position: fixed; top: 25%; left: 25%">--%>

                <div class="sign" style="position: fixed; top: 33%; left: 37%">
                    <%--                            <span class="fast-flicker">b</span>rea<span class="flicker">t</span>he--%>
                    <span class="fast-flicker">${about_message.substring(0,1)}</span>${about_message.substring(1,5)}<span
                        class="flicker">${about_message.substring(5,6)}</span>${about_message.substring(6)}
                </div>


                <%--                        <h1 class="text-about" id="text-about"> ${about_message} </h1>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
            </div>


        </div>
    </div>
    <%--        </div>--%>
    <div class="footer" id="footer" style="color: white; background: rgba(0,0,0,100)"><ft:footerTag/></div>

</div>


<script>
    let hdr = document.getElementById('header');
    let wrpp = document.getElementById('wrapper');
    let ftr = document.getElementById('footer');
    let box = document.getElementById('box');
    let colr = "rgba(0,0,0,.99)"

    let i = 100, counter = 0;

    function changeVisibility() {
        i--;
        colr = "rgba(0,0,0," + i * .01 + ")"
        hdr.style.background = colr;
        wrpp.style.background = colr;
        ftr.style.background = colr;

        if (i >= counter) {
            setTimeout(changeVisibility, 30);
        }
    }
    window.onload = changeVisibility;

   let j  = 0;

    function boxOpacity() {
        j++;
        box.style.opacity = j * .01;
        if (j < 100) {
            setTimeout(boxOpacity, 10);
        }
    }

    window.onunload = setTimeout(boxOpacity, 3500)
</script>

<%--<script>--%>
<%--    let mess = "${about_message}";--%>
<%--    let message_size = mess.length;--%>
<%--    let step = 3000/message_size;--%>
<%--    console.log('string length ='+message_size+' step ='+step);--%>
<%--    let substr = "";--%>

<%--    let k = 0, count = message_size+1, offset = 0;--%>

<%--    function changeVisibilityMess() {--%>
<%--        k++;--%>
<%--        substr = mess.substring(0,offset);--%>
<%--        ta.innerHTML = substr--%>
<%--        offset++;--%>
<%--        console.log('mess ='+substr+' offset ='+ offset);--%>
<%--        if (k < count) {--%>
<%--            setTimeout(changeVisibilityMess, step);--%>
<%--        }--%>
<%--    }--%>
<%--    window.onload = changeVisibilityMess;--%>
<%--</script>--%>


</body>
</html>

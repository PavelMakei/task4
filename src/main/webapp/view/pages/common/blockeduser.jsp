<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 17.06.2022
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../parts/common.jsp" %>

<fmt:message key="blocked.account.label" var="blocked_account_label"/>
<fmt:message key="blocked.account.message" var="blocked_account_message"/>
<fmt:message key="our.contact.phone" var="our_contact_phone"/>

<head>

    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="${path}/icons/favicon.ico" type="image/x-icon"/>
    <link href="${path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/css/enter.css" rel="stylesheet">

    <title>${blocked_account_label}</title>

</head>
<body>
<div class="wrapper">
    <div class="header">
        <%@include file="header.jsp" %>
    </div> <!-- end of header -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7">
                    <div class="card" style="border-color: goldenrod">
                        <div class="card-header bg-light"
                             style="text-align:center; color: black; font-size: large"></div>
                        <div class="card-body bg-dark bg-opacity-75">
                            <div class="alert alert-danger" role="alert">
                                <h4 class="alert-heading">${blocked_account_label}</h4>
                                <p>${blocked_account_message}</p>
                                <hr>
                                <p class="mb-0">${our_contact_phone}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer" style="color: white"><ft:footerTag/></div>
</div>

</body>

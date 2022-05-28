<%--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 09.04.2022
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<fmt:setLocale value="${locale}" scope="session"/>--%>
<%--<fmt:setBundle basename="language_text"/>--%>

<!DOCTYPE html>
<html>
<head>
    <title>Регистрация успешна</title>
</head>
<center>
    <body>
    <H2> Регистрация успешна</H2>
    ${user.firstName}

    <div class="sgpb-popup-dialog-main-div-theme-wrapper-1 sg-animated sgpb-slideInUp"
         style="z-index: 999910; position: fixed; left: 553px; top: 137px; animation-timing-function: linear; animation-duration: 500ms;">
        <img class="sgpb-popup-close-button-1"
             style="z-index: 999920; position: absolute; float: left; right: 9px; bottom: 9px; width: 21px; cursor: pointer; height: 21px; background-repeat: no-repeat; background-size: cover;"
             alt="Close" src="https://codippa.com/wp-content/plugins/popup-builder/public/img/theme_1/close.png">
        <div id="sgpb-popup-dialog-main-div"
             style="box-sizing: content-box; min-width: 120px; max-height: 462px; max-width: 1844px; border-style: solid; border-color: rgb(255, 0, 0); border-width: 0px; padding: 7px; width: 800.483px; background-repeat: no-repeat; background-position: center center; background-color: rgb(255, 255, 255); box-shadow: rgb(204, 204, 204) 0px 0px 0px 14px; overflow: auto;"
             class="sgpb-content sgpb-content-3610 sgpb-theme-1-content sg-popup-content">
            <div style="height:100%;width:100%;overflow:auto;">
                <div class="sg-popup-builder-content" id="sg-popup-content-wrapper-3610" data-id="3610"
                     data-events="[{&quot;param&quot;:&quot;load&quot;,&quot;value&quot;:&quot;5&quot;,&quot;hiddenOption&quot;:[]}]"
                     data-options="YTo4Njp7czoyMzoic2dwYi1zdWJzLWZvcm0tYmctY29sb3IiO3M6NzoiI2YyZjJmMiI7czoyNToic2dwYi1zdWJzLWZvcm0tYmctb3BhY2l0eSI7czoxOiIxIjtzOjIyOiJzZ3BiLXN1YnMtZm9ybS1wYWRkaW5nIjtzOjI6IjIyIjtzOjI3OiJzZ3BiLXN1YnMtZW1haWwtcGxhY2Vob2xkZXIiO3M6MTY6IkVudGVyIHlvdXIgZW1haWwiO3M6MTk6InNncGItc3Vicy1nZHByLXRleHQiO3M6MTE5OiJjb2RpcHBhIHdpbGwgdXNlIHRoZSBpbmZvcm1hdGlvbiB5b3UgcHJvdmlkZSBvbiB0aGlzIGZvcm0gdG8gYmUgaW4gdG91Y2ggd2l0aCB5b3UgYW5kIHRvIHByb3ZpZGUgdXBkYXRlcyBhbmQgbWFya2V0aW5nLiI7czoyODoic2dwYi1zdWJzLXZhbGlkYXRpb24tbWVzc2FnZSI7czoyMzoiVGhpcyBmaWVsZCBpcyByZXF1aXJlZC4iO3M6MjA6InNncGItc3Vicy10ZXh0LXdpZHRoIjtzOjU6IjUwMHB4IjtzOjIxOiJzZ3BiLXN1YnMtdGV4dC1oZWlnaHQiO3M6NDoiNDBweCI7czoyNzoic2dwYi1zdWJzLXRleHQtYm9yZGVyLXdpZHRoIjtzOjM6IjJweCI7czoyMzoic2dwYi1zdWJzLXRleHQtYmctY29sb3IiO3M6NzoiI2ZmZmZmZiI7czoyNzoic2dwYi1zdWJzLXRleHQtYm9yZGVyLWNvbG9yIjtzOjc6IiNDQ0NDQ0MiO3M6MjA6InNncGItc3Vicy10ZXh0LWNvbG9yIjtzOjc6IiMzMjU5MDAiO3M6MzI6InNncGItc3Vicy10ZXh0LXBsYWNlaG9sZGVyLWNvbG9yIjtzOjc6IiNhYWE2OGYiO3M6MTk6InNncGItc3Vicy1idG4td2lkdGgiO3M6NToiMzAwcHgiO3M6MjA6InNncGItc3Vicy1idG4taGVpZ2h0IjtzOjQ6IjQwcHgiO3M6MjY6InNncGItc3Vicy1idG4tYm9yZGVyLXdpZHRoIjtzOjM6IjBweCI7czoyNzoic2dwYi1zdWJzLWJ0bi1ib3JkZXItcmFkaXVzIjtzOjM6IjRweCI7czoyNjoic2dwYi1zdWJzLWJ0bi1ib3JkZXItY29sb3IiO3M6NzoiIzRDQUY1MCI7czoxOToic2dwYi1zdWJzLWJ0bi10aXRsZSI7czo5OiJTdWJzY3JpYmUiO3M6Mjg6InNncGItc3Vicy1idG4tcHJvZ3Jlc3MtdGl0bGUiO3M6MTQ6IlBsZWFzZSB3YWl0Li4uIjtzOjIyOiJzZ3BiLXN1YnMtYnRuLWJnLWNvbG9yIjtzOjc6IiMwMTUzODkiO3M6MjQ6InNncGItc3Vicy1idG4tdGV4dC1jb2xvciI7czo3OiIjRkZGRkZGIjtzOjIzOiJzZ3BiLXN1YnMtZXJyb3ItbWVzc2FnZSI7czo3MToiVGhlcmUgd2FzIGFuIGVycm9yIHdoaWxlIHRyeWluZyB0byBzZW5kIHlvdXIgcmVxdWVzdC4gUGxlYXNlIHRyeSBhZ2Fpbi4iO3M6MjU6InNncGItc3Vicy1pbnZhbGlkLW1lc3NhZ2UiO3M6MzU6IlBsZWFzZSBlbnRlciBhIHZhbGlkIGVtYWlsIGFkZHJlc3MuIjtzOjI1OiJzZ3BiLXN1YnMtaGlkZS1zdWJzLXVzZXJzIjtzOjI6Im9uIjtzOjI2OiJzZ3BiLXN1YnMtc3VjY2Vzcy1iZWhhdmlvciI7czo5OiJoaWRlUG9wdXAiO3M6MjU6InNncGItc3Vicy1zdWNjZXNzLW1lc3NhZ2UiO3M6NTA6IllvdSBoYXZlIHN1Y2Nlc3NmdWxseSBzdWJzY3JpYmVkIHRvIHRoZSBuZXdzbGV0dGVyIjtzOjMwOiJzZ3BiLXN1YnMtc3VjY2Vzcy1yZWRpcmVjdC1VUkwiO3M6MDoiIjtzOjIzOiJzZ3BiLXN1YnMtc3VjY2Vzcy1wb3B1cCI7czo0OiI1MjAyIjtzOjE1OiJzZ3BiLXN1YnMtZW1haWwiO3M6MDoiIjtzOjIwOiJzZ3BiLXN1YnMtZmlyc3QtbmFtZSI7czowOiIiO3M6MTk6InNncGItc3Vicy1sYXN0LW5hbWUiO3M6MDoiIjtzOjI0OiJzZ3BiLXN1YnMtaGlkZGVuLWNoZWNrZXIiO3M6MDoiIjtzOjk6InNncGItdHlwZSI7czoxMjoic3Vic2NyaXB0aW9uIjtzOjE1OiJzZ3BiLWlzLXByZXZpZXciO3M6MToiMCI7czoxNDoic2dwYi1pcy1hY3RpdmUiO3M6Mjoib24iO3M6MzQ6InNncGItYmVoYXZpb3ItYWZ0ZXItc3BlY2lhbC1ldmVudHMiO2E6MTp7aTowO2E6MTp7aTowO2E6MTp7czo1OiJwYXJhbSI7czoxMjoic2VsZWN0X2V2ZW50Ijt9fX1zOjIwOiJzZ3BiLWNvbnRlbnQtcGFkZGluZyI7czoxOiI3IjtzOjE4OiJzZ3BiLXBvcHVwLXotaW5kZXgiO3M6NDoiOTk5OSI7czoxNzoic2dwYi1wb3B1cC10aGVtZXMiO3M6MTI6InNncGItdGhlbWUtMSI7czoxODoic2dwYi1vdmVybGF5LWNvbG9yIjtzOjA6IiI7czoyMDoic2dwYi1vdmVybGF5LW9wYWNpdHkiO3M6MzoiMC44IjtzOjI1OiJzZ3BiLWNvbnRlbnQtY3VzdG9tLWNsYXNzIjtzOjE2OiJzZy1wb3B1cC1jb250ZW50IjtzOjI2OiJzZ3BiLWJhY2tncm91bmQtaW1hZ2UtbW9kZSI7czo5OiJuby1yZXBlYXQiO3M6MjQ6InNncGItZW5hYmxlLWNsb3NlLWJ1dHRvbiI7czoyOiJvbiI7czoyMzoic2dwYi1jbG9zZS1idXR0b24tZGVsYXkiO3M6MToiMCI7czoyNjoic2dwYi1jbG9zZS1idXR0b24tcG9zaXRpb24iO3M6MTE6ImJvdHRvbVJpZ2h0IjtzOjI0OiJzZ3BiLWJ1dHRvbi1wb3NpdGlvbi10b3AiO3M6MDoiIjtzOjI2OiJzZ3BiLWJ1dHRvbi1wb3NpdGlvbi1yaWdodCI7czoxOiI5IjtzOjI3OiJzZ3BiLWJ1dHRvbi1wb3NpdGlvbi1ib3R0b20iO3M6MToiOSI7czoyNToic2dwYi1idXR0b24tcG9zaXRpb24tbGVmdCI7czowOiIiO3M6MTc6InNncGItYnV0dG9uLWltYWdlIjtzOjA6IiI7czoyMzoic2dwYi1idXR0b24taW1hZ2Utd2lkdGgiO3M6MjoiMjEiO3M6MjQ6InNncGItYnV0dG9uLWltYWdlLWhlaWdodCI7czoyOiIyMSI7czoxNzoic2dwYi1ib3JkZXItY29sb3IiO3M6NzoiIzAwMDAwMCI7czoxODoic2dwYi1ib3JkZXItcmFkaXVzIjtzOjE6IjAiO3M6MjM6InNncGItYm9yZGVyLXJhZGl1cy10eXBlIjtzOjE6IiUiO3M6MTY6InNncGItYnV0dG9uLXRleHQiO3M6NToiQ2xvc2UiO3M6MjU6InNncGItcG9wdXAtZGltZW5zaW9uLW1vZGUiO3M6MTQ6InJlc3BvbnNpdmVNb2RlIjtzOjMzOiJzZ3BiLXJlc3BvbnNpdmUtZGltZW5zaW9uLW1lYXN1cmUiO3M6NDoiYXV0byI7czoxMDoic2dwYi13aWR0aCI7czo1OiI2NDBweCI7czoxMToic2dwYi1oZWlnaHQiO3M6NToiNDgwcHgiO3M6MTQ6InNncGItbWF4LXdpZHRoIjtzOjA6IiI7czoxNToic2dwYi1tYXgtaGVpZ2h0IjtzOjA6IiI7czoxNDoic2dwYi1taW4td2lkdGgiO3M6MzoiMTIwIjtzOjE1OiJzZ3BiLW1pbi1oZWlnaHQiO3M6MDoiIjtzOjI1OiJzZ3BiLXNob3ctcG9wdXAtc2FtZS11c2VyIjtzOjI6Im9uIjtzOjMxOiJzZ3BiLXNob3ctcG9wdXAtc2FtZS11c2VyLWNvdW50IjtzOjE6IjEiO3M6MzI6InNncGItc2hvdy1wb3B1cC1zYW1lLXVzZXItZXhwaXJ5IjtzOjE6IjEiO3M6MTk6InNncGItb3Blbi1hbmltYXRpb24iO3M6Mjoib24iO3M6MjY6InNncGItb3Blbi1hbmltYXRpb24tZWZmZWN0IjtzOjE0OiJzZ3BiLXNsaWRlSW5VcCI7czoyNToic2dwYi1vcGVuLWFuaW1hdGlvbi1zcGVlZCI7czozOiIwLjUiO3M6MjA6InNncGItY2xvc2UtYW5pbWF0aW9uIjtzOjI6Im9uIjtzOjI3OiJzZ3BiLWNsb3NlLWFuaW1hdGlvbi1lZmZlY3QiO3M6MTU6InNncGItc2xpZGVPdXRVcCI7czoyNjoic2dwYi1jbG9zZS1hbmltYXRpb24tc3BlZWQiO3M6MzoiMC41IjtzOjE2OiJzZ3BiLXBvcHVwLWZpeGVkIjtzOjI6Im9uIjtzOjI1OiJzZ3BiLXBvcHVwLWZpeGVkLXBvc2l0aW9uIjtzOjE6IjUiO3M6Mjk6InNncGItZW5hYmxlLWNvbnRlbnQtc2Nyb2xsaW5nIjtzOjI6Im9uIjtzOjE2OiJzZ3BiLXBvcHVwLW9yZGVyIjtzOjE6IjAiO3M6MTY6InNncGItcG9wdXAtZGVsYXkiO3M6MToiMCI7czoxMjoic2dwYi1wb3N0LWlkIjtzOjQ6IjM2MTAiO3M6MTY6InNncGItc3Vicy1maWVsZHMiO2E6Njp7czo1OiJlbWFpbCI7YTo0OntzOjY6ImlzU2hvdyI7czoxOiIxIjtzOjU6ImF0dHJzIjthOjY6e3M6NDoidHlwZSI7czo1OiJlbWFpbCI7czoxMzoiZGF0YS1yZXF1aXJlZCI7czoxOiIxIjtzOjQ6Im5hbWUiO3M6MTU6InNncGItc3Vicy1lbWFpbCI7czoxMToicGxhY2Vob2xkZXIiO3M6MTY6IkVudGVyIHlvdXIgZW1haWwiO3M6NToiY2xhc3MiO3M6Mzk6ImpzLXN1YnMtdGV4dC1pbnB1dHMganMtc3Vicy1lbWFpbC1pbnB1dCI7czoyNDoiZGF0YS1lcnJvci1tZXNzYWdlLWNsYXNzIjtzOjI5OiJzZ3BiLXN1YnMtZW1haWwtZXJyb3ItbWVzc2FnZSI7fXM6NToic3R5bGUiO2E6Nzp7czo1OiJ3aWR0aCI7czo1OiI1MDBweCI7czo2OiJoZWlnaHQiO3M6NDoiNDBweCI7czoxMjoiYm9yZGVyLXdpZHRoIjtzOjM6IjJweCI7czoxMjoiYm9yZGVyLWNvbG9yIjtzOjc6IiNDQ0NDQ0MiO3M6MTY6ImJhY2tncm91bmQtY29sb3IiO3M6NzoiI2ZmZmZmZiI7czo1OiJjb2xvciI7czo3OiIjMzI1OTAwIjtzOjEyOiJhdXRvY29tcGxldGUiO3M6Mzoib2ZmIjt9czoyMToiZXJyb3JNZXNzYWdlQm94U3R5bGVzIjtzOjU6IjUwMHB4Ijt9czoxMDoiZmlyc3QtbmFtZSI7YTo0OntzOjY6ImlzU2hvdyI7czowOiIiO3M6NToiYXR0cnMiO2E6Njp7czo0OiJ0eXBlIjtzOjQ6InRleHQiO3M6MTM6ImRhdGEtcmVxdWlyZWQiO3M6MDoiIjtzOjQ6Im5hbWUiO3M6MjA6InNncGItc3Vicy1maXJzdC1uYW1lIjtzOjExOiJwbGFjZWhvbGRlciI7czowOiIiO3M6NToiY2xhc3MiO3M6NDQ6ImpzLXN1YnMtdGV4dC1pbnB1dHMganMtc3Vicy1maXJzdC1uYW1lLWlucHV0IjtzOjI0OiJkYXRhLWVycm9yLW1lc3NhZ2UtY2xhc3MiO3M6MzQ6InNncGItc3Vicy1maXJzdC1uYW1lLWVycm9yLW1lc3NhZ2UiO31zOjU6InN0eWxlIjthOjc6e3M6NToid2lkdGgiO3M6NToiNTAwcHgiO3M6NjoiaGVpZ2h0IjtzOjQ6IjQwcHgiO3M6MTI6ImJvcmRlci13aWR0aCI7czozOiIycHgiO3M6MTI6ImJvcmRlci1jb2xvciI7czo3OiIjQ0NDQ0NDIjtzOjE2OiJiYWNrZ3JvdW5kLWNvbG9yIjtzOjc6IiNmZmZmZmYiO3M6NToiY29sb3IiO3M6NzoiIzMyNTkwMCI7czoxMjoiYXV0b2NvbXBsZXRlIjtzOjM6Im9mZiI7fXM6MjE6ImVycm9yTWVzc2FnZUJveFN0eWxlcyI7czo1OiI1MDBweCI7fXM6OToibGFzdC1uYW1lIjthOjQ6e3M6NjoiaXNTaG93IjtzOjA6IiI7czo1OiJhdHRycyI7YTo2OntzOjQ6InR5cGUiO3M6NDoidGV4dCI7czoxMzoiZGF0YS1yZXF1aXJlZCI7czowOiIiO3M6NDoibmFtZSI7czoxOToic2dwYi1zdWJzLWxhc3QtbmFtZSI7czoxMToicGxhY2Vob2xkZXIiO3M6MDoiIjtzOjU6ImNsYXNzIjtzOjQzOiJqcy1zdWJzLXRleHQtaW5wdXRzIGpzLXN1YnMtbGFzdC1uYW1lLWlucHV0IjtzOjI0OiJkYXRhLWVycm9yLW1lc3NhZ2UtY2xhc3MiO3M6MzM6InNncGItc3Vicy1sYXN0LW5hbWUtZXJyb3ItbWVzc2FnZSI7fXM6NToic3R5bGUiO2E6Nzp7czo1OiJ3aWR0aCI7czo1OiI1MDBweCI7czo2OiJoZWlnaHQiO3M6NDoiNDBweCI7czoxMjoiYm9yZGVyLXdpZHRoIjtzOjM6IjJweCI7czoxMjoiYm9yZGVyLWNvbG9yIjtzOjc6IiNDQ0NDQ0MiO3M6MTY6ImJhY2tncm91bmQtY29sb3IiO3M6NzoiI2ZmZmZmZiI7czo1OiJjb2xvciI7czo3OiIjMzI1OTAwIjtzOjEyOiJhdXRvY29tcGxldGUiO3M6Mzoib2ZmIjt9czoyMToiZXJyb3JNZXNzYWdlQm94U3R5bGVzIjtzOjU6IjUwMHB4Ijt9czo0OiJnZHByIjthOjY6e3M6NjoiaXNTaG93IjtzOjA6IiI7czo1OiJhdHRycyI7YTo2OntzOjQ6InR5cGUiO3M6MTQ6ImN1c3RvbUNoZWNrYm94IjtzOjEzOiJkYXRhLXJlcXVpcmVkIjtzOjA6IiI7czo0OiJuYW1lIjtzOjE0OiJzZ3BiLXN1YnMtZ2RwciI7czo1OiJjbGFzcyI7czozODoianMtc3Vicy1nZHByLWlucHV0cyBqcy1zdWJzLWdkcHItbGFiZWwiO3M6MjoiaWQiO3M6MjE6InNncGItZ2Rwci1maWVsZC1sYWJlbCI7czoyNDoiZGF0YS1lcnJvci1tZXNzYWdlLWNsYXNzIjtzOjIzOiJzZ3BiLWdkcHItZXJyb3ItbWVzc2FnZSI7fXM6NToic3R5bGUiO2E6MTp7czo1OiJ3aWR0aCI7czo1OiI1MDBweCI7fXM6NToibGFiZWwiO3M6MTI6IkFjY2VwdCBUZXJtcyI7czo0OiJ0ZXh0IjtzOjExOToiY29kaXBwYSB3aWxsIHVzZSB0aGUgaW5mb3JtYXRpb24geW91IHByb3ZpZGUgb24gdGhpcyBmb3JtIHRvIGJlIGluIHRvdWNoIHdpdGggeW91IGFuZCB0byBwcm92aWRlIHVwZGF0ZXMgYW5kIG1hcmtldGluZy4iO3M6MjE6ImVycm9yTWVzc2FnZUJveFN0eWxlcyI7czo1OiI1MDBweCI7fXM6MTQ6ImhpZGRlbi1jaGVja2VyIjthOjM6e3M6NjoiaXNTaG93IjtzOjA6IiI7czo1OiJhdHRycyI7YTo1OntzOjQ6InR5cGUiO3M6NjoiaGlkZGVuIjtzOjEzOiJkYXRhLXJlcXVpcmVkIjtzOjA6IiI7czo0OiJuYW1lIjtzOjI0OiJzZ3BiLXN1YnMtaGlkZGVuLWNoZWNrZXIiO3M6NToidmFsdWUiO3M6MDoiIjtzOjU6ImNsYXNzIjtzOjQzOiJqcy1zdWJzLXRleHQtaW5wdXRzIGpzLXN1YnMtbGFzdC1uYW1lLWlucHV0Ijt9czo1OiJzdHlsZSI7YTozOntzOjg6InBvc2l0aW9uIjtzOjg6ImFic29sdXRlIjtzOjQ6ImxlZnQiO3M6NzoiLTUwMDBweCI7czo3OiJwYWRkaW5nIjtzOjE6IjAiO319czo2OiJzdWJtaXQiO2E6Mzp7czo2OiJpc1Nob3ciO3M6MToiMSI7czo1OiJhdHRycyI7YTo2OntzOjQ6InR5cGUiO3M6Njoic3VibWl0IjtzOjQ6Im5hbWUiO3M6MTY6InNncGItc3Vicy1zdWJtaXQiO3M6NToidmFsdWUiO3M6OToiU3Vic2NyaWJlIjtzOjEwOiJkYXRhLXRpdGxlIjtzOjk6IlN1YnNjcmliZSI7czoxOToiZGF0YS1wcm9ncmVzcy10aXRsZSI7czoxNDoiUGxlYXNlIHdhaXQuLi4iO3M6NToiY2xhc3MiO3M6MTg6ImpzLXN1YnMtc3VibWl0LWJ0biI7fXM6NToic3R5bGUiO2E6OTp7czo1OiJ3aWR0aCI7czo1OiIzMDBweCI7czo2OiJoZWlnaHQiO3M6NDoiNDBweCI7czoxNjoiYmFja2dyb3VuZC1jb2xvciI7czoxODoiIzAxNTM4OSAhaW1wb3J0YW50IjtzOjU6ImNvbG9yIjtzOjc6IiNGRkZGRkYiO3M6MTM6ImJvcmRlci1yYWRpdXMiO3M6MTQ6IjRweCAhaW1wb3J0YW50IjtzOjEyOiJib3JkZXItd2lkdGgiO3M6MTQ6IjBweCAhaW1wb3J0YW50IjtzOjEyOiJib3JkZXItY29sb3IiO3M6MTg6IiM0Q0FGNTAgIWltcG9ydGFudCI7czoxNDoidGV4dC10cmFuc2Zvcm0iO3M6MTU6Im5vbmUgIWltcG9ydGFudCI7czoxMjoiYm9yZGVyLXN0eWxlIjtzOjU6InNvbGlkIjt9fX1zOjI1OiJzZ3BiLWVuYWJsZS1wb3B1cC1vdmVybGF5IjtzOjI6Im9uIjtzOjIyOiJzZ3BiLWJ1dHRvbi1pbWFnZS1kYXRhIjtzOjA6IiI7czoyNjoic2dwYi1iYWNrZ3JvdW5kLWltYWdlLWRhdGEiO3M6MDoiIjtzOjE0OiJzZ3BiQ29uZGl0aW9ucyI7Tjt9">
                    <div class="sgpb-popup-builder-content-3610 sgpb-popup-builder-content-html"><p
                            style="text-align: center;"><strong><span style="color: #008080; font-size: 18pt;"><span
                            style="font-family: tahoma, arial, helvetica, sans-serif;">Never Miss an article !</span><br> </span><br>
                        <span style="font-size: 14pt; font-family: 'trebuchet ms', geneva, sans-serif;">Get the new post delivered straight into your inbox, enter your email and hit the button</span></strong>
                    </p>
                        <div class="sgpb-subs-form-3610 sgpb-subscription-form">
                            <div class="subs-form-messages sgpb-alert sgpb-alert-success sg-hide-element"><p>You have
                                successfully subscribed to the newsletter</p></div>
                            <div class="subs-form-messages sgpb-alert sgpb-alert-danger sg-hide-element"><p>There was an
                                error while trying to send your request. Please try again.</p></div>
                            <form class="sgpb-form" id="sgpb-form" method="post" novalidate="novalidate">
                                <div class="sgpb-form-wrapper">
                                    <div class="sgpb-inputs-wrapper js-email-wrapper js-sgpb-form-field-email-wrapper ">
                                        <input type="email" data-required="1" name="sgpb-subs-email"
                                               placeholder="Enter your email"
                                               class="js-subs-text-inputs js-subs-email-input"
                                               data-error-message-class="sgpb-subs-email-error-message"
                                               style="width:500px; height:40px; border-width:2px; border-color:#CCCCCC; background-color:#ffffff; color:#325900; autocomplete:off; ">
                                        <div class="sgpb-subs-email-error-message"></div>
                                    </div>
                                    <div class="sgpb-inputs-wrapper js-first-name-wrapper js-sgpb-form-field-first-name-wrapper sg-js-hide">
                                        <input type="text" data-required="" name="sgpb-subs-first-name" placeholder=""
                                               class="js-subs-text-inputs js-subs-first-name-input"
                                               data-error-message-class="sgpb-subs-first-name-error-message"
                                               style="width:500px; height:40px; border-width:2px; border-color:#CCCCCC; background-color:#ffffff; color:#325900; autocomplete:off; ">
                                        <div class="sgpb-subs-first-name-error-message"></div>
                                    </div>
                                    <div class="sgpb-inputs-wrapper js-last-name-wrapper js-sgpb-form-field-last-name-wrapper sg-js-hide">
                                        <input type="text" data-required="" name="sgpb-subs-last-name" placeholder=""
                                               class="js-subs-text-inputs js-subs-last-name-input"
                                               data-error-message-class="sgpb-subs-last-name-error-message"
                                               style="width:500px; height:40px; border-width:2px; border-color:#CCCCCC; background-color:#ffffff; color:#325900; autocomplete:off; ">
                                        <div class="sgpb-subs-last-name-error-message"></div>
                                    </div>
                                    <div class="sgpb-inputs-wrapper js-gdpr-wrapper js-sgpb-form-field-gdpr-wrapper sg-js-hide">
                                        <div class="sgpb-gdpr-label-wrapper" style="width:500px; "><input
                                                type="checkbox" data-required="" name="sgpb-subs-gdpr"
                                                class="js-subs-gdpr-inputs js-subs-gdpr-label"
                                                id="sgpb-gdpr-field-label"
                                                data-error-message-class="sgpb-gdpr-error-message"><label
                                                class="js-login-remember-me-label-edit" for="sgpb-gdpr-field-label">Accept
                                            Terms</label>
                                            <div class="sgpb-gdpr-error-message"></div>
                                        </div>
                                        <div class="sgpb-alert-info sgpb-alert sgpb-gdpr-info js-subs-text-checkbox sgpb-gdpr-text-js"
                                             style="width:500px; ">codippa will use the information you provide
                                            on this form to be in touch with you and to provide updates and marketing.
                                        </div>
                                    </div>
                                    <div class="sgpb-inputs-wrapper js-hidden-checker-wrapper js-sgpb-form-field-hidden-checker-wrapper sg-js-hide">
                                        <input type="hidden" data-required="" name="sgpb-subs-hidden-checker" value=""
                                               class="js-subs-text-inputs js-subs-last-name-input"
                                               style="position:absolute; left:-5000px; padding:0; ">
                                        <div class="sgpb-subs-hidden-checker-error-message"></div>
                                    </div>
                                    <div class="sgpb-inputs-wrapper js-submit-wrapper js-sgpb-form-field-submit-wrapper ">
                                        <input type="submit" name="sgpb-subs-submit" value="Subscribe"
                                               data-title="Subscribe" data-progress-title="Please wait..."
                                               class="js-subs-submit-btn"
                                               style="width:300px; height:40px; background-color:#015389 !important; color:#FFFFFF; border-radius:4px !important; border-width:0px !important; border-color:#4CAF50 !important; text-transform:none !important; border-style:solid; ">
                                        <div class="sgpb-subs-submit-error-message"></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <script type="text/javascript">var sgpbSubsValidateObj3610 = {
                            rules: {"sgpb-subs-email": {required: true, email: true}}, messages: {
                                "sgpb-subs-email": {
                                    "required": "This field is required.",
                                    "email": "Please enter a valid email address."
                                }
                            }
                        };</script>
                    </div>
                </div>
            </div>
        </div>
    </div>


    </body>
</center>

</html>

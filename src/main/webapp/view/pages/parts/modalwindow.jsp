<!--
  Created by IntelliJ IDEA.
  User: Pavel_Makei
  Date: 04.06.2022
  Time: 12:03
  To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- -Modal window-->

<div class="modal" id="mainModal">
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title text-muted align-content-center">Lighting shop</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body" style="text-align: center;">
            <p><strong><fmt:message key="${message}"/></strong></p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Ok</button>
        </div>
    </div>
</div>
</div>

<!--end modal window-->



<c:if test="${!empty message}">

<script>
    let mainModal = new bootstrap.Modal(document.getElementById('mainModal'), {});
    mainModal.toggle();
    // autoclose
    mainModalTimeout = setTimeout(function(){mainModal.hide()},3000);
</script>
</c:if>


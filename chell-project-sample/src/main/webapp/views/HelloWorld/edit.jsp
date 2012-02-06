<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />
<portlet:actionURL name="savePreferences" var="saveUrl" />

<c:if test="${!empty fwkMessageArea}">${fwkMessageArea}</c:if>
<c:if test="${!empty pf.errors}">${pf.errors}</c:if>

<form action="${saveUrl}" method="post">
    ${pf.html}
</form>

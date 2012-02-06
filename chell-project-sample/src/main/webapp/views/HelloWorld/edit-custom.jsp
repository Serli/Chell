<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />
<portlet:actionURL name="savePreferences" var="saveUrl" />

<c:if test="${!empty fwkMessageArea}">${fwkMessageArea}</c:if>

<h1>Edit preferences (custom view)</h1>

<form action="${saveUrl}" method="post">
    <table>
        <tr>
            <td>${pf.label['welcomePrefix']}</td>
            <td>${pf.input['welcomePrefix']}</td>
            <td>${pf.error['welcomePrefix']}</td>
        </tr>
        <tr>
            <td>${pf.label['defaultName']}</td>
            <td>${pf.input['defaultName']}</td>
            <td>${pf.error['defaultName']}</td>
        </tr>
        <tr>
            <td>${pf.label['upper']}</td>
            <td>${pf.input['upper']}</td>
            <td>${pf.error['upper']}</td>
        </tr>
        <tr>
            <td>${pf.label['animals']}</td>
            <td>${pf.input['animals']}</td>
            <td>${pf.error['animals']}</td>
        </tr>
        <tr>
            <td>${pf.label['age']}</td>
            <td>${pf.input['age']}</td>
            <td>${pf.error['age']}</td>
        </tr>
        <tr>
            <td>${pf.label['cities']}</td>
            <td>${pf.input['cities']}</td>
            <td>${pf.error['cities']}</td>
        </tr>
        <tr>
            <td>${pf.label['comment']}</td>
            <td>${pf.input['comment']}</td>
            <td>${pf.error['comment']}</td>
        </tr>
        <tr>
            <td>${pf.label['birthday']}</td>
            <td>${pf.input['birthday']}</td>
            <td>${pf.error['birthday']}</td>
        </tr>
    </table>
    <div>
        <span>${pf.button['cancel']}</span>
        <span>${pf.button['submit']}</span>
    </div>
</form>

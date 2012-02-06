<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="chl" uri="http://serli.com/taglibs/chell"%>

<h1>Edit preferences (custom taglib view)</h1>

<chl:message-area />

<chl:form form="pf" action="savePreferences">
    <table>
        <tr>
            <td><chl:label field="welcomePrefix"/></td>
            <td><chl:input field="welcomePrefix"/></td>
            <td><chl:error field="welcomePrefix"/></td>
        </tr>
        <tr>
            <td><chl:label field="defaultName"/></td>
            <td><chl:input field="defaultName"/></td>
            <td><chl:error field="defaultName"/></td>
        </tr>
        <tr>
            <td><chl:label field="upper"/></td>
            <td><chl:input field="upper"/></td>
            <td><chl:error field="upper"/></td>
        </tr>
        <tr>
            <td><chl:label field="animals"/></td>
            <td><chl:input field="animals"/></td>
            <td><chl:error field="animals"/></td>
        </tr>
        <tr>
            <td><chl:label field="age"/></td>
            <td><chl:input field="age"/></td>
            <td><chl:error field="age"/></td>
        </tr>
        <tr>
            <td><chl:label field="cities"/></td>
            <td><chl:input field="cities"/></td>
            <td><chl:error field="cities"/></td>
        </tr>
        <tr>
            <td><chl:label field="comment"/></td>
            <td><chl:input field="comment"/></td>
            <td><chl:error field="comment"/></td>
        </tr>
        <tr>
            <td><chl:label field="birthday"/></td>
            <td><chl:input field="birthday"/></td>
            <td><chl:error field="birthday"/></td>
        </tr>
    </table>
    <div>
        <span><chl:button type="cancel"/></span>
        <span><chl:button type="submit"/></span>
    </div>
</chl:form>

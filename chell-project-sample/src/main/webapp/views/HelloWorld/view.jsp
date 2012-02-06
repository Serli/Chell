<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="chl" uri="http://serli.com/taglibs/chell"%>

<portlet:defineObjects />

<h1>${welcomeMsg}</h1>
<h4>${animalsMsg}</h4>
<h4>${birthdayMsg}</h4>

<chl:form form="uf" action="submitUsername">
    <chl:fields />
</chl:form>

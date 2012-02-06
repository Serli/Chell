<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="chl" uri="http://serli.com/taglibs/chell"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />

<chl:message type="INFO" text="${cause}"/>
<div><img src="<c:url value="/images/computer.png" />" alt=""/></div>
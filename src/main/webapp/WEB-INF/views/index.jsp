<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Dashboard" />
<c:set var="pageName" value="dashboard" />
<c:set var="content" value="dashboard-content.jsp" />

<jsp:include page="layout.jsp" />

<!-- Dashboard Content -->
<jsp:include page="dashboard-content.jsp" />

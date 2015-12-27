<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<html>
<head>
<title><spring:message code="page.title" /></title>
<link href="${rootURL}resources/bootstrap/css/bootstrap.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${rootURL}resources/bootstrap/css/project.css" media="screen" rel="stylesheet" type="text/css" />
</head>

<body>
    <%@include file="head/navigator.jsp"%>
    <div class="container">
        <h2>
            <spring:message code="welcome.text" />
            <%=UserController.getCurrentUser().getName()%>
        </h2>
    </div>
    <script type="text/javascript" src="${rootURL}resources/jquery/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="${rootURL}resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>
<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<html>
<%@include file="head/head.jsp"%>
<body>
    <%@include file="head/navigator.jsp"%>
    <div class="container">
        <div class="container-page">
            <%@include file="templates/track/query.jsp"%>
            <%@include file="templates/track/result.jsp"%>
        </div>
        <%@include file="templates/track/popup.jsp"%>
    </div>
    <%@include file="head/bottom.jsp"%>
    <%@include file="templates/track/scripts.jsp"%>
</body>
</html>
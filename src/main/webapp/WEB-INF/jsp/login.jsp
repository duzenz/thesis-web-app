<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<html>
<%@include file="head/head.jsp"%>
<body>
    <div class="container">
        <div class="container-fluid">
            <section class="container-section">
                <div class="container-page">
                    <%@include file="templates/login/login_form.jsp"%>
                    <%@include file="templates/login/register_form.jsp"%>
                </div>
            </section>
        </div>
    </div>
    <%@include file="head/bottom.jsp"%>
    <%@include file="templates/login/scripts.jsp"%>
</body>
</html>
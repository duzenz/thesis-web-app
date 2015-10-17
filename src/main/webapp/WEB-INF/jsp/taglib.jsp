<%@page import="com.duzenz.recommender.web.controllers.UserController"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<c:url var="rootURL" value="/" />

<script type="text/javascript">
	window.session = {};
	window.session.user = <%=UserController.getCurrentUserJson() %>;
</script>

<!--  
 taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
page import="com.huawei.is.vtag.web.controllers.UserController"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="uri" value="${req.requestURI}" />
<c:url var="rootURL" value="/" />
-->


<nav class="navbar navbar-default recommender-navbar">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="welcome">
			    <spring:message code="navigator.home"/>
			</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${rootUrl}track"><spring:message code="navigator.recommendation"/></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${rootUrl}logout"><spring:message code="navigator.logout"/></a></li>
			</ul>
		</div>
	</div>
</nav>
<nav class="navbar navbar-default recommender-navbar">
    <div class="container-fluid">
        <div class="navbar-header">
            <sec:authorize access="hasRole('ROLE_USER')">
                <a class="navbar-brand" href="${rootUrl}welcome"> <spring:message code="navigator.home" />
                </a>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a class="navbar-brand" href="${rootUrl}admin"> <spring:message code="navigator.home" />
                </a>
            </sec:authorize>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <sec:authorize access="hasRole('ROLE_USER')">
                <ul class="nav navbar-nav">
                    <li><a href="${rootUrl}track"><spring:message code="navigator.recommendation" /></a></li>
                </ul>
                <ul class="nav navbar-nav">
                    <li><a href="${rootUrl}profile"><spring:message code="navigator.profile" /></a>
                </ul>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a class="navbar-brand" href="${rootUrl}adminprofile"> <spring:message code="navigator.profile" />
                </a>
            </sec:authorize>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${rootUrl}logout"><spring:message code="navigator.logout" /></a></li>
            </ul>
        </div>
    </div>
</nav>
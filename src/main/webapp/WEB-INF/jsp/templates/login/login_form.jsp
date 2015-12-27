<div class="col-md-6">
    <div>
        <h3 class="dark-grey">
            <spring:message code="login.title" />
        </h3>
        <!-- TODO add navigator bar -->

        <div class="account-wall">
            <form:form id="loginForm" method="post" action="${rootURL}login" modelAttribute="user" class="form-signin form-horizontal" role="form">
                <div class="form-group">
                    <label> <spring:message code="login.username" />
                    </label> <input type="text" id="username" name="username" class="form-control" placeholder="<spring:message code='login.username'/>" required autofocus />
                </div>
                <div class="form-group">
                    <label> <spring:message code="login.password" />
                    </label> <input type="password" id="password" name="password" class="form-control" placeholder="<spring:message code="login.password"/>" required />
                </div>
                <div class="form-group fright">
                    <button id="login_btn" class="btn btn-primary" type="submit">
                        <spring:message code="login.submit" />
                    </button>
                </div>
            </form:form>
        </div>

        <div class="login-error-container">
            <c:if test="${param.error != null}">
                <div class="alert alert-danger">
                    <spring:message code="login.fail"></spring:message>
                </div>
            </c:if>
            <c:if test="${param.logout != null}">
                <div class="alert alert-success">
                    <spring:message code="login.logout"></spring:message>
                </div>
            </c:if>
        </div>

    </div>
</div>
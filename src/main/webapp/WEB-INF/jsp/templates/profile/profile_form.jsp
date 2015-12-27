<div class="col-md-6">
    <h3 class="dark-grey">
        <spring:message code="profile.title" />
    </h3>
    <p>
        <spring:message code="profile.description" />
    </p>

    <h3 class="dark-grey">
        <spring:message code="profile.req" />
    </h3>

    <form:form class="form-signin form-horizontal" role="form">

        <div class="form-group">
            <label><spring:message code="profile.current_password" /></label> <input type="password" name="profile_current_password" class="form-control" id="profile_current_password" value="" placeholder="<spring:message code='profile.current_password'/>" required>
        </div>

        <div class="form-group">
            <label><spring:message code="profile.new_password" /></label> <input type="password" name="profile_new_password" class="form-control" id="profile_new_password" value="" placeholder="<spring:message code='profile.new_password'/>" required>
        </div>

        <div class="form-group">
            <label><spring:message code="profile.repeat_password" /></label> <input type="password" name="profile_repeat_password" class="form-control" id="profile_repeat_password" value="" placeholder="<spring:message code='profile.repeat_password'/>" required>
        </div>

        <div class="fleft" style="margin-left: 60px">
            <button class="btn btn-primary" type="button" id="profile_clear_btn">
                <spring:message code="profile.clear_btn" />
            </button>
        </div>

        <div class="fright" style="margin-right: -15px">
            <button class="btn btn-primary" type="button" id="profile_submit_btn">
                <spring:message code="profile.submit" />
            </button>
        </div>
    </form:form>
</div>
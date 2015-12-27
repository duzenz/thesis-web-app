<div class="col-md-3">

    <div class="header">
        <h3 class="dark-grey">
            <spring:message code="recommend.title" />
        </h3>
    </div>

    <div class="query-container">
        <div style="border: 2px solid #E6E6E6; margin-top: 25px; padding-left: 10px; margin-bottom: 10px;">
            <div>
                <label class='query-container-label'><spring:message code="query.cftype" />: </label><span id="query_cftype"></span>
            </div>
            <div>
                <label class='query-container-label'><spring:message code="query.age" />: </label><span id="query_age"></span>
            </div>
            <div>
                <label class='query-container-label'><spring:message code="query.country" />: </label><span id="query_country"></span>
            </div>
            <div>
                <label class='query-container-label'><spring:message code="query.gender" />: </label><span id="query_gender"></span>
            </div>
            <div>
                <label class='query-container-label'><spring:message code="query.register" />: </label><span id="query_register"></span>
            </div>
            <div>
                <label class='query-container-label'><spring:message code="query.tag" />: </label><span id="query_tag"></span>
            </div>
        </div>

        <div class="form-group">
            <label style="display: block"><spring:message code="recommend.tag" /></label> <select id="recommend_tag" name="recommend_tag" style="width: 100%" multiple="multiple">
            </select>
        </div>

        <div class="form-group fright">
            <button type="button" class="btn btn-primary" id="recommend_btn">
                <spring:message code="recommend.submit" />
            </button>
        </div>
    </div>

</div>
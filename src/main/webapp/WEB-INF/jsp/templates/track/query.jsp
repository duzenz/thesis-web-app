<div class="col-md-6">

    <div class="header">
        <h3 class="dark-grey">
            <spring:message code="recommend.title" />
        </h3>
    </div>
    
    <div class="query-container not-visible">
        <div class="query-form-container" style="width:55%;float:left">
            <div class="form-group">
                <label style="display: block">
                    <spring:message code="recommend.cftype" />
                </label>
                <input type="radio" id="recommend_cftype_user" name="recommend_cftype" value="user-based" checked>User Based
                <input type="radio" id="recommend_cftype_item" name="recommend_cftype" value="item-based">Item Based
            </div>
            
            <div class="form-group">
                <label style="display: block">
                    <spring:message code="recommend.duration" />
                </label>
                <select id="recommend_duration" name="recommend_duration" style="width: 90%">
                    <option value="0"><spring:message code="recommend.duration" /></option>
                    <option value="duration_short"><spring:message code="recommend.duration.short" /></option>
                    <option value="duration_normal"><spring:message code="recommend.duration.normal" /></option>
                    <option value="duration_long"><spring:message code="recommend.duration.long" /></option>
                </select>
            </div>
    
            <div class="form-group">
                <label style="display: block">
                    <spring:message code="recommend.selfview" />
                </label>
                <select id="recommend_selfview" name="recommend_selfview" style="width: 90%">
                    <option value="0"><spring:message code="recommend.selfview" /></option>
                    <option value="selfview_few"><spring:message code="recommend.selfview.few" /></option>
                    <option value="selfview_average"><spring:message code="recommend.selfview.average" /></option>
                    <option value="selview_many"><spring:message code="recommend.selfview.many" /></option>
                </select>
            </div>
    
            <div class="form-group">
                <label style="display: block"><spring:message code="recommend.tag" /></label>
                <select id="recommend_tag" name="recommend_tag" style="width: 100%" multiple="multiple">
                </select>
            </div>
    
            <div class="form-group">
                <label style="display: block">
                    <spring:message code="recommend.release" />
                </label>
                <select id="recommend_release" name="recommend_release" style="width: 90%">
                    <option value="0"><spring:message code="recommend.release" /></option>
                    <%
                        for (int i = 2000; i <= 2011; i++) {
                    %>
                    <option value="<%=i%>"><%=i%></option>
                    <%
                        }
                    %>
                </select>
            </div>
            
            <div class="form-group">
                <label style="display: block">
                    <spring:message code="recommend.artist" />
                </label>
                <input id="recommend_artist" name="recommend_artist" data-artist-id="" type="text" style="width: 90%" placeholder="<spring:message code='recommend.artist' />"/>
            </div>
    
            <div class="form-group not-visible recommend_track_div" >
                <label style="display: block">
                    <spring:message code="recommend.track" />
                </label>
                <input id="recommend_track" name="recommend_track" type="text" style="width: 90%" placeholder="<spring:message code='recommend.track' />"/>
            </div>
    
            <div class="form-group fright">
                <button type="button" class="btn btn-primary" id="recommend_btn">
                    <spring:message code="recommend.submit" />
                </button>
            </div>
        </div>
    
        <div class="query-container" style="border:2px solid #E6E6E6;float:left;width:45%;margin-top: 25px;padding-left: 10px;">
            <div><label class='query-container-label'><spring:message code="query.age" />: </label><span id="query_age"></span></div>
            <div><label class='query-container-label'><spring:message code="query.country" />: </label><span id="query_country"></span></div>
            <div><label class='query-container-label'><spring:message code="query.gender" />: </label><span id="query_gender"></span></div>
            <div><label class='query-container-label'><spring:message code="query.cftype" />: </label><span id="query_cftype"></span></div>
            <div><label class='query-container-label'><spring:message code="query.duration" />: </label><span id="query_duration"></span></div>
            <div><label class='query-container-label'><spring:message code="query.selfview" />: </label><span id="query_selfview"></span></div>
            <div><label class='query-container-label'><spring:message code="query.tag" />: </label><span id="query_tag"></span></div>
            <div><label class='query-container-label'><spring:message code="query.release" />: </label><span id="query_release"></span></div>
            <div><label class='query-container-label'><spring:message code="query.artist" />: </label><span id="query_artist"></span></div>
            <div><label class='query-container-label'><spring:message code="query.track" />: </label><span id="query_track"></span></div>
        </div>
    </div>

</div>
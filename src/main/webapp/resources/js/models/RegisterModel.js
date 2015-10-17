BaseModel("RegisterModel", {
	CreateUserService : Constant.BaseUrl + "rest/users/",
	LastFmUserService : Constant.BaseUrl + "rest/datauser/",
}, {
	init : function() {
	},

	createUser : function(userObj) {
		return this.makeRequest(this.Class.CreateUserService, userObj, "POST");
	},

	createLastFmUser : function(lastfmObj) {
		return this.makeRequest(this.Class.LastFmUserService, lastfmObj, "POST");
	}

});
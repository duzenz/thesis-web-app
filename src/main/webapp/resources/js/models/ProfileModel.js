BaseModel("ProfileModel", {
    oldPasswordCheckService : Constant.BaseUrl + "rest/profile/controlPassword",
    savePasswordService : Constant.BaseUrl + "rest/profile/savePassword",
}, {
    init : function() {
    },

    isOldPasswordCorrect : function(password) {
        return this.makeRequest(this.Class.oldPasswordCheckService, {
            userId : Constant.User.userId,
            password : password
        }, "GET", false);
    },

    saveNewPassword : function(password) {
        return this.makeRequest(this.Class.savePasswordService, {
            userId : Constant.User.userId,
            password : password
        }, "GET", false);
    }

});
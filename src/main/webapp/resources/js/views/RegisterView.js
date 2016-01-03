BaseView("RegisterView", {}, {
    init : function() {
        this.registerModel = new RegisterModel();
        this.age = $("#register_age");
        this.country = $("#register_country");
        this.gender = $("#register_gender");
        this.username = $("#register_username");
        this.password = $("#register_password");
        this.submit = $("#register_button");
        this.loginBtn = $("#login_btn");
        this.loginPassword = $("#password");
        this.bindEvents();
    },

    bindEvents : function() {
        var self = this;

        this.loginBtn.on("click", function() {
            var password = self.loginPassword.val();
            if (password.length < 4 || password.length > 8) {
                self.showFailPopup(Constant.Translation.password_validation_fail);
                return false;
            }
        });

        this.submit.on("click", function() {
            var username = self.username.val();
            var age = self.age.val();
            var country = self.country.val();
            var gender = self.gender.val();
            var password = self.password.val();
            var userObj = {};

            if (username == "" || age == "0" || country == "0" || gender == "0" || password == "") {
                self.showFailPopup(Constant.Translation.form_fill_fail);
            } else if (password.length < 4 || password.length > 8) {
                self.showFailPopup(Constant.Translation.password_validation_fail);
            } else {
                userObj.id = "";
                userObj.password = password;
                userObj.name = username;
                userObj.email = username;
                userObj.roles = [];
                userObj.roles[0] = {};
                userObj.roles[0].roleName = "ROLE_USER";

                self.registerModel.createUser(userObj).done(function(res) {
                    var lastfmObj = {};
                    lastfmObj.country = country;
                    lastfmObj.gender = gender;
                    lastfmObj.age = age;
                    lastfmObj.userId = res.id;

                    var d = new Date();
                    var yyyy = d.getFullYear().toString();
                    var mm = (d.getMonth() + 1).toString(); // getMonth() is
                    // zero-based
                    var dd = d.getDate().toString();
                    lastfmObj.registered = yyyy + "-" + (mm[1] ? mm : "0" + mm[0]) + "-" + (dd[1] ? dd : "0" + dd[0]);
                    lastfmObj.ageCol = self.getUserAgeCol(age);
                    lastfmObj.registerCol = yyyy;
                    self.registerModel.createLastFmUser(lastfmObj).done(function() {
                        self.showSuccessPopup(Constant.Translation.user_create_success);
                        self.clearRegisterForm();
                    }).fail(function(res) {
                        self.showFailPopup(Constant.Translation.user_create_fail);
                    });
                }).fail(function(res) {
                   self. showFailPopup(Constant.Translation.user_create_fail);   
                });
            }
        });
    },

    clearRegisterForm : function() {
        this.username.val("");
        this.age.puidropdown('selectValue', "0");
        this.country.puidropdown('selectValue', "0");
        this.gender.puidropdown('selectValue', "0");
        this.password.val("");
    }
});
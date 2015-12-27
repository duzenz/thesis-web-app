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
                $('#default').puigrowl('show', [ {
                    severity : 'error',
                    summary : 'Error',
                    detail : 'Girilen şifre 4 - 8 karakter arası olmalıdır.'
                } ]);
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
                $('#default').puigrowl('show', [ {
                    severity : 'error',
                    summary : 'Error',
                    detail : 'Tüm alanları doldurmanız gerekmektedir'
                } ]);
            } else if (password.length < 4 || password.length > 8) {
                $('#default').puigrowl('show', [ {
                    severity : 'error',
                    summary : 'Error',
                    detail : 'Girilen şifre 4 - 8 karakter arası olmalıdır.'
                } ]);
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
                        $('#default').puigrowl('show', [ {
                            severity : 'info',
                            summary : 'Info',
                            detail : 'Kullanıcı oluşturma işlemi başarılı'
                        } ]);
                        self.clearRegisterForm();
                    }).fail(function(res) {
                        $('#default').puigrowl('show', [ {
                            severity : 'error',
                            summary : 'Error',
                            detail : 'Kullanıcı oluşturma işlemi başarısız'
                        } ]);
                    });
                }).fail(function(res) {
                    $('#default').puigrowl('show', [ {
                        severity : 'error',
                        summary : 'Error',
                        detail : 'Kullanıcı oluşturma işlemi başarısız'
                    } ]);
                });
            }
        });

    },

    getUserAgeCol : function(age) {
        var ageCol = "";
        if (age > 0) {
            if (age <= 17) {
                ageCol = "0-17";
            } else if (age > 17 && age <= 24) {
                ageCol = "18-24";
            } else if (age > 24 && age <= 30) {
                ageCol = "25-30";
            } else if (age > 30 && age <= 40) {
                ageCol = "31-40";
            } else if (age > 40 && age <= 50) {
                ageCol = "41-50";
            } else {
                ageCol = "51-100";
            }
        }
        return ageCol;
    },

    clearRegisterForm : function() {
        this.username.val("");
        this.age.puidropdown('selectValue', "0");
        this.country.puidropdown('selectValue', "0");
        this.gender.puidropdown('selectValue', "0");
        this.password.val("");
    }
});
BaseView("AdminProfileView", {}, {
    init : function() {
        this.profileModel = new AdminProfileModel();
        this.currentPassword = $("#profile_current_password");
        this.newPassword = $("#profile_new_password");
        this.repeatedPassword = $("#profile_repeat_password");
        this.clearBtn = $("#profile_clear_btn");
        this.submitBtn = $("#profile_submit_btn");
        this.bindEvents();
    },

    bindEvents : function() {
        var self = this;
        this.submitBtn.on("click", function() {
            var currentPassword = self.currentPassword.val();
            var newPassword = self.newPassword.val();
            var repeatedPassword = self.repeatedPassword.val();
            if (self.validatePasswords(currentPassword, newPassword, repeatedPassword)) {
                self.profileModel.isOldPasswordCorrect(currentPassword).done(function(res) {
                    if (res) {
                        self.profileModel.saveNewPassword(newPassword).done(function(res) {
                            if (res) {
                                self.showSuccessPopup(Constant.Translation.password_success);
                                self.clearRegisterForm();
                            } else {
                                self.showFailPopup(Constant.Translation.password_fail);
                            }
                        }).fail(function(res) {
                            self.showFailPopup(Constant.Translation.password_fail);
                        });
                    } else {
                        self.showFailPopup(Constant.Translation.password_old_fail);
                    }
                }).fail(function(res) {
                    self.showFailPopup(Constant.Translation.password_change_fail);
                });
            }
        });

        this.clearBtn.on("click", function() {
            self.clearRegisterForm();
        });
    },

    validatePasswords : function(oldPassword, newPassword, repeatedPassword) {
        var self = this;
        if (oldPassword.length < 4 || oldPassword.length > 8) {
            self.showFailPopup(Constant.Translation.password_validation_fail);
            return false;
        }

        if (newPassword.length < 4 || newPassword.length > 8) {
            self.showFailPopup(Constant.Translation.password_validation_fail);
            return false;
        }

        if (repeatedPassword.length < 4 || repeatedPassword.length > 8) {
            self.showFailPopup(Constant.Translation.password_validation_fail);
            return false;
        }

        if (newPassword != repeatedPassword) {
            self.showFailPopup(Constant.Translation.password_repeated_fail);
            return false;
        }
        return true;
    },

    clearRegisterForm : function() {
        this.currentPassword.val("");
        this.newPassword.val("");
        this.repeatedPassword.val("");
    }
});
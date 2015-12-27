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
                                $('#default').puigrowl('show', [ {
                                    severity : 'info',
                                    summary : 'Info',
                                    detail : 'Şifre başarıyla değiştirildi.'
                                } ]);
                                self.clearRegisterForm();
                            } else {
                                $('#default').puigrowl('show', [ {
                                    severity : 'error',
                                    summary : 'Error',
                                    detail : 'Yeni şifre kaydı başarısız oldu!'
                                } ]);
                            }
                        }).fail(function(res) {
                            $('#default').puigrowl('show', [ {
                                severity : 'error',
                                summary : 'Error',
                                detail : 'Yeni şifre kaydı başarısız oldu!'
                            } ]);
                        });
                    } else {
                        $('#default').puigrowl('show', [ {
                            severity : 'error',
                            summary : 'Error',
                            detail : 'Eski şifrenizi yanlış girdiniz!'
                        } ]);
                    }
                }).fail(function(res) {
                    $('#default').puigrowl('show', [ {
                        severity : 'error',
                        summary : 'Error',
                        detail : 'Şifre değiştirme işlemi başarısız!'
                    } ]);
                });
            }
        });

        this.clearBtn.on("click", function() {
            self.clearRegisterForm();
        });
    },

    validatePasswords : function(oldPassword, newPassword, repeatedPassword) {
        if (oldPassword.length < 4 || oldPassword.length > 8) {
            $('#default').puigrowl('show', [ {
                severity : 'error',
                summary : 'Error',
                detail : 'Girilen şifreler 4 - 8 karakter arası olmalıdır.'
            } ]);
            return false;
        }

        if (newPassword.length < 4 || newPassword.length > 8) {
            $('#default').puigrowl('show', [ {
                severity : 'error',
                summary : 'Error',
                detail : 'Girilen şifreler 4 - 8 karakter arası olmalıdır.'
            } ]);
            return false;
        }

        if (repeatedPassword.length < 4 || repeatedPassword.length > 8) {
            $('#default').puigrowl('show', [ {
                severity : 'error',
                summary : 'Error',
                detail : 'Girilen şifreler 4 - 8 karakter arası olmalıdır.'
            } ]);
            return false;
        }

        if (newPassword != repeatedPassword) {
            $('#default').puigrowl('show', [ {
                severity : 'error',
                summary : 'Error',
                detail : 'Yeni şifre alanları birbiriyle uyuşmamaktadır.'
            } ]);
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
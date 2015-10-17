BaseView("RegisterView", {
}, {
	init : function() {
		this.registerModel = new RegisterModel();
		this.age = $("#register_age");
		this.country = $("#register_country");
		this.gender = $("#register_gender");
		this.username = $("#register_username");
		this.password = $("#register_password");
		this.submit = $("#register_button");
		this.bindEvents();
	},
	
	bindEvents: function() {
		var self = this;
		this.submit.on("click", function() {
			var username = self.username.val();
			var age = self.age.val();
			var country = self.country.val();
			var gender = self.gender.val();
			var password = self.password.val();
			var userObj = {};
			userObj.id = "";
			userObj.password = password;
			userObj.name = username;
			userObj.email = username;
			userObj.roles = [];
			userObj.roles[0] = {};
			userObj.roles[0].roleName = "ROLE_USER";
			
			
			if (username == "" || age == "0" || country == "0" || gender == "0" || password == "") {
				$('#default').puigrowl('show', [{severity: 'error', summary: 'Error', detail: 'Tüm alanları doldurmanız gerekmektedir'}]);
			} else {
				self.registerModel.createUser(userObj).done(function(res) {
					var lastfmObj = {};
					lastfmObj.country = country;
					lastfmObj.gender = gender;
					lastfmObj.age = age;
					lastfmObj.userId = res.id;
					lastfmObj.registered = "2015-01-25 12:00:00";
					self.registerModel.createLastFmUser(lastfmObj).done(function() {
						$('#default').puigrowl('show', [{severity: 'info', summary: 'Info', detail: 'Kullanıcı oluşturma işlemi başarılı'}]);
						self.clearRegisterForm();
					}).fail(function(res) {
						$('#default').puigrowl('show', [{severity: 'error', summary: 'Error', detail: 'Kullanıcı oluşturma işlemi başarısız'}]);
					});
				}).fail(function(res) {
					$('#default').puigrowl('show', [{severity: 'error', summary: 'Error', detail: 'Kullanıcı oluşturma işlemi başarısız'}]);
				});
			}
		});
		
	},
	
	clearRegisterForm: function() {
		this.username.val("");
		this.age.puidropdown('selectValue', "0");
		this.country.puidropdown('selectValue', "0");
		this.gender.puidropdown('selectValue', "0");
		this.password.val("");
	}
});
$(document).ready(function() {
	new BaseView();
});

Constant("BaseView", {}, {
	init : function() {
		console.log("Base view initialized");
		this.document = $(document);
		this.body = $("body");
		this.bindEvents();
	},
	
	getDialogOptions: function() {
		return {
			showEffect: 'fade',
			hideEffect: 'fade',
			minimizable: false,
			maximizable: false,
			draggable: false,
			resizable: false,
			modal: true,
			width: 700,
			height:500
		};
	},

	bindEvents : function() {
		var self = this;
		this.document.on({
			ajaxStart : function() {
				self.body.addClass("loading");
			},
			ajaxStop : function() {
				self.body.removeClass("loading");
			}
		});
		$('#default').puigrowl(); 
	}
});
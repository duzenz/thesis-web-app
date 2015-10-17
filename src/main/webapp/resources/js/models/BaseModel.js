Constant("BaseModel", {}, {
	init : function() {
		console.log("Base Model initialized");
	},

	makeRequest : function(url, data, type, stringify) {
		stringify = (typeof stringify === 'undefined') ? true : stringify;
		if (stringify === true) {
			data = JSON.stringify(data);
		}
		return $.ajax({
			url : url,
			type: type,
			dataType : "json",
			data: data,
			contentType: "application/json"
		});
	}

});
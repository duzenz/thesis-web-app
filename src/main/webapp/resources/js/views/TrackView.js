BaseView("TrackView", {
}, {
	init : function() {
		
		var self = this;
		this.document = $(document);
		this.trackModel = new TrackModel();
		
		$('#dlg').puidialog(
			self.getDialogOptions()
		);
		
		$(".query-container").show();
		this.setQueryParams();
		this.bindEvents();
	},
	
	bindEvents: function() {
		var self = this;
		
		$("#query_cftype").text("Kullanıcı Tabanlı");

		$('#recommend_artist').puiautocomplete({  
            effect: 'fade',
            effectSpeed: 'fast',
            completeSource:function(request, response) {  
                $.ajax({  
                    type: "GET",  
                    url: './rest/artist/search',  
                    data: {query: request.query},  
                    dataType: "json",  
                    context: this,  
                    success: function(data) {
                    	$("#query_artist").text("");
                    	$("#query_artist").attr("data-artist-id", "");
                    	var filteredData = [];
                    	for( var i = 0; i < data.length; i++) {
                    		filteredData.push(JSON.parse('{"value":' + data[i].id + ', "label":"' + data[i].artistName + '"}'));
                    	}
                        response.call(this, filteredData);  
                    }
                });
            },
            
            select: function(event, item) {
                $("#query_artist").text(item.data("label"));
                $("#query_artist").attr("data-artist-id", item.data("value"));
            }  
        });
		
		$('#recommend_track').puiautocomplete({  
            effect: 'fade',
            effectSpeed: 'fast',
            completeSource: function(request, response) {  
                $.ajax({  
                    type: "GET",  
                    url: './rest/track/search',  
                    data: {query: request.query},  
                    dataType: "json",  
                    context: this,  
                    success: function(data) {
                    	$("#query_track").text("");
                    	$("#query_track").attr("data-track-id", "");
                    	var filteredData = [];
                    	for( var i = 0; i < data.length; i++) {
                    		filteredData.push(JSON.parse('{"value":' + data[i].id + ', "label":"' + data[i].trackName + '"}'));
                    	}
                    	response.call(this, filteredData);  
                    }
                });  
            },
            
            select: function(event, item) {
                $("#query_track").text(item.data("label"));
                $("#query_track").attr("data-track-id", item.data("value"));
            }
        });
		
		$("#recommend_duration").puidropdown({
			change: function(event){
				$("#query_duration").text("");
				if (event.target.value != 0) { 
					$("#query_duration").text(event.target.selectedOptions[0].label);
				}
			}
		});
		
		$("#recommend_selfview").puidropdown({
			change: function(event){
				$("#query_selfview").text("");
				if (event.target.value != 0) { 
					$("#query_selfview").text(event.target.selectedOptions[0].label);
				}
			}
		});
		
		$('#recommend_tag').puilistbox({
			scrollHeight : 90,
			itemSelect: function(event, item) {  
				$("#query_tag").text($('#recommend_tag').val().toString());
			},
			itemUnselect: function(event, item) {
				$("#query_tag").text($('#recommend_tag').val().toString());
			}
		});
		
		$("#recommend_release").puidropdown({
			change: function(event){
				$("#query_release").text("");
				if (event.target.value != 0) { 
					$("#query_release").text(event.target.selectedOptions[0].label);
				}
			}
		});
		
		this.document.on("click", ".show-popup", function() {
			self.trackModel.getTrackDetail($(this).data("mbid")).done(function(res) {
				$("#track_name").text(res.track.name);
				$("#album_name").text(res.track.album.title);
				$("#album_url").text(res.track.album.url);
				$("#album_url").parent("a").attr("href", res.track.album.url);
				$("#duration").text(res.track.duration);
				$("#play_url").text(res.track.url);
				$("#play_url").parent("a").attr("href", res.track.url);
				$("#artist_name").text(res.track.artist.name);
				$("#artist_url").text(res.track.artist.url);
				$("#artist_url").parent("a").attr("href", res.track.artist.url);
				$('#dlg').puidialog("show");
			}).fail(function(res) {
				console.log("failed");
			});
		});
		
		this.document.on("click", "#recommend_cftype_user", function() {
			$(".recommend_track_div").hide();
			$("#query_cftype").text("Kullanıcı Tabanlı");
		});
		
		this.document.on("click", "#recommend_cftype_item", function() {
			$(".recommend_track_div").show();
			$("#query_cftype").text("Parça Tabanlı");
		});
		
		this.document.on("click", ".recommend_save_btn", function() {
			console.log("save");
			var table = $('#result-table').DataTable();
			var data = table.rows().data()
			for (var i = 0, len = data.length; i < len; i++) {
				if ($("#" + $(data[i].check).attr("id")).is(":checked")) {
					console.log($(data[i].check).attr("data-track-id"));
				}
			}
		});
		
		this.document.on("click", "#recommend_btn", function() {
			var cfType = $('input[name=recommend_cftype]:checked').val(),
				duration = $("#recommend_duration").val(),
				selfview = $("#recommend_selfview").val(),
				tag = $("#recommend_tag").val().toString(),
				release = $("#recommend_release").val(),
				artistId = $("#query_artist").attr("data-artist-id"),
				trackId = $("#query_track").attr("data-track-id");
			if (cfType == "item-based" && trackId == undefined) {
				$('#default').puigrowl('show', [{severity: 'error', summary: 'Error', detail: 'Item tabanlı işbirlikçi algoritmada parça girilmesi zorunludur.'}]);
			} else {
				trackId = trackId ? trackId : "";
				artistId = artistId ? artistId : "";
				$('.result-table-container').html("");
				self.trackModel.getRecommendations(Constant.User.userId, cfType, Constant.User.age, Constant.User.country, 
										Constant.User.gender, duration, selfview, tag, release, artistId, trackId)
										.done(function(res) {
											if (res) {
												self.createRecommendTable(res); 
											}
										}).fail(function(res) {
											console.log("Error occured");
										});
			}
		});
	},
	
	createRecommendTable: function(data) {
		var filteredData = [];
		for (var i = 0; i < data.length; i++) {
			var obj = {};
			obj.check = "<input type='checkbox' id='recommend-" + data[i].track.id + "' name='recommend-" + data[i].track.id + "' data-track-id='" + data[i].track.id + "'>";
			obj.mbid = data[i].track.trackId;
			obj.track = data[i].track.trackName;
			obj.show = "<a href='#' class='show-popup' data-index='" + i + "' data-mbid='" + data[i].track.trackId + "'>Show</a>";
			filteredData.push(obj);
		}
		
		$('.result-table-container').html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="result-table"></table>' );
		var  table = $('#result-table').DataTable( {
			"bFilter": false,
	    	"bLengthChange": false,
	    	"bPaginate": false,
	    	"scrollY": "550px",
	    	"scrollCollapse": true,
	    	"bInfo" : false,
	        "data": filteredData,
	        "columns": [
	            {"title": "Seç", "data": "check", "width":"10px"},
	            {"title": "Parça", "data": "track"},
	            {"title": "mbid", "data": "mbid", "visible":false},
	            {"title": "Dinle", "data": "show", "width":"10px"}
	        ]
	    });
		$('.result-table-container').append("<button class='btn recommend_save_btn' id='save_recommendations'>Save</button>")
	},
	
	setQueryParams: function() {
		$("#query_age").text(Constant.User.age);
		$("#query_country").text(Constant.User.country);
		$("#query_gender").text(Constant.User.gender);
	}
});
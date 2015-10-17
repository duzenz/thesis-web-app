BaseModel("TrackModel", {
	GetTopTracks: Constant.BaseUrl + "rest/trackfrekans/track/top/",
	GetSimilarTracks: Constant.BaseUrl + "rest/track/searchLabel/",
	GetUserBasedRecommendations: Constant.BaseUrl + "rest/usertrack/recommend/user/",
	GetItemBasedRecommendations: Constant.BaseUrl + "rest/usertrack/recommend/item/",
	GetRecommendations: Constant.BaseUrl + "rest/track/recommendTrack",
	GetTrackInfo: "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=465a9ad86bad93eff26b316a993ea6ca&format=json&mbid=",
}, {
	init : function() {
	},
	
	getSimilarTrackLabels: function(label) {
		return this.makeRequest(this.Class.GetSimilarTracks + label, {}, "GET");
	},
	
	getUserBasedRecommendations: function(userId, algorithm, itemCount) {
		return this.makeRequest(this.Class.GetUserBasedRecommendations + userId + "/" + itemCount + "/" + algorithm, {}, "GET");
	},
	
	getItemBasedRecommendations: function(itemId, algorithm, itemCount) {
		return this.makeRequest(this.Class.GetItemBasedRecommendations + itemId + "/" + itemCount + "/" + algorithm, {}, "GET");
	},
	
	getTrackDetail: function(mbid) {
		return this.makeRequest(this.Class.GetTrackInfo + mbid, {}, "GET");
	},
	

	getRecommendations : function(userId, cfType, age, country, gender, duration, selfview, tag, release, artistId, trackId) {
		return this.makeRequest(this.Class.GetRecommendations, 
								{
								userId : userId,
								cfType : cfType,
								age : age,
								country : country,
								gender : gender,
								duration : duration,
								selfview : selfview,
								tag : tag,
								release : release,
								artistId : artistId,
								trackId : trackId
								}, "GET", false);
	}
	
});
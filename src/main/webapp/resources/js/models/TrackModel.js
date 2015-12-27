BaseModel("TrackModel", {
    GetRecommendations : Constant.BaseUrl + "rest/track/recommendTrack",
    UpdateEngines : Constant.BaseUrl + "rest/track/updateEngines",
    GetTrackInfo : "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=465a9ad86bad93eff26b316a993ea6ca&format=json&mbid=",
}, {
    init : function() {
    },

    getTrackDetail : function(mbid) {
        return this.makeRequest(this.Class.GetTrackInfo + mbid, {}, "GET");
    },

    getRecommendations : function(userId, age, country, gender, register, tag) {
        return this.makeRequest(this.Class.GetRecommendations, {
            userId : userId,
            age : age,
            country : country,
            gender : gender,
            register : register,
            tag : tag,
        }, "GET", false);
    },

    updateEngines : function(obj) {
        return this.makeRequest(this.Class.UpdateEngines, obj, "POST");
    }

});
BaseView("TrackView", {}, {
    init : function() {
        var self = this;
        this.document = $(document);
        this.trackModel = new TrackModel();

        $('#dlg').puidialog(self.getDialogOptions());
        $(".query-container").show();

        this.setQueryParams();
        this.bindEvents();
    },

    setQueryParams : function() {
        $("#query_age").text(Constant.User.age);
        $("#query_country").text(Constant.User.country);
        $("#query_gender").text(Constant.User.gender);
        $("#query_register").text(Constant.User.register);
    },

    bindEvents : function() {
        var self = this;
        $("#query_cftype").text("Kullanıcı Tabanlı");
        $('#recommend_tag').puilistbox({
            scrollHeight : 90,
            itemSelect : function(event, item) {
                if ($('#recommend_tag').val() != null) {
                    $("#query_tag").text($('#recommend_tag').val().toString());
                }
            },
            itemUnselect : function(event, item) {
                if ($('#recommend_tag').val() != null) {
                    $("#query_tag").text($('#recommend_tag').val().toString());
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

        this.document.on("click", ".recommend_save_btn", function() {
            var table = $('#result-table').DataTable();
            var data = table.rows().data()
            var listenings = [];
            for (var i = 0, len = data.length; i < len; i++) {
                if ($("#" + $(data[i].check).attr("id")).is(":checked")) {
                    var obj = {};
                    obj.user = {
                        id : Constant.User.userId
                    };
                    obj.track = {
                        id : parseInt($(data[i].check).attr("data-track-id"))
                    };
                    obj.recommendSource = $(data[i].check).attr("data-recommend-source");
                    obj.time = self.getCurrentDate();
                    listenings.push(obj);
                }
            }
            self.trackModel.updateEngines(listenings).done(function(res) {
                console.log(res);
            }).fail(function(res) {
                console.log("Error happened");
            });
        });

        this.document.on("click", "#recommend_btn", function() {
            var tag = "";
            if ($("#recommend_tag").val() != null) {
                tag = $("#recommend_tag").val().toString();
            }

            $('.result-table-container').html("");

            // TODO user id fix
            self.trackModel.getRecommendations(13, self.getUserAgeCol(Constant.User.age), Constant.User.country, Constant.User.gender, Constant.User.register, tag).done(function(res) {
                if (res) {
                    self.createRecommendTable(res);
                }
            }).fail(function(res) {
                console.log("Error occured");
            });
        });
    },

    createRecommendTable : function(data) {
        var filteredData = [];
        for (var i = 0; i < data.length; i++) {
            var obj = {};
            obj.check = "<input type='checkbox' id='recommend-" + data[i].track.id + "' name='recommend-" + data[i].track.id + "' data-recommend-source='" + data[i].recommendationSource + "' data-artist-id='" + data[i].artistId + "' data-track-id='"
                    + data[i].track.id + "'>";
            obj.mbid = data[i].track.trackId;
            obj.track = data[i].track.trackName;
            obj.show = "<a href='#' class='show-popup' data-index='" + i + "' data-mbid='" + data[i].track.trackId + "'>Show</a>";
            filteredData.push(obj);
        }

        $('.result-table-container').html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="result-table"></table>');
        var table = $('#result-table').DataTable({
            "bFilter" : false,
            "bLengthChange" : false,
            "bPaginate" : false,
            "scrollY" : "550px",
            "scrollCollapse" : true,
            "bInfo" : false,
            "data" : filteredData,
            "columns" : [ {
                "title" : "Seç",
                "data" : "check",
                "width" : "10px"
            }, {
                "title" : "Parça",
                "data" : "track"
            }, {
                "title" : "mbid",
                "data" : "mbid",
                "visible" : false
            }, {
                "title" : "Dinle",
                "data" : "show",
                "width" : "10px"
            } ]
        });
        $('.result-table-container').append("<button class='btn recommend_save_btn' id='save_recommendations'>Save</button>")
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
    }

});
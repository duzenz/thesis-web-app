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

    getDialogOptions : function() {
        return {
            showEffect : 'fade',
            hideEffect : 'fade',
            minimizable : false,
            maximizable : false,
            draggable : false,
            resizable : false,
            modal : true,
            width : 700,
            height : 500
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
    },

    getCurrentDate : function() {
        var d = new Date();
        var yyyy = d.getFullYear().toString();
        var mm = (d.getMonth() + 1).toString(); // getMonth() is zero-based
        var dd = d.getDate().toString();
        var hh = d.getHours().toString();
        var min = d.getMinutes().toString();
        var ss = d.getSeconds().toString();
        return yyyy + "-" + (mm[1] ? mm : "0" + mm[0]) + "-" + (dd[1] ? dd : "0" + dd[0]) + " " + (hh[1] ? hh : "0" + hh[0]) + ":" + (min[1] ? min : "0" + min[0]) + ":" + (ss[1] ? ss : "0" + ss[0]);
    }
});
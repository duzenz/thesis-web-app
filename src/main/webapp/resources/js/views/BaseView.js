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
    },
    
    showSuccessPopup : function(message) {
        $('#default').puigrowl('show', [ {
            severity : 'info',
            summary : 'Info',
            detail : message
        } ]);
    },
    
    showFailPopup : function(message) {
        $('#default').puigrowl('show', [ {
            severity : 'error',
            summary : 'Error',
            detail : message
        } ]);
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
    },
    
    clearElements: function(target) {
        $(target).find(':input').each(function() {
          switch (this.type) {
              case 'password':
              case 'text':
              case 'textarea':
              case 'file':
              case 'select-one':
              case 'select-multiple':
                  $(this).val('');
                  break;
              case 'checkbox':
              case 'radio':
                  this.checked = false;
          }
      });
    },
    
});
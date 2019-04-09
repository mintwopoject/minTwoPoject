
$(document).ready(function(){


$("#loginbtn").click(function(){
    $("#loginbtn").button('loading').delay(1000).queue(function() {
    });
    $.ajax({
        type: "Post",
        contentType: "application/json;charset=UTF-8",
        url: '/sys/login',
        dataType:"json",
        data: $('#formtj').serializeJson(),
        success: function (data) {
            $("#yzmimg").click();


            $("#loginbtn" ).button('reset');
        }
    });
});


})

$(document).ready(function(){


/*$("#login").click(function(){
    // $("#loginbtn").button('loading').delay(1000).queue(function() {
    // });
    var AccountName = $("#AccountName").val();
    var Password = $("#Password").val();
    $.ajax({
        type: "post",
        contentType: "application/json;charset=UTF-8",
        url: '/sys/login',
        dataType:"json",
        data:{"AccountName":AccountName,"Password":Password} ,
        success: function (data) {
            toastr_success(JSON.stringify(data))
            $("#loginbtn" ).button('reset');
        }
    });
});*/

    $("#login").click(function () {
        // $("#contractCreationTemplateEditFrom").data('bootstrapValidator').validate();
        // if ($("#contractCreationTemplateEditFrom").data('bootstrapValidator').isValid()) {
        new AjaxRequest({
            type: "post",
            url: "/sys/login",
            param: $('#loginFrom').serializeJson(),
            tableparam: {currentpagecount: 1},
            callBack:function (data) {
                if (data==true){
                    window.location.replace("/static/main.html");
                    toastr_warning("登陆成功");

                }else {
                    window.location.replace("/static/login.html");
                    toastr_warning("登陆失败！")

                }
            }
        });
        // }
    })

})

$(document).ready(function(){
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
                    isSuccess(data)
                    setTimeout(function(){window.location.replace("/static/main.html");}, 1000);

                }else {
                    isSuccess(data)
                    // setTimeout(function(){window.location.replace("/static/login.html");}, 1000);
                }
            }
        });
        // }
    })


    function isSuccess(data) {
        if (data==true){
            toastr_success("登陆成功");
        }else {
            toastr_error("用户名或密码不对，请重新登陆！")
        }
    }

})
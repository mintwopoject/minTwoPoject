
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
                if (data==0){
                    toastr_success("登陆成功");
                    setTimeout(function(){window.location.replace("/static/main.html");}, 1000);

                }else if (data==2) {
                    toastr_warning("验证码不对，请重新输入")
                    uploadLoginValidateCode();
                    // setTimeout(function(){window.location.replace("/static/login.html");}, 1000);
                }else {
                    toastr_error("用户名或密码不对，请重新输入！")
                    uploadLoginValidateCode();
                }
            }
        });
        // }
    })



})
function uploadLoginValidateCode() {
    $("#loginValidateCode").attr("src","/loginValidateCode?random="+new Date().getMilliseconds());
}
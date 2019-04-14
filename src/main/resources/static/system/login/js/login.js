
// $(document).ready(function(){
//     $("#login").click(function () {
//         $("#loginFrom").data('bootstrapValidator').validate();
//         if ($("#loginFrom").data('bootstrapValidator').isValid()) {
//         new AjaxRequest({
//             type: "post",
//             url: "/loginVerify",
//             param: $('#loginFrom').serializeJson(),
//             tableparam: {currentpagecount: 1},
//             callBack:function (data) {
//                 if (data==0){
//                     toastr_success("登陆成功");
//                     // setTimeout(function(){window.location.replace("/main.html");}, 1000);
//                     qingkong()
//
//                 }
//             }
//         });
//         }
//     })
//
//
//
// })
function uploadLoginValidateCode() {
    $("#loginValidateCode").attr("src","/vercode/loginValidateCode?random="+new Date().getMilliseconds());
}
$(function () {
    $("#login").click(function () {
        $.ajax({
            url:"/loginVerify",
            type:"post",
            data:{
                accountName:$("#AccountName").val(),
                password:$("#Password").val(),
                validateCode:$("#validateCode").val()
            },
            success:function (data) {
                console.log(data);
                if(data.status){
                    toastr_success("登录成功")
                    window.location.href="/main.html";
                    // localStorage.setItem("accountName",data.data);
                }else if (data.message=='2') {
                    toastr_error("用户名或密码错!");
                    uploadLoginValidateCode();
                }else {
                    toastr_error("验证码错误!");
                    uploadLoginValidateCode();
                }
            }
        })

    })
})
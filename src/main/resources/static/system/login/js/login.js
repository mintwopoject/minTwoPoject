
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
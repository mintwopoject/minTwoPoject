function loginValidator() {
    $('#loginFrom').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            // invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [''],
        fields: {
            accountName: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                }
            },password: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                }
            },validateCode: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '验证码不能为空'
                    },
                }
            }
        }
    });


}

loginValidator()
function qingkong(){
    $('#loginFrom').bootstrapValidator('resetForm', true);
}

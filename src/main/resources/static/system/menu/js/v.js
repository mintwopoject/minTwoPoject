function menuAddValidator() {
    $('#menuaddfrom').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [''],
        fields: {
            text: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '菜单名称不能为空'
                    },
                }
            },linkAddress: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '菜单路径不能为空'
                    },
                }
            },icon: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '菜单图标不能为空'
                    },
                }
            },sort: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '菜单排序不能为空'
                    },regexp:{
                        regexp:/^\d+$|^\d+[.]?\d+$/,
                        message:"只能输入数字"
                    }
                }
            },
        }
    });
    $("#menuadd").on('hidden.bs.modal',function(e){
        $('#menuaddfrom').bootstrapValidator('resetForm', true);
        $('#menuaddfrom')[0].reset();
    });

}


function menuEditValidator() {
    $('#menueditfrom').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [''],
        fields: {
            text: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '菜单名称不能为空'
                    },
                }
            },linkAddress: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '菜单路径不能为空'
                    },
                }
            },icon: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '菜单图标不能为空'
                    },
                }
            },sort: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '菜单排序不能为空'
                    },regexp:{
                    regexp:/^\d+$|^\d+[.]?\d+$/,
                    message:"只能输入数字"
                }
                }
            },
        }
    });
    $("#menuedit").on('hidden.bs.modal',function(e){
        $('#menueditfrom').bootstrapValidator('resetForm', true);
        $('#menueditfrom')[0].reset();
    });

}

menuAddValidator();
menuEditValidator();



function roleAddValidator() {
    $('#roleAddFrom').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [''],
        fields: {
            roleName: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '部门名称不能为空'
                    },
                }
            },
            // menuids: {
            //     // trigger:"change",
            //     validators: {
            //         notEmpty: {
            //             message: '拥有权限不能为空'
            //         },
            //     }
            // },

        }
    });
    $("#roleAddModel").on('hidden.bs.modal',function(e){
        $('#roleAddFrom').bootstrapValidator('resetForm', true);
        $('#roleAddFrom')[0].reset();
    });

}

function roleEditValidator() {
    $('#roleEditFrom').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [''],
        fields: {
            roleName: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '部门名称不能为空'
                    },
                }
            },
            // menuids: {
            //     trigger:"change",
            //     validators: {
            //         notEmpty: {
            //             message: '拥有权限不能为空'
            //         },
            //     }
            // },

        }
    });
    $("#roleEditModel").on('hidden.bs.modal',function(e){
        $('#roleEditFrom').bootstrapValidator('resetForm', true);
        $('#roleEditFrom')[0].reset();
    });

}

roleAddValidator();
roleEditValidator();
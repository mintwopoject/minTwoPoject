function userAddValidator() {
    $('#userAddFrom').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [''],
        fields: {
            accountName: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },stringLength: {
                        min: 2,
                        max: 20,
                        message: '用户名长度为：2-20个字符'
                    },
                    regexp: {
                        regexp: /^[0-9a-zA_Z]+$/,
                        message: '输入内容不能为中文和包含其他非法字符'
                    },callback: {
                        message: '该相关依据名称已经存在！',
                        callback: function (value, validator) {
                            if (value == '') {
                                return true;
                            }
                            var tp;
                            new AjaxRequest({
                                url: '/system/tbUser/checkName',
                                type: 'get',
                                asynctype: false,
                                param: {accountName: value},
                                callBack: function (data) {
                                    tp = data.accountName;
                                }
                            });
                            return tp;
                        }
                    }

                }
            },
            password: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },stringLength: {
                        min: 6,
                        max: 30,
                        message: '密码长度为：6-30位'
                    }
                }
            },
            chose_password: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },identical: {
                        field: 'password',
                        message: '两次输入的密码不相符'
                    }
                }
            },
            realName: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '人员姓名'
                    },regexp: {
                        regexp:  /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/,
                        message: '请填写正确姓名'
                    }
                }
            },
            mobilePhone: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '电话不能为空'
                    },stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },
                    regexp: {
                        regexp: /^1[3|5|8|4|7|6]{1}[0-9]{9}$/,
                        message: '请填写正确手机号'
                    }
                }
            },
            email: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },regexp: {
                        regexp: /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/,
                        message: '请填写正确邮箱'
                    }
                }
            },
            roleids: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '拥有权限不能为空'
                    }
                }
            },
        }
    });
    $("#userAddModel").on('hidden.bs.modal',function(e){
        $('#userAddFrom').bootstrapValidator('resetForm', true);
        $('#userAddFrom')[0].reset();
    });

}


function userEditValidator() {
    $('#userEditFrom').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [''],
        fields: {
            accountName: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },stringLength: {
                        min: 2,
                        max: 20,
                        message: '用户名长度为：2-20个字符'
                    },
                    regexp: {
                        regexp: /^[0-9a-zA_Z]+$/,
                        message: '输入内容不能为中文和包含其他非法字符'
                    },callback: {
                        message: '该相关依据名称已经存在！',
                        callback: function (value, validator) {
                            if (value == '') {
                                return true;
                            }
                            var tp;
                            new AjaxRequest({
                                url: '/system/tbUser/checkName',
                                type: 'get',
                                asynctype: false,
                                param: {id:$("#id_Edit").val(),accountName: value},
                                callBack: function (data) {
                                    tp = data.accountName;
                                }
                            });
                            return tp;
                        }
                    }

                }
            },
            realName: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '人员姓名'
                    },regexp: {
                        regexp:  /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/,
                        message: '请填写正确姓名'
                    }
                }
            },
            mobilePhone: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '电话不能为空'
                    },stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },
                    regexp: {
                        regexp: /^1[3|5|8|4|7|6]{1}[0-9]{9}$/,
                        message: '请填写正确手机号'
                    }
                }
            },
            email: {
                trigger:"blur",
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },regexp: {
                        regexp: /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/,
                        message: '请填写正确邮箱'
                    }
                }
            },
            roleids: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '拥有权限不能为空'
                    }
                }
            },
        }
    });
    $("#userEditModel").on('hidden.bs.modal',function(e){
        $('#userEditFrom').bootstrapValidator('resetForm', true);
        $('#userEditFrom')[0].reset();
    });

}

userAddValidator();
userEditValidator();
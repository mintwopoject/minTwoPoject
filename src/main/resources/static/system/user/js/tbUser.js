var selecttableid;
$(document).ready(function(){

    window.operateEvents = {
        'click .useredit_button' : function(e, value, row) {
            selecttableid=row.ID;
            userInfo();
        },
        'click .userdelete_button' : function(e, value, row) {
            selecttableid=row.ID;
        },
    };

    $("#userList").bootstrapTable({
        url: '/system/tbUser/userList',                  //请求后台的URL（*）
        method: 'post', //请求方式（*）
        toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        sortable: false, //是否启用排序
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: pagenumber, //每页的记录行数（*）
        buttonsAlign: "right",
        paginationPreText: '上一页',
        paginationNextText: '下一页',
        uniqueId: "row_id",
        showRefresh: true, //是否显示刷新按钮.
        clickToSelect: true,//是否启用点击选中行
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        locale: 'zh-CN',
        showColumns: true,                  //是否显示所有的列
        ingleSelect : true, // 单选checkbox
        pageList: [pagenumber],
        //得到查询的参数
        queryParams: function (params) {
            currentpagecount = ((params.offset / params.limit) + 1)
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            var temp = {
                "currentpagecount": "" + ((params.offset / params.limit) + 1) + "" //页码
            };

            return temp;
        },
        columns: [{
            checkbox: true
        } ,
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: function (value, row, index) {
                    return getOrderNumber("userList", index)
                }
            },{
                field: 'AccountName',
                title: '用户名',
                align:'center',
            }, {
                field: 'RealName',
                title: '用户姓名',
                align:'center',
            }, {
                field: 'MobilePhone',
                title: '用户电话',
                align:'center',
            }, {
                field: 'Email',
                title: '邮箱',
                align:'center',
            }
            /*,{
                field: 'IsAble',
                title: 'IsAble',
                align:'center',
            },{
                field: 'IfChangePwd',
                title: 'IfChangePwd',
                align:'center',
            }*/
            ,{
                field: 'Description',
                title: '描述',
                align:'center',
            }, {
                field: 'HZType',
                title: 'HZ型',
                align:'center',
            }, {
                field: 'CreateBy',
                title: '创建人',
                align:'center',
            }, {
                field: 'CreateTime',
                title: '创建时间',
                align:'center',
            },{
                field: 'UpdateBy',
                title: '修改人',
                align:'center',
            },{
                field: 'UpdateTime',
                title: '修改时间',
                align:'center',
            }, {
                field: 'button',
                title: '操作',
                align: 'center',
                events : operateEvents,
                formatter: operateFormatter
            }
        ]

    });

    function operateFormatter(value, row, index) {
        selecttableid=row.id;
        return [
            '<button  type="button" title="修改" class="btn btn-primary btn-xs useredit_button" id="userEdit" data-toggle="modal" ><i class="fa fa-pencil"></i></button>',
            '<button  type="button" title="删除" class="btn btn-primary btn-xs userdelete_button" id="userdelete" data-toggle="modal" data-target="#userdel" style="background:#d9534f;border-color:#d9534f"><i class="fa fa-trash-o"></i></button>',
        ]
            .join('');

    };

});
//单条
function userInfo() {
    new AjaxRequest({
        url: "/system/tbUser/info/"+selecttableid,
        param: {},
        callBack: function (data) {
            $('#userEditFrom')[0].reset();
            $('#userEditFrom').initForm(data);
            getRoleAll("roleList","update")
            $("#userEditModel").modal("show");
        }

    })
}


$("#userdelbutton").click(function () {
    $.ajax({
        url:"/system/tbUser/delete/"+selecttableid,
        success:function (data) {
            if (data==true){
                toastr_success("操作成功")
                $("#userdel").modal("hide");
                window.location.reload();
            } else {
                toastr_error("操作失败！");
            }
        }
    })

    // new AjaxRequest({
    //     url: "/system/tbUser/delete/"+selecttableid,
    //     buttonid: 'userdelbutton',
    //     tableurl: '/system/tbUser/userList',
    //     tableid: 'userList',
    //     tableparam: {currentpagecount:1},
    //     modalid:'userdel',
    // });
})


//修改
$("#userEditButton").click(function () {
    // $("#contractCreationTemplateEditFrom").data('bootstrapValidator').validate();
    // if ($("#contractCreationTemplateEditFrom").data('bootstrapValidator').isValid()) {
    new AjaxRequest({
        url: "/system/tbUser/edit",
        param: $('#userEditFrom').serializeJson(),
        buttonid: 'userEditButton',
        tableurl: '/system/tbUser/userList',
        tableid: 'userList',
        tableparam: {currentpagecount: 1},
        modalid: 'userEditModel',
        numberpage: true
    });
    // }
})

//新增
$("#userAddButton").click(function () {
    // $("#contractCreationTemplateEditFrom").data('bootstrapValidator').validate();
    // if ($("#contractCreationTemplateEditFrom").data('bootstrapValidator').isValid()) {
    new AjaxRequest({
        url: "/system/tbUser/save",
        param: $('#userAddFrom').serializeJson(),
        buttonid: 'userAddButton',
        tableurl: '/system/tbUser/userList',
        tableid: 'userList',
        tableparam: {currentpagecount: 1},
        modalid: 'userAddModel',
        numberpage: true
    });
    // }
})



$("#btn-add").click(function () {
    getRoleAll("roleList_Add","");
})


//批量操作获取的id
$("#daochu").click(function () {
    selectId("userList");
});


function getRoleAll(inpId ,state){
    var select = $("#"+inpId);
    //回到初始状态
    select.selectpicker('val',['noneSelectedText'])
    select.html("");
    //重新赋值
    $("#"+inpId).selectpicker({
        noneSelectedText : '请选择角色'//默认显示内容
    });

    new AjaxRequest({
        url: "/role/tbRole/selectRoleList",
        callBack:function (data) {
            var results = data;
            for(var i =0;i<results.length;i++){
                select.append("<option class='form-control' value='"+results[i].id+"'>"+results[i].roleName+"</option>");
            }
            if(state == 'update'){
                new AjaxRequest({
                    url:   "/system/tbUser/getUserRole/"+selecttableid,
                    asynctype:false,
                    callBack:function (data) {
                        //初始化数据刷新
                        select.selectpicker('val', data.roleIds );
                    }
                });
            }

            //初始化数据刷新
            $("#"+inpId).selectpicker('refresh');
        }
    });



}
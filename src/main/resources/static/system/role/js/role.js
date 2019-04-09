var selecttableid;
var currentpagecount;
$(document).ready(function(){

    window.operateEvents = {
        'click .roleedit_button' : function(e, value, row) {
            selecttableid=row.Id;
            roleInfo();
        },
        'click .roledelete_button' : function(e, value, row) {
            selecttableid=row.Id;
        },
    };

    $("#roleList").bootstrapTable({
        url: '/role/tbRole/roleList',                  //请求后台的URL（*）
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
                    return getOrderNumber("roleList", index)
                }
            },{
                field: 'RoleName',
                title: '部门名称',
                align:'center',
            }, {
                field: 'Description',
                title: '拥有权限',
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
                '<button  type="button" title="修改" class="btn btn-primary btn-xs roleedit_button" id="roleEdit" data-toggle="modal" ><i class="fa fa-pencil"></i></button>',
                '<button  type="button" title="删除" class="btn btn-primary btn-xs roledelete_button" id="roledelete" data-toggle="modal" data-target="#roledel" style="background:#d9534f;border-color:#d9534f"><i class="fa fa-trash-o"></i></button>',
            ]
                .join('');

    };

})
//菜单权限单条
function roleInfo() {
    new AjaxRequest({
        url: "/role/tbRole/info/"+selecttableid,
        param: {},
        callBack: function (data) {
            $('#roleEditFrom')[0].reset();
            $('#roleEditFrom').initForm(data);
            //生成树
            var tree = data.id;
            MyTreeView('depttreeview_edit','useScope_edit','useScopename_edit','/system/tbmenu/menuChachaox/'+tree,'{}','POST','2','请选择部门权限');
            // MyTreeView('depttreeview_add','useScope_add','useScopename_add','/system/tbmenu/muenInfo/'+data.id,'{}','POST','1','请选择使用范围');
            $("#roleEditModel").modal("show");
        }

    })
}

//修改
$("#roleEditButton").click(function () {
    // $("#contractCreationTemplateEditFrom").data('bootstrapValidator').validate();
    // if ($("#contractCreationTemplateEditFrom").data('bootstrapValidator').isValid()) {
        new AjaxRequest({
            url: "/role/tbRole/edit",
            param: $('#roleEditFrom').serializeJson(),
            buttonid: 'roleEditButton',
            tableurl: '/role/tbRole/roleList',
            tableid: 'roleList',
            tableparam: {currentpagecount: 1},
            modalid: 'roleEditModel',
            numberpage: true
        });
   // }
})

//新增
$("#roleAddButton").click(function () {
    // $("#contractCreationTemplateEditFrom").data('bootstrapValidator').validate();
    // if ($("#contractCreationTemplateEditFrom").data('bootstrapValidator').isValid()) {
    new AjaxRequest({
        url: "/role/tbRole/save",
        param: $('#roleAddFrom').serializeJson(),
        buttonid: 'roleAddButton',
        tableurl: '/role/tbRole/roleList',
        tableid: 'roleList',
        tableparam: {currentpagecount: 1},
        modalid: 'roleAddModel',
        numberpage: true
    });
    // }
});

$("#btn-add").click(function () {
    MyTreeView('depttreeview_Add','useScope_Add','useScopename_Add','/system/tbmenu/menuList','{}','POST','1','请选择部门权限');

})




//批量操作获取的id
$("#daochu").click(function () {
    selectId("roleList");
});


$("#daoru").click(function(){
    var formdata = new FormData($("#form_table")[0]);
    $.ajax({
        url: "/ustils/Excel/import",
        type: "POST",
        data:formdata,
        dataType: "json",
        processData: false,  // 告诉jQuery不要去处理发送的数据
        contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
        success: function (res) {

        }
    })
})


$("#roledelbutton").click(function () {
    $.ajax({
        url:"/role/tbRole/delete/"+selecttableid,
        success:function (data) {
            if (data==true){
                toastr_success("操作成功")
                $("#roledel").modal("hide");
                window.location.reload();
            } else {
                toastr_error("操作失败！");
            }
        }
    })
    // $("#roledel").modal("hide");

    // new AjaxRequest({
    //     url: "/role/tbRole/delete/"+selecttableid,
    //     buttonid: 'roledelbutton',
    //     tableurl: '/role/tbRole/roleList',
    //     tableid: 'roleList',
    //     tableparam: {currentpagecount:currentpagecount},
    //     modalid:'roledel',
    //     numberpage:true
    // });

})

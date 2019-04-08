
var nods;
var treenode;


$("#tree").contextMenu({
    width: 110, // width
    itemHeight: 30, // 菜单项height
    bgColor: "#333", // 背景颜色
    color: "#fff", // 字体颜色
    fontSize: 12, // 字体大小
    hoverBgColor: "#99CC66", // hover背景颜色
    target: function(ele) { // 当前元素
        console.log(ele);
    },
    menu: [{ // 菜单项
        text: "新增",
        // icon: "/static/assets/images/add.png",
        callback: function() {
            add()
        }
    },
        {
            text: "修改",
            // icon: "/static/assets/images/paste.png",
            callback: function() {
                edit()
            }
        },
        {
            text: "删除",
            // icon: "/static/assets/images/del.png",
            callback: function() {
                del();
            }
        }
    ]

});

$(document).on('mouseover', '.me-codesta', function () {
    $('.finale h1:first').css({ opacity: 0 });
    $('.finale h1:last').css({ opacity: 1 });
});

$(document).on('mouseout', '.me-codesta', function () {
    $('.finale h1:last').css({ opacity: 0 });
    $('.finale h1:first').css({ opacity: 1 });
});


$('body').on('mousedown', '#tree li', function (e) {
    if (e.button == 2) {
        $(this).click();
        return false;
    }
    return true;
});


function add(){
    info("parentId_add","parentName_add");
    $("#menuadd").modal('show');
}

function edit(){
    infoTo("parentId_edit","parentName_edit");
    infoTos("text_edit","id_edit","linkAddress_edit","icon_edit","sort_edit")
    $("#menuedit").modal('show');
}

function del(){
    $("#menudel").modal('show');
}


function info(id,name){
    $.ajax({
        url:"/system/tbmenu/info/"+treenode.id,
        success:function (data) {
            $("#"+id).val(data.id)
            $("#"+name).val(data.text)
        }
    })
}

function infoTo(id,name){
    $.ajax({
        url:"/system/tbmenu/infoto/"+treenode.id,
        success:function (data) {
            $("#"+id).val(data.id)
            $("#"+name).val(data.text)
        }
    })
}

function infoTos(text,id,url,icon,sort){
    $.ajax({
        url:"/system/tbmenu/infotos/"+treenode.id,
        success:function (data) {
            $("#"+text).val(data.text)
            $("#"+id).val(data.id)
            $("#"+url).val(data.linkAddress)
            $("#"+icon).val(data.icon)
            $("#"+sort).val(data.sort)
        }
    })
}


$("#menudelbutton").click(function () {
    $.ajax({
        url:"/system/tbmenu/delete/"+treenode.id,
        success:function (data) {
            if (data==true){
                $("#menudel").modal('hide');
                toastr_success("操作成功")
                ss()
            } else {
                toastr_error("操作失败！");
            }
        }
    })
})



//新增
$("#menuaddbutton").click(function () {
    new AjaxRequest({
        url: "/system/tbmenu/add",
        param: $('#menuaddfrom').serializeJson(),
        buttonid:'menuaddbutton',
        callBack: function (data) {
            console.log(data)
            if (data==true){
                $("#menuadd").modal("hide");
                toastr_success("操作成功");
                ss()
            } else {
                toastr_error("操作失败！");
            }

        }
    });
})

//修改
$("#menueditbutton").click(function () {
    new AjaxRequest({
        url: "/system/tbmenu/edit",
        param: $('#menueditfrom').serializeJson(),
        buttonid:'menueditbutton',
        callBack: function (data) {
            if (data==true){
                $("#menuedit").modal("hide");
                toastr_success("操作成功");
                ss()
            } else {
                toastr_error("操作失败！");
            }

        }
    });
})


//菜单列表
function ss() {
    $.ajax({
        url:"/system/tbmenu/menuList",
        success:function (data) {
            nods=data
            $('#tree').treeview({
                data: nods,//节点数据
                showBorder: true, //是否在节点周围显示边框
                showCheckbox: false, //是否在节点上显示复选框
                showIcon: true, //是否显示节点图标
                highlightSelected: true,
                levels: 0,
                multiSelect: false, //是否可以同时选择多个节点
                showTags: true,
            });
        }
    })

};

ss();
function itemOnclick(target) {
    //找到当前节点id
    var nodeid = $(target).attr('data-nodeid');
    var tree = $('#tree');
    // console.log(treeview)
    //获取当前节点对象
    treenode = tree.treeview('getNode', nodeid);
}
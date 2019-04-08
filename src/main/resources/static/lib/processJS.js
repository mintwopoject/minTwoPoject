var QJpassingType="";
//要素保存路径
var auditingProxyPath = "/business/auditingkeypoint";
var instanceCode="";
var linkCode="";
var processCode="";
// 监控事件 点击查看审批意见详情
window.operateEventsApprovalOpinion = {
    'click .look_button' : function(e, value, row) {
        $("#lookapprovalOpinion").html(value);
        $("#lookapprovalOpinionModal").modal('show')

    }
};

/**
 *
 * @param selectId                     下一环节下拉
 * @param msgList                      返回值list,用来循环出下拉
 * @param processmodalId               流程模态框id
 * @param nodeCodeId                返回值流程code 此id用来赋值input的code
 * @param modalid                      新增页面或者编辑页面的模态框id
 * @param tableId                      列表tableId
 * @param addOrtype                    true 为新增页面 flase 为编辑页面
 * @param currentpagecount             刷新table表格的页码 addOrtype:true的状态下不用传
 */
function getProcessNodeList(passing_type,currentNodeCode,rowid,nodeType,userId,instanceCode,msgList,modalid,tableId,addOrtype,currentpagecount,laseNode) {
    QJpassingType =  passing_type;
    if (rowid!="") {
        $("#rowid").val(rowid);
    }
    $("#instance_code").val(instanceCode);
    $("#userId").val(userId);
    $("#currentNodeCode").val(currentNodeCode);
    $("#nodeType").val(nodeType);
    // 清空
    $("#node_code").empty();
    $("#huanjie").empty();
    $("#node_code").append("<option value=''>请选择</option>");
    for (var i = 0; i < msgList.length; i++){
        $("#node_code").append("<option value="+msgList[i].nodeCode+">"+msgList[i].nodeName+":"+msgList[i].approvalType+"</option>");
        $("#huanjie").append("<input type='hidden' id="+msgList[i].nodeCode+"  value="+msgList[i].approvalType+">");
    }
    if (addOrtype){
        $("#"+ tableId).bootstrapTable('refreshOptions',{pageNumber:1})
    } else {
        $("#"+ tableId).bootstrapTable('refreshOptions',{pageNumber:currentpagecount})
    }
    initprocesstable(nodeType,instanceCode,currentNodeCode,passing_type,laseNode);
    $("#liuchengModal").modal('show');
    if (modalid!=""){
        $("#"+modalid).modal('hide');
    }
}

/**
 * 获取当前环节审批人集合
 * @param line_node 环节code
 * @param process_code 流程code
 */

function getApprovalUserids(node_code,instanceCode,approvalType,passingType) {
    $.ajax({
        type:'post',
        url: "/process/sendURL",
        contentType: "application/json;charset=UTF-8",
        headers:{"token":sessionStorage.getItem("token")},
        data:JSON.stringify({
            node_code:node_code,
            url: ApprovalUserids,
            instance_code:instanceCode,
            authorization_code:sessionStorage.getItem("workflowCode"),
            passing_type:passingType
        }),
        success: function (data, status) {
            const strToObj = JSON.parse(data)[0]
            if (strToObj.statecode==200){
                var createId=strToObj.rows.create_user;
                var deptId=strToObj.rows.confOrganization;
                var roleId=strToObj.rows.confQuarters;
                var userId=strToObj.rows.confUser;
                if (approvalType=="一人审批"){
                    getApproval(createId,deptId,roleId,userId,true)  ;//单人模式
                }else {
                    getApproval(createId,deptId,roleId,userId,false) ;//多人模式
                }

            }else {
                toastr_error(strToObj.stateinfo);
            }
        },
        error: function (err, status) {
            if(JSON.stringify(err.statusText).indexOf("timeout")==1){
                toastr_error('请求超时!');
            }else {
                toastr_error('操作失败!');
            }
        }
    })
}
function getApproval(createId,deptId,roleId,userId,bool){
    var select = $("#userids");
    //回到初始状态
    select.html("")
    if (bool){
        select.selectpicker('destroy').removeAttr("multiple").val('').selectpicker({
            noneSelectedText: '请选择人员...' //默认显示内容
        });
    }else {
        select.selectpicker('destroy').attr("multiple",true).val('').selectpicker({
            noneSelectedText: '请选择人员...' //默认显示内容
        });
    }
    $.ajax({
        type:'post',
        url: "/process/getProcessUserId",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "deptId" : deptId,
            "roleId" : roleId,
            "userId" : userId,
            "createId":createId
        }),
        success: function (data, status){
            var datas=data.returnlist;
            for (var i = 0; i < datas.length; i++){
                select.append("<option value="+datas[i].id+">"+datas[i].personName+"</option>");
            }
            //初始化数据刷新
            select.selectpicker('refresh');
            select.selectpicker('render');
        },
        error:function (err, status) {
            toastr_error(err.msg)
        }
    });
}

/**
 * 初始化审批环节页面
 */
function initprocesstable(nodeType,instanceCode,currentNodeCode,passing_type,laseNode) {
    $('#processtable').bootstrapTable('destroy');
    $('#approvalOpinion').val("");
    var select = $("#userids");
    select.selectpicker('destroy').html("").val('').selectpicker({
        noneSelectedText: '请选择人员...' //默认显示内容
    });
    if(nodeType=="start round mix"){
        console.log("当前环节为:启动环节");
        divhideorshow(true,true,false,false);
    }else if (nodeType=="end round mark") {
        console.log("当前环节为:结束环节");
        if (passing_type=="同意"){
            divhideorshow(false,false,true,true);
        } else {
            divhideorshow(false,false,false,false);
        }
        processInitFileInput(instanceCode,currentNodeCode);
        processCreateUploadTable(instanceCode,currentNodeCode,true,true);
    }else {
        console.log("当前环节为:正常环节");
        if (laseNode=="nolast" && passing_type=="同意"){
            divhideorshow(false,false,true,true);
        } else {
            divhideorshow(false,false,false,false);
        }
        processInitFileInput(instanceCode,currentNodeCode);
        processCreateUploadTable(instanceCode,currentNodeCode,true,true);
    }
}
/**
 * div显隐选择器
 */
function divhideorshow(isUpload,isShenpiyijian,isHuanjieshenpiren,isXiayihuanjie){
    if (isUpload){
        $("#process_upload").hide();
    }else {
        $("#process_upload").show();
    }
    if (isShenpiyijian){
        $("#shenpiyijian").hide();
    }else {
        $("#shenpiyijian").show();
    }
    if (isHuanjieshenpiren){
        $("#huanjieshenpiren").hide();
    }else {
        $("#huanjieshenpiren").show();
    }
    if (isXiayihuanjie){
        $("#xiayihuanjie").hide();
    }else {
        $("#xiayihuanjie").show();
    }
}

//下一环节 监控change 事件
// 获取当前环节审批人集合
$('#node_code').change(function(){
    var nodeCode =$("#node_code").val();
    var instanceCode =$("#instance_code").val();
    var approvalType =$("#"+nodeCode).val();
    if (nodeCode!=''){
        getApprovalUserids(nodeCode,instanceCode,approvalType,QJpassingType);
    }else {
        $('#approvalOpinion').val("");
        var select = $("#userids");
        select.selectpicker('destroy').html("").val('').selectpicker({
            noneSelectedText: '请选择人员...' //默认显示内容
        });
    }
});

//渲染loaderbutton
function showLoaderbutton(buttonids){
    var myshows = buttonids.split(",");
    for(var j = 0,len = myshows.length; j < len; j++){
        $("#"+myshows[j] ).button('loading').delay(1000).queue(function() {
        });
    }
};
//隐藏loaderbutton
function hideLoaderbutton(buttonids){
    var myhides = buttonids.split(",");
    for(var j = 0,len = myhides.length; j < len; j++){
        $("#"+myhides[j] ).button('reset');
    }
};


//'审批所用时间'格式化
function ChangeHourMinutestr (value, row, index) {
    if (value !== "" && value !== null) {
        if (value ==0){return "<1分钟"}
        return ((Math.floor(value / 60)).toString().length < 2 ? "0" + (Math.floor(value / 60)).toString() : (Math.floor(value / 60)).toString()) + "小时" + ((value % 60).toString().length < 2 ? "0" + (value % 60).toString() : (value % 60).toString())+"分钟";
    }else {
        return "";
    }

}
//审批建议的格式化
function lookapprovalOpinion (value, row, index) {
    var values = row.approval_opinion;
    var span=document.createElement('span');
    span.setAttribute('title',values);
    span.innerHTML = (row.approval_opinion.toString().length>10)?(row.approval_opinion.substring(0,10)+"..."):(row.approval_opinion);
    return [
        '<a class="look_button" style="cursor:pointer;">'+span.outerHTML+'</a>'
    ]
        .join('');

}
//按钮触发获取相应数据
function buttonAjax(type){
    passing_type = type;
    $.ajax({
        type: 'post',
        url: "/process/sendURL",
        contentType: "application/json;charset=UTF-8",
        headers:{"token":sessionStorage.getItem("token")},
        beforeSend:showLoaderbutton("tongyi,bohui"),
        data:JSON.stringify({
            url: ProcessNodeList,
            instance_code:instanceCode,
            passing_type:type,
            authorization_code:sessionStorage.getItem("workflowCode"),
            taskid:task_id
        }),
        success: function (data, status) {
            hideLoaderbutton("tongyi,bohui");
            console.log(data)
            const strToObj = JSON.parse(data)[0]
            var msg = strToObj.rows.processDesigns;
            var laseNode = strToObj.rows.lase_node;
            var nodeType = strToObj.rows.processDesign.nodeType;
            var currentNodeCode = strToObj.rows.processDesign.nodeCode;
            getProcessNodeList(type,currentNodeCode,"",nodeType,sessionStorage.getItem("landerId"),instanceCode,msg,"","todolist",true,"",laseNode);

            //添加下拉多选
            initLoadAuditingSelectpicker();
        },
        error: function (err, status) {
            if (JSON.stringify(err.statusText).indexOf("timeout") == 1) {
                toastr_error('请求超时!');
            } else {
                toastr_error('操作失败!');

            }
        }
    })
}
//同意按钮事件
$('#tongyi').bind('click',function(){
    if( myValidator() ){
        if(keypointObj.length==0){
            buttonAjax("同意")
        }else{
            new AjaxRequest({
                url: auditingProxyPath+"/save",
                param: {jsonStr:$("#keyPointForm").serializeJson(),processCode:processCode,linkCode:linkCode,instanceCode:instanceCode},
                asynctype :false,
                contentType :"default",
                buttonid:'auditingAddBtn',
                callBack: function (data) {
                    toastr_success(data.msg);
                    if(data.code == 0){
                        buttonAjax("同意")
                    }else{
                        toastr_error(data.msg);
                    }
                }
            })
        }
    }else {
        $('#myTab a[href="#home3"]').tab('show')
        toastr_error('请先查看审核要素,是否填写完整!');
    }
});
//打回起草环节提交
$('#tijiao').bind('click',function(){
    buttonAjax("同意");
});

//驳回按钮事件
$('#bohui').bind('click',function(){

    if( myValidator() ){
        if(keypointObj.length==0){
            buttonAjax("驳回")
        }else {
            new AjaxRequest({
                url: auditingProxyPath+"/save",
                param: {jsonStr:$("#keyPointForm").serializeJson(),processCode:processCode,linkCode:linkCode,instanceCode:instanceCode},
                asynctype :false,
                contentType :"default",
                buttonid:'auditingAddBtn',
                callBack: function (data) {
                    if(data.code == 0){
                        buttonAjax("驳回")
                    }else{
                        toastr_error(data.msg);
                    }
                }
            })
        }

    }else {
        $('#myTab a[href="#home3"]').tab('show')
        toastr_error('请先查看审核要素,是否填写完整!');
    }

});

//流程文件上传
$("#uploadFile__process").click(function () {
    document.getElementById('uploadForm').reset()
    $("#upload_div").modal('show');
});

//根据Code查询审批信息
function findByCode(processCode,linkCode,instanceCode) {
    new AjaxRequest({
        url: auditingProxyPath+"/findByCode",
        param: {processCode:processCode,linkCode:linkCode,instanceCode:instanceCode},
        asynctype :false,
        contentType :"default",
        callBack: function (data) {
            initLoadValue(data);
        }
    })
}
//查看附件
function Lookfileprocess(approvalLinkCode,instanceCode) {
    processCreateUploadFileTable("processlookfiletable",instanceCode,approvalLinkCode,true)
    $("#lookfileModal").modal('show')
}



//起草申请我的流程查看(流程图和审批记录)
function showmyprocess(instanceCode) {
    //流程图查看
    $('#liuchengtu').attr('src', openpathinfo+"?authorization_code="+sessionStorage.getItem("workflowCode")+"&instance_code="+instanceCode+"");
    $("#processAuditRecordlist").bootstrapTable('destroy');
    $("#processAuditRecordlist").bootstrapTable({
        url: "/process/sendURL",
        contentType: "application/json;charset=UTF-8",
        headers:{"token":sessionStorage.getItem("token")},
        method: 'post', //请求方式（*）
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        sortable: false, //是否启用排序
        pageNumber:1, //初始化加载第一页，默认第一页
        pageSize: 10, //每页的记录行数（*）
        buttonsAlign: "right",
        paginationPreText:'上一页',
        paginationNextText:'下一页',
        uniqueId: "id",
        showRefresh: true, //是否显示刷新按钮.
        clickToSelect: true,//是否启用点击选中行
        sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        locale:'zh-CN',
        pageList: [pagenumber],    //得到查询的参数
        responseHandler: function (res){
            return res[0].rows.rows
        },
        queryParams: function(params) {
            var temp = {
                url: ProcessInstanceApproval,
                instance_code:instanceCode,
                authorization_code:sessionStorage.getItem("workflowCode")
            };

            return JSON.stringify(temp);
        },
        onLoadSuccess: function(data){
            $("#myprocess").modal('show');
        },
        columns: [{
            title: '序号',
            field: '',
            align: 'center',
            formatter: function (value, row, index) {
                return index+1
            }
        },{
            field: 'node_name',
            title: '环节名称',
            align: 'center'

        },{
            field: 'approval_opinion',
            title: '审批建议',
            align: 'center',
            class: 'colStyle',
            events : operateEventsApprovalOpinion,
            formatter: lookapprovalOpinion
        },{
            field: 'user_name',
            title: '审批人',
            align: 'center'
        },{
            field: 'user_department',
            title: '审批人部门',
            align: 'center'
        },{
            field: 'approval_type',
            title: '审批结果',
            align: 'center'
        },{
            field: 'complete_time',
            title: '审批完成时间',
            align: 'center'
        },{
            field: 'spent_time',
            title: '审批所用时间',
            align: 'center',
            formatter: ChangeHourMinutestr
        }, {
            field: 'time_out_is',
            title: '是否超时审批',
            align: 'center'
        }, {
            field: 'button',
            title: '操作',
            align: 'center',
            events : operateEvents,
            formatter: operateFormatter1
        }]
    });
    function operateFormatter1(value, row, index) {
        return [
            '<button  type="button" class="btn btn-primary btn-xs lookfile_button" id="lookfileMyhandlebutton" data-toggle="modal" title="查看附件"><i class="fa fa-clock-o"></i></button>'
        ]
            .join('');
    }
    $('#myTab a[href="#home"]').tab('show');
}



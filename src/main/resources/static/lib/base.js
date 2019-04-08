var dictPath = "/sys/dict";
/**
 * @auther chengkexing
 * @date 2019-1-8
 * 格式为
 * [
 *     [生成selectid , 字典表父级唯一标识]，
 *     [生成selectid , 字典表父级唯一标识],
 *     ...
 * ]
 * @param body  生成select的内容
 * @param only  是否允许多次复用(选填默认为true)
 * @Other 字典表部分
 */

function SelectObj() {
    var obj = {};
    this.add = function(key, value) {
        obj[key] = value;//把键值绑定到obj对象上
    }
    //get方法，根据key获取value的值
    this.get = function(key) {
        if(obj[key] || obj[key] === 0 || obj[key] === false) {
            return obj[key]
        } else {
            return null;
        }
    }
}
function loadDictSelet(body,only){
    if(only == null)only = true;
    var selectObj = new Map();
    for (var i = 0; i < body.length; i++) {
        var dataBoday = body[i][1];
        if (dataBoday!=""){
            $.ajaxSetup({ cache: true });
            $.ajax({
                type: 'get',
                url: dictPath+"/checkDictOut/"+dataBoday,
                contentType: "application/json;charset=UTF-8",
                async:false,
                dataType:"json",
                success: function (data) {
                    if(data != null) {
                        var baseSelect = $(body[i][0]);
                        for (var j = 0; j <data.length ; j++) {
                            if(j==0){
                                if(only) baseSelect.empty();
                                if(baseSelect.children("option").length === 0) baseSelect.append("<option value=''>请选择</option>");
                            }
                            baseSelect.append("<option value='"+data[j].dictId+"'>"+data[j].dictName+"</option>");
                            selectObj.set(data[j].dictId,data[j].dictName);
                        }
                    }
                }
            });
        }
    }
    // toastr_error(JSON.stringify(selectObj))
    return selectObj;
}
function loadDictALLSelet(body,only){
    if(only == null)only = true;
    var selectObj = new Map();
    for (var i = 0; i < body.length; i++) {
        var dataBoday = body[i][1];
        if (dataBoday!=""){
            $.ajaxSetup({ cache: true });
            $.ajax({
                type: 'get',
                url: dictPath+"/checkDictOut/"+dataBoday,
                contentType: "application/json;charset=UTF-8",
                async:false,
                dataType:"json",
                success: function (data) {
                    if(data != null) {
                        var baseSelect = $(body[i][0]);
                        for (var j = 0; j <data.length ; j++) {
                            if(j==0){
                                if(only) baseSelect.empty();
                                if(baseSelect.children("option").length === 0) baseSelect.append("<option value='ALL'>请选择</option>");
                            }
                            baseSelect.append("<option value='"+data[j].dictId+"'>"+data[j].dictName+"</option>");
                            selectObj.set(data[j].dictId,data[j].dictName);
                        }
                    }
                }
            });
        }
    }
    // toastr_error(JSON.stringify(selectObj))
    return selectObj;
}
function add_modal_open (){
    var modal_list = $('.listen_modal') ;
    $.each(modal_list,function (i) {
        var modal_ = $( modal_list[i]).attr('id');
        $('#' + modal_).on('hidden.bs.modal', function () {
            $('body').addClass("modal-open");
        })
    })
}
function RMB(num){
    if(num){
        num = typeof num == "string"?parseFloat(num):num//判断是否是字符串如果是字符串转成数字
        num = num.toFixed(2);//保留两位
        num = parseFloat(num);//转成数字
        num = num.toLocaleString();//转成金额显示模式
        //判断是否有小数
        if(num.indexOf(".")==-1){
            num = num+".00"+"元";
        }else{
            num = num.split(".")[1].length<2?num+"0"+"元":num+"元";
        }
        return num;//返回的是字符串23,245.12保留2位小数
    }else{
        return num = null;
    }
}

add_modal_open()

/**
 * @author chengkexing
 * @other 生成User信息到表单中
 * @param fromId 需要在infor 方法之后调用 用以加载用户信息
 */
function selectUserInformation(formId) {
    var createUserId = $("#"+formId+" input[name^='createUserId']").val();
    var createDeptId = $("#"+formId+" input[name^='deptId']").val();
    new AjaxRequest({
        url:   "/bus/base/findUserOrDeptById/"+createUserId+"&&"+createDeptId,
        type:"get",
        asynctype: false ,
        callBack: function (sessionData) {
            var createUserName = $("#"+formId+" input[name^='createUserName']");
            var createDeptName =$("#"+formId+" input[name^='createDeptName']");
            createUserName.val(sessionData.createUserName);
            createDeptName.val(sessionData.deptName);
        }
    });
}

function getUserName() {
    var userName = "";
    $.ajaxSetup({cache:false});
    $.ajax({
        type: "get",
        async: false,
        contentType: "application/json;charset=UTF-8",
        url: '/sys/user/info',
        dataType:"json",
        success: function (data) {
            userName = data.person_name;
        }
    });
    return userName;
}

/**
 * @author chengkexing
 * @other 页面加载时间控件以及验证方法
 * @param dataArray   需要创建时间控件的id  需要在验证中加载 配合验证使用
 * @param formId   form表单的id     需要在验证中加载 配合验证使用
 */
function loadDate(dataArray,formId){
    if(formId != null && formId != ""){
        for (var i = 0; i <dataArray.length ; i++) {
            new dateLoadObject( dataArray[i] ,formId );
        }
    }else{
        for (var i = 0; i <dataArray.length  ; i++) {
            nomalLoadDate( dataArray[i] );
        }
    }
}

/**
 * @author ckx 2019-1-21
 * 区间时间段的限制 需要单独加载
 * @param dataObj
 *  dataObj = {
 *      beginTime:'beginTimeId',
 *      endTime:'endTimeId'
 *  }
 * @param formId
 */
function loadDateGroup(dateObj,formId){
    if(formId != null && formId != ""){
        //注意顺序 先加载end 再从begin重置end
        new dateLoadObject(dateObj.endTime,formId);
        new dateLoadObject(dateObj.beginTime,formId,dateObj.endTime);
            // new dateLoadObject( dataObj[i] ,formId );
    }else{
        //同样的
        new nomalLoadDate( dateObj.endTime );
        new nomalLoadDate( dateObj.beginTime,dateObj.endTime );
    }
}

function dateLoadObject( dateTimeSource,formId,endTimeId ){
    $( "#"+dateTimeSource ).datetimepicker({
        language:  'zh-CN',
        todayHighlight:true,
        autoclose:true,

        minView:2, //最精准的时间选择为日期0-分 1-时 2-日 3-月
        format: 'yyyy-mm-dd',      /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy,yyyy-mm-dd hh:ii*/
    }).on('hide',function(event) {
        dataTemp = dateTimeSource.split("_");
        $('#'+formId).data('bootstrapValidator')
            .updateStatus(""+dataTemp[0],'NOT_VALIDATED',null)
            .validateField(""+dataTemp[0]);
        if(endTimeId != null && endTimeId != "" ){
            new beginTimeFunction(event,endTimeId,formId);
        }
    });
}

function getOrderNumber(tableId,index) {
    var pageSize = $('#'+tableId).bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
    var pageNumber = $('#'+tableId).bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
    return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
}

function beginTimeFunction(event,endTimeId,formId) {
    event.preventDefault();
    event.stopPropagation();
    var startTime = event.date;

    $("#"+endTimeId).datetimepicker('setStartDate',startTime);

    $("#"+endTimeId).val('');
    if(formId != null && formId != ""){
        var endTime = endTimeId.split("_");
        $('#'+formId).data('bootstrapValidator').updateStatus(""+endTime[0],'NOT_VALIDATED',null).validateField(""+endTime[0]);
    }
}

function nomalLoadDate(beginTimeId ,endTimeId) {
    $( "#"+ beginTimeId).datetimepicker({
        language:  'zh-CN',
        todayHighlight:true,
        autoclose:true,
        clearBtn: true,//清空按钮
        minView:2, //最精准的时间选择为日期0-分 1-时 2-日 3-月
        format: 'yyyy-mm-dd'
    }).on('hide',function(event) {
        if(endTimeId != null && endTimeId != ""){
            beginTimeFunction(event,endTimeId)
        }
    });
}

/**
 * 屏蔽回退事件backspace键
 */

$("input[readOnly]").keydown(function(e) {
    e.preventDefault();
});


/**
 * 字符串转日期
 * @param dateStr
 * @param separator 分隔符
 * @returns {Date}
 */
function strToDate(dateStr,separator){
    if(!separator){
        separator="-";
    }
    var dateArr = dateStr.split(separator);
    var year = parseInt(dateArr[0]);
    var month;
    //处理月份为04这样的情况
    if(dateArr[1].indexOf("0") == 0){
        month = parseInt(dateArr[1].substring(1));
    }else{
        month = parseInt(dateArr[1]);
    }
    var day = parseInt(dateArr[2]);
    var date = new Date(year,month -1,day);
    return date;
}
function dateToString(date){
    var year = date.getFullYear();
    var month =(date.getMonth() + 1).toString();
    var day = (date.getDate()).toString();
    if (month.length == 1) {
        month = "0" + month;
    }
    if (day.length == 1) {
        day = "0" + day;
    }
    var dateTime = year + "-" + month + "-" + day;
    return dateTime;
}

/**
 * 格式化 yyyy-MM-dd hh:mm:ss为 yyyy-MM-dd
 * @param date
 */
function dataFormatFunction(dateStr){
    var date = strToDate(dateStr);
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    if( (month+'').length == 1) month = '0'+month;
    if( (day+'').length == 1) day = '0'+day;
    return year+"-"+month+"-"+day;

}

//判断非空
function isEmpty(obj) {
    if (obj === null) return true;
    if (typeof obj === 'undefined') {
        return true;
    }
    if (typeof obj === 'string') {
        if (obj === "") {
            return true;
        }
        var reg = new RegExp("^([ ]+)|([　]+)$");
        return reg.test(obj);
    }
    return false;
}

function serialize(formid) {
    var oj = JSON.parse($('#'+formid).serializeJson());
    oj.workflowCode=sessionStorage.getItem("workflowCode");
    return JSON.stringify(oj);
}
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

var table;
//菜单列表
function sss(tableId) {
    table=tableId;
    $.ajax({
        url:"/system/tbmenu/menuList",
        success:function (data) {
            $('#'+tableId).treeview({
                data: data,//节点数据
                showBorder: true, //是否在节点周围显示边框
                showCheckbox: true, //是否在节点上显示复选框
                showIcon: true, //是否显示节点图标
                highlightSelected: true,
                levels: 0,
                multiSelect: false, //是否可以同时选择多个节点
                showTags: true,
            });
        }
    })

};
var treenode;
var treeview;
function itemOnclick(target) {
    //找到当前节点id
    // $('#resourceTree').treeview('getChecked');
    var nodeid = $(target).attr('data-nodeid');
    var tree = $('#'+table);
    // console.log(treeview)
    //获取当前节点对象
    treenode = tree.treeview('getNode', nodeid);
    // console.log(treenode.id)
    treeview = $('#'+table).treeview('getChecked');
    // if (treeview.treeviewitem.StatePictureIndex==2){
        console.log(treeview)
    //     toastr_success(treeview)
    // }



}
//获取当前选中id
function selectId(tableId) {
    row = $.map($('#'+tableId).bootstrapTable('getSelections'), function (row) {
        return row;
    });
    var id=[];
    for (let i = 0; i <row.length ; i++) {
        id.push(row[i].Id)
    }

    $.ajax({
        url:'/system/generalMethod/batchId',
        type:'get',
        dataType: 'json',
        traditional : true,
        data:{"ids":id},
    })

}

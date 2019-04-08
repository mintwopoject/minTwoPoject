/**
 * 上传  合同模块
 * @param ctrlName
 * @param UUid
 *
 * /upload  上传文件接口
 * /get_correlation_list 获取表格接口
 * /downloadFile 下载接口
 * /del_file 删除接口
 */
var table = "";
var accessories = "";
var attachmentProxyPath = "";
var uploadType = '';
var quanjutemp = "";

function setAttachmentProxyPath(){
    attachmentProxyPath = "/bus/attachmentTable";
}
function setProcessAttachmentProxyPath(){
    attachmentProxyPath = '/bus/processAttachmentTable';
}
function setPermitAttachmentProxyPath(){
    attachmentProxyPath = '/bus/permitAttachmentTable';
}
function baseInitFileInput(ctrlName,temp,tableId,maxFileCount,FileType) {
    var maxCount = 10;
    var acceptType = "";
    UPLOADALL();
    if (maxFileCount!=null && maxFileCount!="" && maxFileCount!=undefined){
        maxCount=maxFileCount;
        UPLOADONLYALLOW_WORD();
    }
    if(  FileType!=null && FileType!=undefined && FileType!=""  ){
        if( FileType.toUpperCase() == 'IMG' ){
            UPLOADONLYALLOW_IMG();//仅允许上传图片
        }else if (FileType.toUpperCase() == 'WORD') {
            UPLOADONLYALLOW_WORD()//仅允许上传word;
        }
    }

   /* table = tableId;
    accessories = accessoriesUuid;*/
    destroyUploadImg(ctrlName);
    var control = $('#' + ctrlName);
    for(var i=0;i<uploadType.length;i++){
        if(i !=uploadType.length-1){
            acceptType+="."+uploadType[i]+","
        }else {
            acceptType+="."+uploadType[i]
        }
    }
    $(control).attr("accept",acceptType);
    control.fileinput({
        'theme': 'explorer-fas',
        language: 'zh',
        showPreview :true, //是否显示预览
        hideThumbnailContent: true,
        allowedFileExtensions: uploadType,//上传类型
        // elErrorContainer: '#errorBlock',//是否显示上传错误信息
        overwriteInitial: false,
        initialPreviewAsData: true,
        maxFileSize:25600,// 单位为kb，如果为0表示不限制文件大小
        maxFileCount:maxCount, //表示允许同时上传的最大文件个数
        uploadUrl:attachmentProxyPath+'/upload', //上传的地址
        uploadIcon: '<i class="glyphicon glyphicon-upload"></i>',
        browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>;',
        fileActionSettings: {
            indicatorNew:'<i class="glyphicon glyphicon-repeat text-warning" ></i>',
            indicatorSuccess: '<i class="glyphicon glyphicon-ok text-success"></i>',
            indicatorError: '<i class="glyphicon glyphicon-remove text-danger"></i>',
        },
        showCaption: false,
        uploadAsync: false,
        // removeFromPreviewOnError:true,
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: '选择的上传文件个数 <b>({n})</b> 超出最大文件的限制个数 <b>{m}</b>.',
        msgSizeTooLarge: '文件 "{name}" (<b>{size} KB</b>) 超过了允许大小 <b>{maxSize} KB</b>.',
        layoutTemplates:{
            actionZoom: '',
            actionUpload: ''
        },
        uploadExtraData: function(previewId, index) {   //额外参数 返回json数组
            var ppt = JSON.parse(temp);
            return ppt ;
        }
    }).on('filebatchuploadsuccess', function(event, data, previewId, index) {     //上传中
        var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
        $('#upload_div').modal("hide");
        $("#"+tableId).bootstrapTable("refreshOptions",JSON.parse(temp));
        // baseCreateUploadTable(tableId,temp,isDelBase,isDownloadBase,isReadBase,isUpdateBase);
    }).on('filebatchuploaderror', function(event, data, msg) {  //一个文件上传失败
        toastr_error(msg)
    })
    $(control).addClass("file-loading");
}


/**
 * @param tableId           上传展示tableId
 * @param accessoriesUuid   关联id
 * @param isDel             (选填)是否给予删除功能
 * @param isDownload        (选填)是否给予下载功能
 * @param isRead            (选填)是否给予NTKO打开功能(仅 word )
 * @param isUpdate          (选填)是否给予NTKO更新功能(仅 word )
 */
var isDelBase = null;
var isDownloadBase = null;
var isReadBase = null;
var isUpdateBase = null;
function baseCreateUploadTable(tableId,temp,isDel,isDownload,isRead,isUpdate,isExport,isEquals){
    table = tableId;
    // accessories = accessoriesUuid;
    var setColumn = checkColumn(isDownload,isDel,isRead,isUpdate,isExport,isEquals,tableId,temp);
    $('#'+tableId).bootstrapTable('destroy');
    $("#"+tableId).bootstrapTable({
        url: attachmentProxyPath+"/get_correlation_list",                      //请求后台的URL（*）
        method: 'GET', //请求方式（*）
        toolbar: '#deltoolbar',              //工具按钮用哪个容器
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        sortable: false, //是否启用排序
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: pagenumber, //每页的记录行数（*）
        buttonsAlign: "right",
        paginationPreText:'上一页',
        paginationNextText:'下一页',
        uniqueId: "id",
        showRefresh: true, //是否显示刷新按钮.
        clickToSelect: true,//是否启用点击选中行
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        locale:'zh-CN',
        showColumns: true,                  //是否显示所有的列
        pageList: [pagenumber],
        //得到查询的参数
        queryParams: function(params) {

            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            var pppt = JSON.parse(temp);
            var pages=((params.offset / params.limit) + 1)
            pppt.currentpagecount=pages
            return pppt;
        },
        columns: setColumn

    });

}

function checkColumn(isDownload,isDel,isRead,isUpdate,isExport,isEquals,tableId,temp){
    var maxColumn = 2;
    var setColumn =  [{
        title: '序号',
        field: '',
        align: 'center',
        formatter: function (value, row, index) {
            return getOrderNumber(tableId,index)
        }
    }, {
        field: 'file_name',
        title: '文件名称',
        align: 'center'
    }, {
        field: 'file_size',
        title: '文件大小',
        align: 'center',
        formatter :conver,
    }];

    if(isDownload != null && isDownload){
        setColumn [++maxColumn] =
            {
                field:"id",
                title:"下载",
                align: 'center',
                formatter :function (value, row, index) {
                    return  fileOperation(value,row,attachmentProxyPath)
                },
            };
    }
    if(isRead != null && isRead){
        setColumn [++maxColumn] =
            {
                field:"id",
                title:"查看",
                align: 'center',
                formatter :readNtko,
            };
    }
    if(isDel != null && isDel){
        setColumn [++maxColumn] =
            {
                field:"id",
                title:"删除",
                align: 'center',
                // formatter :fileOperationDel
                 formatter :function (value, row, index) {
                   return  fileOperationDel(value,row,tableId,temp)
                 },
            };

    }
    if(isUpdate != null && isUpdate){
        setColumn [++maxColumn] =
            {
                field:"id",
                title:"修改",
                align: 'center',
                formatter :function(value, row, index){
                   return updateNtko(value,row,tableId);
                }
            };
    }
    if(isExport != null && isExport){
        setColumn [++maxColumn] =
            {
                field:"id",
                title:"导出",
                align: 'center',
                formatter :exportNtko,
            };
    }
    if(isEquals != null && isEquals){
        setColumn [++maxColumn] =
            {
                field:"id",
                title:"对比",
                align: 'center',
                formatter :equalsNtko,
            };
    }

    isDownloadBase = isDownload;
    isDelBase = isDel;
    isReadBase = isRead;
    isUpdateBase = isUpdate;

    return setColumn;
}

function conver(value){
    var size = "";
    if( value < 0.1 * 1024 ){ //如果小于0.1KB转化成B
        size = value.toFixed(2) + "B";
    }else if(value < 0.1 * 1024 * 1024 ){//如果小于0.1MB转化成KB
        size = (value / 1024).toFixed(2) + "KB";
    }else if(value < 0.1 * 1024 * 1024 * 1024){ //如果小于0.1GB转化成MB
        size = (value / (1024 * 1024)).toFixed(2) + "MB";
    }else{ //其他转化成GB
        size = (value / (1024 * 1024 * 1024)).toFixed(2) + "GB";
    }

    var sizestr = size + "";
    var len = sizestr.indexOf("\.");
    var dec = sizestr.substr(len + 1, 2);
    if(dec == "00"){//当小数点后为00时 去掉小数部分
        return sizestr.substring(0,len) + sizestr.substr(len + 3,2);
    }
    return sizestr;
}
function destroyUploadImg(ctrlName){
    $("#"+ctrlName).each(function(index,html){
        var upfile = $(html);
        upfile.fileinput("destroy");
    });
}
function fileOperation(value,row,attachmentProxyPath){
    // var row_id = row.id + "";
    // var url = "/bus/attachmentTable/download/"+row.id;
    // var fileName = row.file_name+ "";
    // var fileType = row.file_type+ "";
    // var name = fileName.trim()+"."+fileType.trim();
    var row_id = row.id + "";
    return '<a href="#" class="downLoadAttachment" onclick=downloadFile('+row_id+',"'+attachmentProxyPath+'")>下载</a>';
    // return "<a href="+url+" target='_blank'>下载</a>";
}
function fileOperationDel(value,row,tableId,temp){
    var row_id = row.id + "";
    return '<a href="#" class="deleteAttachment" onclick=delFile('+row_id+',"'+tableId+'",'+temp+')>删除</a>';
}
function readNtko(value,row) {
    if(row.file_type.toUpperCase().indexOf("DOC") != -1){
        return "<a href='#' class='findAttachment'  onclick='lookAt("+row.id+")'>查看</a>";
    }
}
function updateNtko(value,row,tableId) {
    if(row.file_type.toUpperCase().indexOf("DOC") != -1){
        var bd = "'"+row.id+"','"+tableId+"'";
        return "<a href='#' class='updatedAttachment' onclick=updateAt("+bd+")>修改</a>";
    }
}
function equalsNtko(value,row) {
    if(row.file_type.toUpperCase().indexOf("DOC") != -1){
        return "<a href='#' class='equalsAttachment' onclick='equalsAt("+row.id+")'>对比</a>";
    }
}
function exportNtko(value,row) {
    if(row.file_type.toUpperCase().indexOf("DOC") != -1){
        return "<a href='#' class='exportAttachment' onclick='exportAt("+row.id+")'>导出</a>";
    }
}

function getInfo(rowpath){
    window.open(rowpath);
}

/**
 * 2019-1-19 ckx
 * 全局懒加载上传删除模态框 并控制点击事件
 * @type {string}
 */
var OtherfileId = "";
function downloadFile(fileId,Path) {
    new AjaxRequest({
        url:   Path+"/downloadFile/"+fileId,
        type: 'get',
        param: {},
        callBack: function (data) {
            if (data.code==0){
                window.location.href(attachmentProxyPath+"/download/"+fileId)
            }else {
                toastr_error(data.msg)
            }
        }
    });

}
var tempTableId = "";
var tempAccessories = "";
function delFile(fileId,tableId,temp){
    quanjutemp=temp;
    tempTableId = tableId;
    OtherfileId = fileId;

    if (!$('#upLoadTableRealXXXXXXXXdel').length) {
        $("#container").after("<div class='modal fade listen_modal' id='upLoadTableRealXXXXXXXXdel' tabindex='-1' template='dialog' > <div class='modal-dialog' template='document'> <div class='modal-content' style='height: 130px;'> <div class='modal-header'> <h4 class='modal-title' id='delUploadFileAsLabel'>是否继续删除</h4> </div> <div class='modal-footer'> <button type='button' class='btn btn-default' data-dismiss='modal' onclick='del_upload_file()'>关闭</button> <button type='button' class='btn btn-primary' id='delUploadFilebutton_2f4aaddde33c9b93c36fd2503f3d122b'>是</button> </div> </div> </div> </div>");
    }
    $("#upLoadTableRealXXXXXXXXdel").modal('show');
    $("#upLoadTableRealXXXXXXXXdel").on('hide.bs.modal',function(){
        setTimeout(function () {
            $('._body').addClass('modal-open');
        },500)

    });

    $("#delUploadFilebutton_2f4aaddde33c9b93c36fd2503f3d122b").unbind("click",RealDelUploadFile_b6b3087427d93349f491111a8167c0d0);
    $("#delUploadFilebutton_2f4aaddde33c9b93c36fd2503f3d122b").bind("click",RealDelUploadFile_b6b3087427d93349f491111a8167c0d0);
}
function refrenceOnly(){
    // baseCreateUploadTable(tempTableId,JSON.stringify(quanjutemp),isDelBase,isDownloadBase,isReadBase,isUpdateBase);
    $("#"+tempTableId).bootstrapTable('refreshOptions',quanjutemp);
}
function RealDelUploadFile_b6b3087427d93349f491111a8167c0d0() {
    new AjaxRequest({
        url:  attachmentProxyPath+"/del_file/"+OtherfileId,
        param: {},
        buttonid: '',
        callBack:function(data){
            if (data.code == 0) {
                $("#upLoadTableRealXXXXXXXXdel").modal('hide');
                toastr_success(data.msg);
            }
            if (data.code == 500) {
                toastr_error(data.msg);
            }
            refrenceOnly();
        }
    });
}


var ntkoProxyPath = '/business/ntko';
var outData = '';
var a = 1;
function getValue(){
    return data;
}

function lookAt(id) {
    $.post(ntkoProxyPath+"/readOnlyNtko",
        {ntkoIds:id},
        function (data){
            if(data.code==0){
                outData = JSON.stringify(data.ntko);
                window.open("/business.s/ntko/page/ntkoReadOnly.html" );
            }else{
                toastr_error(data.msg);
            }
        }
    )
}

function updateAt(id,tableId) {
    var parents = $("#"+tableId).parents("form");
    if(parents){
        generateNtko4Form( parents.attr('id') );
    }

    $.post(ntkoProxyPath+"/updateNtko",
       {updateJson:"",ntkoIds:id},
        function (data){
            if(data.code==0){
                outData = JSON.stringify(data.ntko);
                window.open("/business.s/ntko/page/updateNtko.html");
            }else{
                toastr_error(data.msg);
            }
        }
    )
}
function equalsAt(id) {
    $.post(ntkoProxyPath+"/equalsNtko",
        { ntkoIds:id },
        function (data){
            if(data.code==0){
                outData = JSON.stringify(data.ntko);
                window.open("/business.s/ntko/page/equalsNtko.html");
            }else{
                toastr_error(data.msg);
            }
        }
    )
}
function exportAt(id) {
    $.post(ntkoProxyPath+"/exportNtko",
        {ntkoIds:id},
        function (data){
            if(data.code==0){
                outData = JSON.stringify(data.ntko);
                window.open("/business.s/ntko/page/exportNtko.html");
            }else{
                toastr_error(data.msg);
            }
        }
    )
}

/**
 * 上传文件枚举类型
 */
//上传所有类型
function UPLOADALL() {
    uploadType = ['jpg', 'png', 'gif','jpeg','mp4','zip','mov','docx','pdf','xlsx','doc'];
    return uploadType;
}
//仅允许图片
function UPLOADONLYALLOW_IMG(){
    uploadType = ['jpg', 'png', 'gif','jpeg'];
    return uploadType;
}
//仅允许word
function UPLOADONLYALLOW_WORD(){
    uploadType = ['docx','doc'];
    return uploadType;
}

/**
 * 筛选form表单下的键值对 预留放入ntko中
 * @param formId
 */
var markValue = [];
function generateNtko4Form(formId){
    var $formgroups = $("#"+formId+" .form-group");
    var arr = new Array();
    $.each($formgroups,function (index, formGrop ) {
        arr[index] = getFormGropLabelAndInputValue( formGrop );
    })
    markValue = arr;
    return arr;
}
//注意此方法并不筛选隐藏域
function getFormGropLabelAndInputValue( formGrop ){
    var $formGrop = $( formGrop );
    //筛选标题
    var label = $formGrop.find("label").html();
    //筛选内容
    var text = $formGrop.find("input[type=text]").val();
    text = formGropIsNull(text);

    var textarea = $formGrop.find("textarea").val();
    textarea = formGropIsNull(textarea);

    var select = $formGrop.find("select").find("option:selected").text();
    select = formGropIsNull(select);

    var body = text + textarea + select ;
    return {markName:label,markValue:body};
}
function formGropIsNull(exp){
    if (typeof(exp) == "undefined")     return '';
    if (isNaN(exp))     return exp+'';
    if (exp==null)      return '';
    return exp;
}
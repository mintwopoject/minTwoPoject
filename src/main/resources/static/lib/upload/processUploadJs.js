/**
 * 流程审批附件
 * @type {string}
 */
var processProxyPath = '/bus/processAttachmentTable';

/**
 * 生成上传模块
 * @param ctrlName          上传div ID
 * @param instanceCode      实例code
 * @param nodeCode          环节code
 * @param tableId           对应列表id
 * @param maxFileCount      最大上传数量
 */
function processInitFileInput(instanceCode,nodeCode,maxFileCount,FileType) {
    var temp={
        instanceCode:instanceCode,
        nodeCode:nodeCode,
        currentpagecount:"1"
    };
    //赋值上传附件路径
    setProcessAttachmentProxyPath();
    //创建表单
    var ppt = JSON.stringify(temp);
    baseInitFileInput("file_zh",ppt,"processtable",maxFileCount,FileType);
}

/**
 * 创建表单
 * @param tableId           对应列表id
 * @param accessoriesUuid   附件唯一标识
 * @param isDel             是否赋予删除功能
 * @param isDownload        是否赋予下载功能
 * @param isRead            是否赋予NTKO查看功能
 * @param isUpdate          是否赋予NTKO修改功能
 */
function processCreateUploadTable(instanceCode,nodeCode,isDel,isDownload){
    var temp={
        instanceCode:instanceCode,
        nodeCode:nodeCode,
        currentpagecount:"1"
    };
    //赋值上传附件路径
    setProcessAttachmentProxyPath();
    //创建表单
    var ppt = JSON.stringify(temp);
    baseCreateUploadTable("processtable",ppt,isDel,isDownload,false,false);
}

/**
 *
 * @param tableID
 * @param instanceCode
 * @param nodeCode
 * @param isDel
 * @param isDownload
 * 用于流程中上传的文件查看
 */

function processCreateUploadFileTable(tableID,instanceCode,nodeCode,isDownload){
    var temp={
        instanceCode:instanceCode,
        nodeCode:nodeCode,
        currentpagecount:"1"
    };
    //赋值上传附件路径
    setProcessAttachmentProxyPath();
    //创建表单
    var ppt = JSON.stringify(temp);
    baseCreateUploadTable(tableID,ppt,false,isDownload,false,false);
}



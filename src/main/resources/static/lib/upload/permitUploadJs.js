/**
 * 证照上传附件
 * @type {string}
 */
var permitProxyPath = '/bus/permitAttachmentTable';

/**
 * 生成上传模块
 * @param ctrlName          上传div ID
 * @param accessoriesUuid   附件唯一标识
 * @param tableId           对应列表id
 * @param maxFileCount      最大上传数量
 */
function permitInitFileInput(ctrlName,accessoriesUuid,tableId,maxFileCount,FileType) {
    var temp={
        accessoriesUuid:accessoriesUuid,
        currentpagecount:"1"
    };
    //赋值上传附件路径
    setPermitAttachmentProxyPath();
    //创建表单
    var ppt = JSON.stringify(temp);
    baseInitFileInput(ctrlName,ppt,tableId,maxFileCount,FileType);
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
function permitCreateUploadTable(tableId,accessoriesUuid,isDel,isDownload,isRead,isUpdate,isExport){
    var temp={
        accessoriesUuid:accessoriesUuid,
        currentpagecount:"1"
    };
    //赋值上传附件路径
    setPermitAttachmentProxyPath();
    //创建表单
    var ppt = JSON.stringify(temp);
    baseCreateUploadTable(tableId,ppt,isDel,isDownload,isRead,isUpdate,isExport);
}



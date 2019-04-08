/**
 * treeid 树结构ID
 * treemenuid 隐藏域存放树结构ID结果
 * url 请求地址
 * type 请求方式
 * params  请求参数
 * openlevels 默认打开树层级
 * textmsg 提示信息
 */
var treedata;
var treeid;
var treemenuid;
var treenode;
var levels;
var treemenuname;
var textmsg;
function MyTree(treeId,treemenuId,treeName,url,params,type,openlevels,text) {
    treeid="#"+treeId;
    treemenuid="#"+treemenuId;
    levels=openlevels;
    treemenuname="#"+treeName;
    textmsg=text;
    new AjaxRequest({
        url:  url,
        type: type,
        param: params,
        callBack: function (data) {
            treedata= "[";
            for(let k in data){
                if(k==data.length-1){
                    treedata+= JSON.stringify(data[k]);
                }else{
                    treedata+= JSON.stringify(data[k])+",";
                }

            }
            treedata+= "]";
            initmenuTree();
            f();
        }
    });

}
function getTree(){
    return treedata;
}
function initmenuTree() {
    var options = {
        bootstrap2 : false,
        showTags : false,
        levels : levels,
        showCheckbox : true,
        showIcon: false,
        data : getTree(),
        onNodeChecked: nodeChecked,
        onNodeUnchecked: nodeUnchecked,
        onNodeSelected : function(event, data) {

        }
    };
    $(treeid).treeview(options);
}

function f() {
    var treeselectid=$("#bmtreeview_edit").treeview('getChecked');
    $(treemenuname).val("");
    $(treemenuid).val("").change();
    for(var i=0;i<treeselectid.length;i++){
        if(i==0 && $(treemenuname).val()==''){
            $(treemenuname).val(treeselectid[i].text+",").change();
            $(treemenuid).val(treeselectid[i].id+",");
        }else{
            $(treemenuname).val($(treemenuname).val()+treeselectid[i].text+",").change();
            $(treemenuid).val($(treemenuid).val()+treeselectid[i].id+",");
        }
    }
    if($(treemenuname).val()==''){$(treemenuname).val(textmsg).change();}
}
function nodeChecked(event, node) {
    f();
}
function nodeUnchecked(event, node) {
    f();
}

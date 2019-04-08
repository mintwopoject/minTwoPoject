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
var oneChecked;
function MyTreeView(treeId,treemenuId,treeName,url,params,type,openlevels,text,isOneChecked) {
    oneChecked = isOneChecked;
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
    var checked = true;
    if(oneChecked){
        checked = false;
    }
    var options = {
        bootstrap2 : false,
        showTags : false,
        levels : levels,
        showCheckbox : checked,
        showIcon: false,
        data : getTree(),
        onNodeChecked: nodeChecked,
        onNodeUnchecked: nodeUnchecked,
        onNodeSelected : function(event, data) {
            if(oneChecked){
                $(treemenuid).val(data.id).change();
                $(treemenuname).val(data.text).change();
            }
        }
    };
    $(treeid).treeview(options);
}

////级联选择
var nodeCheckedSilent = false;

function nodeChecked(event, node) {
    if (nodeCheckedSilent) {
        return;
    }
    nodeCheckedSilent = true;
    checkAllParent(node);
    checkAllSon(node);
    nodeCheckedSilent = false;
    f();
}

var nodeUncheckedSilent = false;

function nodeUnchecked(event, node) {
    if (nodeUncheckedSilent)
        return;
    nodeUncheckedSilent = true;
    uncheckAllParent(node);
    uncheckAllSon(node);
    nodeUncheckedSilent = false;
    f();
}

function f() {
    var treeselectid=$(treeid).treeview('getChecked');
    $(treemenuname).val("").change();
    $(treemenuid).val("");
    for(var i=0;i<treeselectid.length;i++){
        if(i==0 && $(treemenuname).val()==''){
            $(treemenuname).val(treeselectid[i].text+",").change();
            $(treemenuid).val(treeselectid[i].id+",");
        }else{
            $(treemenuname).val($(treemenuname).val()+treeselectid[i].text+",").change();
            $(treemenuid).val($(treemenuid).val()+treeselectid[i].id+",");
        }
    }
    // if($("#gl_bm_edit").val()==''){$("#gl_bm_edit").val("请选择数据权限").change();}
    if($(treemenuname).val()==''){$(treemenuname).val(textmsg).change();/*$(treemenuname).attr('placeholder',textmsg)*/}
}

//选中全部父节点
function checkAllParent(node) {
    $(treeid).treeview('checkNode', node.nodeId, {
        silent: true
    });
    var parentNode = $(treeid).treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    } else {
        checkAllParent(parentNode);
    }
}
//取消全部父节点
function uncheckAllParent(node) {
    $(treeid).treeview('uncheckNode', node.nodeId, {
        silent: true
    });
    var siblings = $(treeid).treeview('getSiblings', node.nodeId);
    var parentNode = $(treeid).treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    }
    var isAllUnchecked = true; //是否全部没选中
    for (var i in siblings) {
        if (siblings[i].state.checked) {
            isAllUnchecked = false;
            break;
        }
    }
    if (isAllUnchecked) {
        uncheckAllParent(parentNode);
    }

}

//级联选中所有子节点
function checkAllSon(node) {
    $(treeid).treeview('checkNode', node.nodeId, {
        silent: true
    });
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            checkAllSon(node.nodes[i]);
        }
    }
}
//级联取消所有子节点
function uncheckAllSon(node) {
    $(treeid).treeview('uncheckNode', node.nodeId, {
        silent: true
    });
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            uncheckAllSon(node.nodes[i]);
        }
    }
}
function itemOnclick(target) {
    //找到当前节点id
    var nodeid = $(target).attr('data-nodeid');
    var tree = $('#menutree');
    //获取当前节点对象
    treenode = tree.treeview('getNode', nodeid);
}

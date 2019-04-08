/*------------------------------------------------------*/
/*                   修改控件的配置信息                                                                    */
/*------------------------------------------------------*/
//64位控件的calssid
var classidx64="A64E3073-2016-4baf-A89D-FFE1FAA10EE0";
//32位控件的classid
var classid="A64E3073-2016-4baf-A89D-FFE1FAA10EC0";
//32位控件包的路径
var codebase="OfficeControl.cab#version=5,0,4,0";
//64位控件包的路径
var codebase64="OfficeControlx64.cab#version=5,0,4,0";
//设置高度
var height= window.screen.availHeight;
//设置宽度
var width=window.screen.availWidth-20;

//买断授权密钥如果不是买断可以不用写
var MakerCaption="天津栢柯信息技术有限公司";
//买断授权密钥如果不是买断可以不用写
var MakerKey="86872A2C9D7562B64A0B02BA633FFCAAB0EEA72F";
//密钥
var ProductCaption="首约科技（北京）有限公司法务暨制度管理系统";
//密钥
var ProductKey="A55601822BF5A4F90F74C3FC442ED3CBF88422F5";
//NTKO主要函数
var ntko;
//服务器路径
var serverPath ="";

/*---------------------------------------------------------------------------------*/
/*             以下内容 请勿修改，否则可能出错  (最好空格换行都不要动)                                                            */
/*---------------------------------------------------------------------------------*/
var userAgent = navigator.userAgent,
    rMsie = /(msie\s|trident.*rv:)([\w.]+)/,
    rFirefox = /(firefox)\/([\w.]+)/,
    rOpera = /(opera).+version\/([\w.]+)/,
    rChrome = /(chrome)\/([\w.]+)/,
    rSafari = /version\/([\w.]+).*(safari)/;
var browser;
var version;
var ua = userAgent.toLowerCase();
function uaMatch(ua) {
    var match = rMsie.exec(ua);
    if (match != null) {
        return { browser : "IE", version : match[2] || "0" };
    }
    var match = rFirefox.exec(ua);
    if (match != null) {
        return { browser : match[1] || "", version : match[2] || "0" };
    }
    var match = rOpera.exec(ua);
    if (match != null) {
        return { browser : match[1] || "", version : match[2] || "0" };
    }
    var match = rChrome.exec(ua);
    if (match != null) {
        return { browser : match[1] || "", version : match[2] || "0" };
    }
    var match = rSafari.exec(ua);
    if (match != null) {
        return { browser : match[2] || "", version : match[1] || "0" };
    }
    if (match != null) {
        return { browser : "", version : "0" };
    }
}
var browserMatch = uaMatch(userAgent.toLowerCase());
if (browserMatch.browser) {
    browser = browserMatch.browser;
    version = browserMatch.version;
}
var loadBD ;
/* 请严格遵循以下格式
{
    //书签信息
    allBookMark:[],
    //是否留痕
    TrackRevisions:false,
    //是否允许本地操作
    Local:false,
    //是否禁用多余功能
    OtherTool:true,
    //功能区显示是否显示
    RibbonBars:false
}
*/

//加载NTKO核心界面
function loadNtko(container,wordPath,body){

    //加载核心控件
    ntkoInit(container,"TANGER_OCX");
    //加载文档
    initNtkoFile("TANGER_OCX",wordPath,body.readOnly);
    loadBD = body;
}

//加载NTKO方法
function ntkoInit(container,objId){
    var container = $("#"+container);
    if (browser=="IE"){
        var objEnd =
            ' <param name="MakerCaption" value="'+MakerCaption+'"> ' +
            ' <param name="MakerKey" value="'+MakerKey+'">   ' +
            ' <param name="ProductCaption" value="'+ProductCaption+'">  ' +
            ' <param name="ProductKey" value="'+ProductKey+'"> ' +
            ' <param name="IsUseUTF8URL" value="-1"> ' +
            ' <param name="IsUseUTF8Data" value="-1">  ' +
            ' <param name="Caption" value="首约科技（北京）有限公司法务暨制度管理系统"> ' +
            ' <SPAN STYLE="color:red">不能装载文档控件。请<a href="/business.s/ntko/officecontrol/NTKO.exe">点击下载控件</a></SPAN> '+
            ' </object>' +
            ' <div id="divRevisedDocument" style="background-color: red;height: 0px"></div> ';
      /*      ' <script type="text/javascript" for="TANGER_OCX" event="AfterOpenFromURL(url,filedocument)">  alert("頁面調用完成！") </script> ';*/
        //alert(window.navigator.platform);
        if(window.navigator.platform=="Win32"){
            var objStr = '<object id="'+objId+'" classid="clsid:'+classid+'"codebase="'+codebase+'" width="'+width+'" height="'+height+'">' ;
            objStr+= objEnd;
            container.html(objStr);
            //$("head").append("<script type='text/javascript' for='TANGER_OCX' event='AfterOpenFromURL(url,filedocument)'> alert('頁面調用完成！') </script>");
        }
        if(window.navigator.platform=="Win64"){
            var objStr = '<object id="'+objId+'" classid="clsid:'+classidx64+'"codebase="'+codebase64+'" width="'+width+'" height="'+height+'">';
            objStr+= objEnd;
            container.html(objStr);
        }

    }else{
        alert("请切换IE浏览器打开!!");
    }
}

//加载NTKO word文档 必须先调用 loadNtko(container) 之后才允许调用
function initNtkoFile(objectId,wordPath,readOnly) {
    var isReadOnly = (readOnly=="yes"?true:false);
    var ntko = document.getElementById(objectId);
    if( ntko ){
        //"http://192.168.1.7:8080/doc/1.doc"
        ntko.BeginOpenFromURL(wordPath ,true ,isReadOnly );
    }else{
        alert("控件未成功加载....");
    }
}

//获取服务器地址
function getURL(){
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： test/test/test.htm  
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName); //获取主机地址，如： http://localhost:8080  
    var localhostPaht = curWwwPath.substring(0, pos); //获取带"/"的项目名，如：/controller
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    var rootPath = localhostPaht + projectName;
    return rootPath;
}

//开始对比
function beginCompare(url){
    //加载核心控件
    ntkoInit("divRevisedDocument","TANGER_OCX_RD");
    //加载文档
    var TANGER_OCX_OBJ_RD = document.getElementById('TANGER_OCX_RD');
    // "http://192.168.1.7:8080/doc/2.doc"
    TANGER_OCX_OBJ_RD.BeginOpenFromURL(url);
}

function getvalue(name) {
    var str = window.location.search;   //location.search是从当前URL的?号开始的字符串 例如：http://www.51job.com/viewthread.jsp?tid=22720 它的search就是?
    if (str.indexOf(name) != -1) {
        var pos_start = str.indexOf(name) + name.length + 1;
        var pos_end = str.indexOf("&", pos_start);
        if (pos_end == -1) {
            return str.substring(pos_start) ;
        } else {
            return str.substring(pos_start,pos_end) ;
        }
    }
}
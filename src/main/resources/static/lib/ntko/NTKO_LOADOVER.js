var ntkoObj = document.getElementById("TANGER_OCX");


loadSetting(loadBD);

//书签数组对象
function loadSetting(body) {
        //填充书签
        getAllCheck(body.allBookMark);
        //设置用户名
        ntkoObj.WebUserName = body.UserName;
        //关闭该功能
        ntkoObj.ShowCommandBar("审阅",false);
        //进入留痕保留状态
        ntkoObj.TrackRevisions = body.TrackRevisions;
        //批注模式是否开启
        ntkoObj.ActiveDocument.TrackRevisions = body.TrackRevisions;
        //是否显示修订文字
        ntkoObj.ActiveDocument.ShowRevisions = body.TrackRevisions;
        //是否隐藏工具栏
        //ntkoObj.IsShowToolMenu = body.OtherTool;
        ntkoObj.IsShowToolMenu = false;
        //是否禁用保存
        ntkoObj.FileSave = !body.Local;
        //标题栏是否显示
        // ntkoObj.Titlebar = body.OtherTool;
        ntkoObj.Titlebar = false;
        //菜单栏是否显示
        //ntkoObj.Menubar = body.OtherTool;
        ntkoObj.Menubar =true;
        //状态栏是否显示
        // ntkoObj. Statusbar = body.OtherTool;
        ntkoObj. Statusbar = false;
        //按钮是否显示
        ntkoObj. Toolbars = body.OtherTool;
        //是否允许承办文件
        ntkoObj.FileNew = body.Local;
        //是否允许打开文件
        ntkoObj.FileOpen  = body.Local;
        //是否显示帮助菜单
        // ntkoObj.IsShowHelpMenu  = body.OtherTool;
        ntkoObj.IsShowHelpMenu  = false;
        //是否允许自定义工具栏
       // ntkoObj.CustomToolBar  = body.OtherTool;
        ntkoObj.CustomToolBar  =false;
        //是否显示OFFICE功能区
        ntkoObj.RibbonBars  = body.OtherTool;

        ntkoObj.SetReadOnly(body.readOnly,"123456");

    //是否显示标题栏
        ntkoObj.Caption = false;

        ntkoObj.MenuBarStyle = 5;
        //动态引入对比
        var action = document.createElement('script');
        action.src = '/lib.s/ntko/NTKOWORDCOMPARE.js';
        action.type = 'text/javascript';
        document.getElementsByTagName('head')[0].appendChild(action);

}

function dQuery(vArg){//参数是变体变量
    this.elements=[];//选择器选择的元素扔到这个数组中
}
/*
$NTKO.prototype = {
    constructor : $NTKO,
    _init_ : function(info) {
        this.name = info.name;
        this.age = info.age;
        this.sex = info.sex;
    },
    sayHello:function(){
        console.log('hello');
    }
}
*/


//同意所有修订
function successRevisions() {
    let neko= document.getElementById("TANGER_OCX");
    neko.ActiveDocument.AcceptAllRevisions();
}
//拒绝所有修订
function notRevisions() {
    let neko= document.getElementById("TANGER_OCX");
    neko.ActiveDocument.RejectAllRevisions();
}

//以 FileName 文件名保存到具体路径
function saveAsURL(serverUrl,FileName){
    let neko= document.getElementById("TANGER_OCX");
    neko.SaveToURL(serverUrl,"editFile", null, FileName+'', null,true);
    switch (neko.StatusCode) {
        case 0:
            neko.ShowTipMessage("文件上传", "保存成功", false);
            neko.ActiveDocument.Saved = true;
            break;
        case 1:
            neko.ShowTipMessage("文件上传", "文件错误", true);
            break;
        case 2:
            neko.ShowTipMessage("文件上传", "网络错误", true);
            break;
        case 3:
            neko.ShowTipMessage("文件上传", "内存错误", true);
            break;
        case 4:
            neko.ShowTipMessage("文件上传", "参数错误", true);
            break;
        case 5:
            neko.ShowTipMessage("文件上传", "读写数据错误", true);
            break;
        case 6:
            neko.ShowTipMessage("文件上传", "读写数据错误", true);
            break;
        case 100:
            neko.ShowTipMessage("文件上传", "其他错误", true);
            break;
    }
}

//替换书签名的内容
function ReplaceBookMark(bookMarkName,bookMarkValue)
{

    if( bookMarkName==null || bookMarkName == '' ){
        return false;
    }
    console.log(bookMarkName);
    try{
        var bkmkObj = ntkoObj.ActiveDocument.BookMarks(bookMarkName);

        if(!bkmkObj) {

        } else {

            var saverange = bkmkObj.Range;
            saverange.Text = bookMarkValue;
            ntkoObj.ActiveDocument.Bookmarks.Add(bookMarkName,saverange);
        }
    }catch (e) {
        return false;
    }

}

//获取所有书签 对象并填入初始化菜单中
function getAllCheck(allBookMark){
    /*allBookMark = [
        {markName:'mark1',markValue:'1111111111'},
        {markName:'mark2',markValue:'2222222222'},
        {markName:'mark3',markValue:'3333333333'},
        {markName:'mark4',markValue:'4444444444'},
        {markName:'mark5',markValue:'5555555555'},
        {markName:'mark6',markValue:'6666666666'},
        {markName:'mark7',markValue:'7777777777'},
        {markName:'mark8',markValue:'8888888888'}
    ];*/

    for (var i = 0; i <allBookMark.length ; i++) {
        var bookMarkName = allBookMark[i].markName;
        var bookMarkValue = allBookMark[i].markValue;
        ReplaceBookMark(bookMarkName,bookMarkValue);
    }
}

function addWaterMark(text)
{
    try
    {
        var ActiveDocument = ntkoObj.ActiveDocument;
        for (i=1;i<=ActiveDocument.Sections.Count ; i++){
            ActiveDocument.Sections(i).Range.Select();
            ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 9; //wdSeekCurrentPageHeader
            var Selection = ActiveDocument.Application.Selection;
            Selection.HeaderFooter.Shapes.AddTextEffect(0, text, "宋体", 1, false, false, 0, 0).Select();
            Selection.ShapeRange.TextEffect.NormalizedHeight = false;
            Selection.ShapeRange.Line.Visible = false;
            Selection.ShapeRange.Fill.Visible = true;
            Selection.ShapeRange.Fill.Solid();
            Selection.ShapeRange.Fill.ForeColor.RGB = 12345;
            Selection.ShapeRange.Fill.Transparency = 0.5;
            Selection.ShapeRange.Rotation = 315;
            Selection.ShapeRange.LockAspectRatio = true;
            Selection.ShapeRange.Height = ActiveDocument.Application.CentimetersToPoints(4.13);
            Selection.ShapeRange.Width = ActiveDocument.Application.CentimetersToPoints(16.52);
            Selection.ShapeRange.WrapFormat.AllowOverlap = true;
            Selection.ShapeRange.WrapFormat.Side = 3;//wdWrapNone
            Selection.ShapeRange.WrapFormat.Type = 3;
            Selection.ShapeRange.RelativeHorizontalPosition = 0;//wdRelativeVerticalPositionMargin
            Selection.ShapeRange.RelativeVerticalPosition = 0; //wdRelativeVerticalPositionMargin
            Selection.ShapeRange.Left = -999995; //wdShapeCenter
            Selection.ShapeRange.Top = -999995; //wdShapeCenter
            ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 0;//wdSeekMainDocument
        }
    }
    catch(err){
        alert("addWaterMark errir:" + err.number + ":" + err.description);
    }
}

//增加图片水印,参数url为绝对url
function addWaterMarkPic(URL)
{
    try
    {
        //TANGER_OCX_OBJ为控件对象
        var ActiveDocument = ntkoObj.ActiveDocument;
        for (i=1;i<=ActiveDocument.Sections.Count ; i++){
            ActiveDocument.Sections(i).Range.Select();
            ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 9; //wdSeekCurrentPageHeader
            var Selection = ActiveDocument.Application.Selection;
            //url只能为绝对url
            Selection.HeaderFooter.Shapes.AddPicture(URL,false,true).Select();
            Selection.ShapeRange.Name = "WordPictureWatermark"+new Date().getTime();
            //亮度
            Selection.ShapeRange.PictureFormat.Brightness =0.8;
            //对比度
            Selection.ShapeRange.PictureFormat.Contrast =0.5;
            //锁定宽高比
            Selection.ShapeRange.LockAspectRatio = true;
            Selection.ShapeRange.Height = 50;
                //ActiveDocument.Application.CentimetersToPoints(4.42);
            Selection.ShapeRange.Width = 50;
                //ActiveDocument.Application.CentimetersToPoints(4.92);
            Selection.ShapeRange.WrapFormat.AllowOverlap = true;
            Selection.ShapeRange.WrapFormat.Side = 1;
            Selection.ShapeRange.WrapFormat.Type = 3;
            Selection.ShapeRange.RelativeHorizontalPosition =0;
            Selection.ShapeRange.RelativeVerticalPosition =0;
            // Selection.ShapeRange.Left=-999995; //wdShapeCenter
            // Selection.ShapeRange.Top=-999995; //wdShapeCenter

            Selection.ShapeRange.Left=380; //wdShapeCenter
            Selection.ShapeRange.Top=0; //wdShapeCenter
            ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 1;//wdSeekMainDocument
        }
    }
    catch(err){
        alert("addWaterMark errir:" + err.number + ":" + err.description);
    }
}

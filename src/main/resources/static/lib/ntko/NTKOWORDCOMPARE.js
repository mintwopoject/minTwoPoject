/*var ntkoObj = document.getElementById("TANGER_OCX");*/
function ShowOriginalDocument()
{
    var ActiveDocument = ntkoObj.ActiveDocument;
    var app = ActiveDocument.Application;
    var view = app.ActiveWindow.View;
    view.ShowRevisionsAndComments = false;
    ntkoObj.GetOfficeVer()==11 ? view.RevisionsView=0 : view.RevisionsView=1;
}
function ShowRevisedDocument()
{
    var ActiveDocument = ntkoObj.ActiveDocument;
    var app = ActiveDocument.Application;
    var view = app.ActiveWindow.View;
    view.ShowRevisionsAndComments = false;
    ntkoObj.GetOfficeVer()==11 ? view.RevisionsView=1 : view.RevisionsView=0;
}
function ShowResultDocument()
{
    var ActiveDocument = ntkoObj.ActiveDocument;
    var app = ActiveDocument.Application;
    var view = app.ActiveWindow.View;
    view.ShowRevisionsAndComments = true;
    view.RevisionsView = 0;
}

//封装的对比方法，一般情况下不必修改
function wordCompare(ReviseAuthor)
{
    var OriginalDocument = document.getElementById("TANGER_OCX").ActiveDocument;
    var RevisedDocument = document.getElementById("TANGER_OCX_RD").ActiveDocument;

    var ActiveDocument = OriginalDocument;
    var app = ActiveDocument.Application;
    app.CompareDocuments(OriginalDocument,RevisedDocument,0,1,true,true,true,true,true,true,true,true,true,true,ReviseAuthor);
    OriginalDocument.TrackRevisions = true;
    OriginalDocument.ShowRevisions = true;
}

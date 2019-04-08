/*
 * type              请求的方式
 * buttonid          锁定按钮id
 * url               发送请求的地址
 * param             发送请求的参数
 * isShowLoader      是否显示loader动画
 * loadtable         是否重新加载表格
 * tableurl          表格请求路径
 * tableid           表格id
 * modalclose        自动关闭modal窗口
 * modalid           modal窗口id
 * dataType          返回JSON数据  默认为JSON格式数据
 * callBack          请求的回调函数
 */
(function(){
    function AjaxRequest(opts){
        this.type         = opts.type || "POST";
        this.buttonid     = opts.buttonid;
        this.buttonidApp  = opts.buttonidApp || false;
        this.url          = opts.url;
        this.param        = opts.param || {};
        this.isShowLoader = opts.isShowLoader || true;
        this.loadtable    = opts.loadtable || true;
        this.tableurl     = opts.tableurl;
        this.tableid      = opts.tableid;
        this.tableparam   = opts.tableparam || {currentpagecount:1};
        this.modalclose   = opts.modalclose  || true;
        this.modalid      = opts.modalid;
        this.dataType     = opts.dataType || "json";
        this.callBack     = opts.callBack;
        this.success      = opts.success;
        this.asynctype    = opts.asynctype ;
        this.tabletype    = opts.tabletype || 'POST';
        this.numberpage   = opts.numberpage || false;
        this.contentType   = opts.contentType || 'application/json;charset=UTF-8' ;
        this.init();
    }

    AjaxRequest.prototype = {
        //初始化
        init: function(){
            this.sendRequest();
        },
        //渲染loader
        showLoader: function(){
            if(this.isShowLoader){
                $("#"+this.buttonid ).button('loading').delay(1000).queue(function() {
                });
                if (this.buttonidApp!=false){
                    $("#"+this.buttonidApp ).button('loading').delay(1000).queue(function() {
                    });
                }

            }
        },
        //隐藏loader
        hideLoader: function(){
            if(this.isShowLoader){
                $("#"+this.buttonid ).button('reset');
                if (this.buttonidApp!=false){
                    $("#"+this.buttonidApp ).button('reset');
                }
            }
        },
        //发送请求
        sendRequest: function(){
            var self = this;
            $.ajaxSetup({ cache: false });
            $.ajax({
                type: self.type,
                url: self.url,
                contentType: this.contentType.toUpperCase()=='DEFAULT'?'application/x-www-form-urlencoded; charset=UTF-8':this.contentType,
                async:self.asynctype,
                data: self.param,
                headers:{"token":sessionStorage.getItem("token")},
                dataType: self.dataType,
                beforeSend: this.showLoader(),
                success: function(res){
                    self.hideLoader();
                    if (res != null) {
                        if(self.callBack){
                            if (Object.prototype.toString.call(self.callBack) === "[object Function]") {
                                self.callBack(res);
                            }else{
                                //console.log("callBack is not a function");
                            }
                        }else{
                            if(res.code=='0'){
                                if(self.modalclose){$('#'+self.modalid).modal('hide');}
                                if(self.loadtable){
                                    $.ajaxSetup({ cache: false });
                                    $.ajax({
                                        type: self.tabletype,
                                        url: self.tableurl,
                                        contentType: "application/json;charset=UTF-8",
                                        headers:{"token":sessionStorage.getItem("token")},
                                        async:self.asynctype,
                                        dataType:"json",
                                        data: JSON.stringify(self.tableparam),
                                        success: function (data) {
                                            $("#"+self.tableid).bootstrapTable('load',data);
                                            if(self.numberpage){
                                                $("#"+self.tableid).bootstrapTable('refreshOptions',{pageNumber:1});
                                            }
                                        }
                                    });
                                }
                                if(res.code=='0'){
                                    toastr_success(res.msg);
                                }
                            }
                            if(res.code=='500'){
                                toastr_error(res.msg);
                            }
                            if(self.success){
                                if (Object.prototype.toString.call(self.success) === "[object Function]") {
                                    self.success(res);
                                }else{
                                    //console.log("callBack is not a function");
                                }
                            }
                        }
                    }else {toastr_error('系统错误');}
                },complete : function(XMLHttpRequest, textStatus) {
                    var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                    if (sessionstatus == "timeout") {
                        toastr_error('登录过期，请刷新重新登录');
                    }
                },error:function (XMLHttpRequest, textStatus, errorThrown){
                    self.hideLoader();
                    // toastr_error(JSON.stringify(XMLHttpRequest))
                    toastr_error('无操作权限!');
                }
        });
        }
    };

    window.AjaxRequest = AjaxRequest;
})();

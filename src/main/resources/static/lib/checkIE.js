function isIE()
{
    return;
    if(!!window.ActiveXObject || "ActiveXObject" in window){

    } else{
        window.location.replace("/static/error/error_001.html");
    }

}
$(document).ready(function(){
    isIE();
});
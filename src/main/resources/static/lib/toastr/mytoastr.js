$(function(){   
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-center",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
});
function toastr_success(str){
    toastr.success(str);
}
function toastr_error(str){
    toastr.error(str);
}
function toastr_info(str){
    toastr.info(str);
}
function toastr_warning(str){
    toastr.warning(str);
}
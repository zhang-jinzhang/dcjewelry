$(document).ready(function () {
    $("#upPic").change(function () {
        var url = getObjectURL(this.files[0]);
        $("#authBg").hide();
        $("#authImg").css("display","block");
        $("#authImg").show().attr("src", url);
    });

    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
});
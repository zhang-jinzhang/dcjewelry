$(document).ready(function () {
    $(".redio1").click(function () {
        $('#sex').val($(this).attr('data-sex'))
        $(this).addClass("active").siblings().removeClass("active")
    })

    $("#headPic").change(function() {
      var url = getObjectURL(this.files[0]);
      $("#headImg").attr("src", url);
    });

    function getObjectURL(file) {
      var url = null ;
      if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
      } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
      } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
      }
      return url;
    }

    $('#form-user').ajaxform({
        "complete": function (result) {
            result = JSON.parse(result);
            if (result.code !== 0) {
                alert(result.message);
            } else {
                alert('保存成功')
                jewelry.redirect("/wx/user/edit.html")
            }
        }
    });
});

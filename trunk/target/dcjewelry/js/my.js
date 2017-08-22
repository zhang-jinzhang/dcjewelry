$(document).ready(function() {
    $("#bgFile").on("change", function () {
        var files = FileAPI.getFiles($(this));
        // 上传图片
        FileAPI.upload({
            url: '/wx/file/upload',
            files: {file: files},
            data : {type:'bgUrl'},
            imageTransform: {
                maxWidth: 1500,
                quality: 0.86
            },
            complete: function (err, xhr) {
                var result = JSON.parse(xhr.responseText);
                if (result.code === 0) {
                    var url = result.data;
                    $("#headBg").css({
                      "background-image" : "url(" + url + ")"
                    });
                }
            }
        });
    });
});

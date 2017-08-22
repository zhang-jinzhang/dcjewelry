$(document).ready(function () {
    $('#save-but').click(function () {
        var article =Number( $('#article').val());
        var liked = Number( $('#liked').val());
        var comment =Number(  $('#comment').val());
        var collection =Number(  $('#collection').val());
        $.post('/admin/score/save', {
            id: 1,
            article: article ,
            liked: liked,
            comment: comment,
            collection: collection
        }, function (result) {
            if (result.code == 0) {
                jewelry.notify("保存成功","success");
            }
        });
    });
})
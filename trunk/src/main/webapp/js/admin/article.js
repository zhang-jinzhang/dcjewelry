/**
 * Created by lingh on 2017/4/6.
 */
function editLinkRender(data, type, row) {
    return '<a href="/admin/article/view.html?id=' + data + '">查看</a><a href="/admin/article/create.html?id=' + data + '">编辑</a>'
}

var editor;
$(document).ready(function () {
    if (typeof UE !== 'undefined') {
        editor = UE.getEditor('content', {
            toolbars: [
                ['fullscreen', 'source', 'undo', 'redo', 'insertimage'],
                ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'justifyleft', 'justifyright', 'justifycenter', 'justifyjustify']
            ],
            autoHeightEnabled: true,
            autoFloatEnabled: true,
            initialFrameHeight: 300,
        });

        var wmPos = $('#wmPosHidden').val();
        $('input[value="' + wmPos + '"]').attr('checked', true);
        $('#topCategorySel').change(function () {
            var pid = $(this).val();
            $('#categorySel').find('option').hide();
            $('#categorySel').find('option[value="0"]').show();
            $('#categorySel').find('option[data-pid="' + pid + '"]').show();
            $('#categorySel').val('0');
        });

        $('#save-btn').click(function () {
            var pics = getPics(),
                title = $('#title').val(),
                id = $('#id').val(),
                pid = $('#topCategorySel').val(),
                cid = $('#categorySel').val(),
                wmId = $('#wmId').val(),
                wmPos = $('input[name="wmPos"]:checked').val(),
                point = $('#point').val(),
                content = editor.getContentTxt(),
                functionType = $('#functionType').val();
            if(pics !== "undefined" && pics !== '') {
                var type = 1;
            }
            if (title === "") {
                jewelry.notify("请输入标题", 'warning');
                return false;
            }
            if (pid === '0') {
                jewelry.notify("请选择频道", 'warning');
                return false;
            }
            if (cid === '0') {
                jewelry.notify("请选择分类", 'warning');
                return false;
            }
            if (content === '') {
                jewelry.notify("请输入资讯内容", 'warning');
                return false;
            }
            $.post('/wx/article/save', {
                id: id,
                title: title,
                pics: pics,
                pid: pid,
                cid: cid,
                wmId: wmId,
                wmPos: wmPos,
                point: point,
                content: content,
                functionType: functionType,
                type: type
            }, function (result) {
                if (result.code !== 0) {
                    jewelry.notify(result.message, 'error');
                    return;
                }
                jewelry.notify('保存成功', function () {
                    jewelry.redirect('/admin/article/index.html');
                });
            });
        });

        $('#topCategorySel').val($('#topCategorySel').attr('data-id'));
        $('#categorySel').val($('#categorySel').attr('data-id'));
    }

    $('button[data-tag]').click(function (e) {
        var tag = $(this).attr('data-tag'),
            ids = jewelry.dtSelectedIds('dt');
        e.preventDefault()
        if (ids.length === 0) {
                jewelry.notify('请选择要' + tag + '的资讯', 'warning')
            return
        }else if( ids.length > 5 ) {
            jewelry.notify('最多只能选择5条资讯', 'warning')
            return
        }
        $.post('/admin/article/tag', {ids: ids, tag: tag}, function (result) {
            if (result.code !== 0) {
                jewelry.notify(result.message, 'error')
                return
            }
            jewelry.notify(tag + '成功', function (result) {
                jewelry.reloadDatatable('dt')
            })
        }, 'json')
    })
    $('button[data-untag]').click(function (e) {
        var tag = $(this).attr('data-untag'),
            ids = jewelry.dtSelectedIds('dt');
        e.preventDefault()
        if (ids.length === 0) {
            jewelry.notify('请选择要取消' + untag + '的资讯', 'warning')
            return
        }
        $.post('/admin/article/untag', {ids: ids, tag: tag}, function (result) {
            if (result.code !== 0) {
                jewelry.notify(result.message, 'error')
                return
            }
            jewelry.notify('取消' + tag + '成功', function () {
                jewelry.reloadDatatable('dt')
            })
        }, 'json')
    })
    $('#reindex').click(function (e) {
        e.preventDefault();
        $.post('/wx/article/reindex', function (result) {
            if (result.code !== 0) {
                jewelry.notify(result.message, 'error')
                return
            }
            jewelry.notify('重建索引成功')
        })
    })
    $('button.btn-del').click(function () {
        jewelry.deleteSelected('dt', '/admin/article/delete');
    })

    $('#search-btn').click(function () {
            var nickname = $('#nickname').val();
            var actitle = $('#actitle').val();
            jewelry.reloadDatatable('dt', {nickname: nickname, actitle: actitle});
    })

    $('button.btn-del-checkbox').click(function () {
            var userIds=getIds();
            if(userIds == "" || userIds==undefined){
                alert("请选择资讯");
                return
            }
            $.post('/admin/article/checkboxDelete', {ids: userIds}, function (result) {
                if (result.code !== 0) {
                    jewelry.notify(result.message, 'error');
                    return
                }
                jewelry.notify('删除成功', function () {
                    jewelry.reloadDatatable('dt')
                })
            })
    })
});

function getPics() {
    var pics = "",
        imgArr = $("#ueditor_0").contents().find("img"),
        i = 0;
    for (i = 0; i < imgArr.length; i++) {
        pics += imgArr[i].src;
        pics += ",";
    }
    if (pics.length > 0) {
        pics = pics.substring(0, pics.length - 1);
    }
    return pics;
}
function checkall() {
    var ischecked = document.getElementById("checkall").checked;
    if(ischecked) {
        checkallbox();
    }else {
        discheckallbox();
    }
}
function checkallbox() {
    var boxarray = document.getElementsByName("box");
    for(var i = 0; i < boxarray.length; i++) {
        boxarray[i].checked = true;
     }
}
function discheckallbox() {
    var boxarray = document.getElementsByName("box");
    for(var i = 0; i < boxarray.length; i++) {
        boxarray[i].checked = false;
     }
}
function checkonebox() {
   if(isallchecked()) {
        document.getElementById("checkall").checked = true;
   }
   if(isalldischecked()) {
        document.getElementById("checkall").checked = false;
    }
 }
 function isallchecked() {
      var boxarray = document.getElementsByName("box");
       for(var i = 0; i < boxarray.length; i++) {
            if(!boxarray[i].checked) {
               return false;
            }
       }
       return true;
 }
 function isalldischecked() {
       var boxarray = document.getElementsByName("box");
       for(var i = 0; i < boxarray.length; i++) {
           if(boxarray[i].checked) {
                return false;
           }
       }
      return true;
 }
function getallcheckedvalue() {
    var boxvalues = "";
    var boxarray = document.getElementsByName("box");
    for(var i = 0; i < boxarray.length; i++) {
        if(boxarray[i].checked) {
            var boxvalue = boxarray[i].value;
            if(boxvalues == "") {
                boxvalues = boxvalue;
            }else {
                boxvalues = boxvalues + "," + boxvalue;
            }
         }
     }
     return boxvalues;
}
function getIds() {
    var boxvalues = getallcheckedvalue();
    var boxvaluesArray = boxvalues.split(",");
    var ids = "";
    for(var i = 0; i < boxvaluesArray.length; i++) {
        var boxvalue = boxvaluesArray[i];
        var boxvalueArray = boxvalue.split("|");
        var id = boxvalueArray[0];
        var username = boxvalueArray[1];
        if(ids == "") {
            ids = id;
        }else {
            ids = ids + "," + id;
        }
    }
    return ids;
}
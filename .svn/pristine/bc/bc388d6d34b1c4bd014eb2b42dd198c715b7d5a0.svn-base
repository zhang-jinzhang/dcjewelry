function levelRender(data, type, row) {
    return "LV" + data;
}

function merchantCidRender(data, type, row) {
    var merchant = row.merchant;
    if (merchant) {
        return categoryMap[merchant.cid].name;
    } else {
        return "";
    }
}

function sexRender(data, type, row) {
    if (data === 1) {
        return "先生";
    } else if (data === 2) {
        return "女士";
    } else {
        return "未知";
    }
}

function editLinkRender(data, type, row) {
    return '<a href="/admin/user/view.html?id=' + data + '">查看</a>';
}

function merchantAreaRender(data, type, row) {
    var merchant = row.merchant;
    if (merchant) {
        return merchant.area;
    } else {
        return '';
    }
}

function merchantNameRender(data, type, row) {
    var merchant = row.merchant;
    if (merchant) {
        return merchant.name;
    } else {
        return '';
    }
}

function merchantEmailRender(data, type, row) {
    var merchant = row.merchant;
    if (merchant) {
        return merchant.email;
    } else {
        return '';
    }
}

$(document).ready(function () {
    $('#search-btn').click(function () {
        var keyword = $('#keyword').val();
        var type = $('#typeSel').val();
        jewelry.reloadDatatable('dt', {keyword: keyword, type: type});
    });
    $('#pass-btn').click(function () {
        review(1);
    });
    $('#fail-btn').click(function () {
        review(2);
    });
});

function review(status) {
    var uid = $('#user-info').attr('data-uid');
    $.post('/admin/user/review', {uid: uid, status: status}, function (result) {
        if (result.code != 0) {
            jewelry.notify(result.message, 'error');
        } else {
            jewelry.notify('操作成功', function () {
                window.location.reload(true);
            });
        }
    });
}
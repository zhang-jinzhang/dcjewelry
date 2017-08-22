$(document).ready(function () {
    $("#subnav li").on('click', function () {
        $(this).addClass("active").siblings().removeClass("active");
        $("#main dl").eq($(this).index()).show().siblings("dl").hide();
    });

    $('a.add-category').click(function (e) {
        var $a = $(this),
            subscribe = $a.attr('data-subscribe'),
            tip = '添加成功！';
        if (subscribe === 'false') {
            tip = '删除成功'
        }
        $.post('/wx/article/category/subscribe', {
            id: $a.attr('data-id'),
            subscribe: subscribe
        }, function (result) {
            if (result.code !== 0) {
                alert(result.message)
                return
            }

            dachao.alert("资讯频道添加", tip, function () {
                window.location.href = '/wx/article/category.html'
            });
        })
    })

    $("#search").focus(function () {
        $("#searchPd").show();
    });

    $("#search").blur(function () {
        setTimeout(function () {
            $("#searchPd").hide();
        }, 200);
    });
    $('#search').on('keyup', function(e) {
        e.preventDefault()
        if (e.which === 13) {
            var q = $('#search').val();
            if (q === '') {
                alert('请输入搜索内容')
                return
            }
            window.location.href = '/wx/article/index.html?keyword=' + q;
        }
    })
});

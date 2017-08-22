$(document).ready(function () {
    $("#userBtn").on("click", function () {
        $("body").addClass("user-left-show");
    });
    $("#shade").on("click", function () {
        $("body").removeClass("user-left-show");
    });

    $("#zxNav").width($("#zxNav li").eq(0).width() * $("#zxNav li").length)

    $('a.follow').live('click', function (e) {
        var $a = $(this);
        e.preventDefault()
        jewelry.follow($a.attr('data-uid'), function () {
            jewelry.reload()
        })
    })

    $('i.collect-icon').live("click", function (e) {
        var $i = $(this),
            $span = $i.parent('div').find('span.favorite-count');
        e.preventDefault()
        e.stopPropagation();
        if ($i.hasClass('active')) {
            return
        }
        jewelry.favorite($(this).attr("data-id"), function () {
            $i.addClass('active')
            var count = $span.html()
            count++
            $span.html(count)
        })
    })
    $('i.zx-zan-icon').live("click", function (e) {
        var $i = $(this),
            $span = $i.parent('div').find('span.like-count');
        e.preventDefault()
        e.stopPropagation();
        if ($i.hasClass('active')) {
            return
        }
        jewelry.like($(this).attr("data-id"), function () {
            $i.addClass('active')
            var count = $span.html()
            count++
            $span.html(count)
        })
    })

    $(window).scroll(function (event) {
        // 滚到底部加载更多旧资讯
        if ($("body").height() - $(this).scrollTop() <= $(this).height()) {
            $.post('/wx/article/search', {
                id: oldest.id,
                updateTime: oldest.updateTime,
                old: true,
                cid: cid
            }, function (result) {
                if (result.code !== 0) {
                    return;
                }
                var htmlPage = result.data;
                if (htmlPage.recordCount > 0) {
                    oldest.id = htmlPage.record.id;
                    oldest.updateTime = htmlPage.record.createTime;
                    $('div.card-list').append(htmlPage.html);
                }
            })
        } else if ($(this).scrollTop() <= 0 && !$("body").hasClass("newHint-show")) {
            $("body").addClass("newHint-show");
            $.post('/wx/article/search', {
                id: newest.id,
                updateTime: newest.updateTime,
                old: false,
                cid: cid
            }, function (result) {
                if (result.code !== 0) {
                    $("body").removeClass("newHint-show");
                    return;
                }
                var htmlPage = result.data;
                if (htmlPage.recordCount > 0) {
                    newest.id = htmlPage.record.id;
                    newest.updateTime = htmlPage.record.createTime;
                    $('div.card-list').prepend(htmlPage.html);
                    $('#newHint').html('又发现了' + htmlPage.recordCount + '条新内容');
                } else {
                    $('#newHint').html('0条新内容');
                }
                $('#newHint').show();
                setTimeout(function () {
                    $("body").removeClass("newHint-show")
                    $('#newHint').hide();
                }, 2000);
            })
        }
    });

    $('a.search').on('click', function (e) {
        var q = $('input.search').val();
        e.preventDefault()
        if (q === '') {
            alert('请输入搜索内容')
            return
        }
        window.location.href = '/wx/article/index.html?keyword=' + q + '&cid=' + cid;
    });
    $('div.hd-add-icon').click(function () {
        window.location.href = '/wx/article/category.html'
    })
    $('div.hd-add-icon-img').click(function () {
            window.location.href = '/wx/article/imgCategory.html'
        })
});

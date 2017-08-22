$(document).ready(function () {
    $("a.user-btn").on("click", function () {
        $("body").addClass("user-left-show");
    });
    $("#shade").on("click", function () {
        $("body").removeClass("user-left-show");
    });
    if($("#swiper img").length > 1){
      var mySwiper = new Swiper('#swiper', {
        autoplay: 5000,//可选选项，自动滑动
        loop: true,
        pagination: '#pagination',
        autoplayDisableOnInteraction: false
      });
    }
    var searchTop = $("#search").offset().top;
    if ($(window).scrollTop() >= $("#search").offset().top) {
        $("body").addClass("search-top");
    }
    $(window).scroll(function (event) {
        if ($(this).scrollTop() >= searchTop) {
            $("body").addClass("search-top");
        } else {
            $("body").removeClass("search-top");
        }
    });

    // 消息滚动
    var area = document.getElementById('newsList'),
        liHeight = area.offsetHeight;
    area.innerHTML += area.innerHTML;
    area.scrollTop = 0;
    var timer;

    function startMove() {
        timer = setInterval(scrollUp, 20);
        area.scrollTop++;
    }

    function scrollUp() {
        if (area.scrollTop % liHeight === 0) {
            clearInterval(timer);
            setTimeout(startMove, 2000);
        }
        else {
            area.scrollTop++;
            if (area.scrollTop >= area.scrollHeight / 2) {
                area.scrollTop = 0;
            }
        }
    }

    setTimeout(startMove, 2000);

    $('i.dianzan-icon').live("click", function (e) {
        var $i = $(this),
            $span = $i.parent('a').find('span.like-count');
        e.preventDefault()
        e.stopPropagation()
        if ($i.hasClass('active')) {
            return
        }
        jewelry.like($i.attr('data-id'), function () {
            $i.addClass('active')
            var count = parseInt($span.html())
            count++
            $span.html(count)
        })
    })

    $('i.collect-icon').live("click", function (e) {
        var $i = $(this);
        e.preventDefault()
        e.stopPropagation();
        if ($i.hasClass('active')) {
            return
        }
        jewelry.favorite($(this).attr("data-id"), function () {
            $i.addClass('active')
        })
    })

    $('i.share-icon').live("click", function (e) {
        var $i = $(this);
        e.preventDefault()
        e.stopPropagation();
        if ($i.hasClass('active')) {
            return
        }
        jewelry.wxshare($i.attr('data-id'), $i.attr('data-title'), '/wx/article/' + $i.attr('data-id') + '.html', $i.attr('data-pic'))
    })

    var refreshing = false;
    $(window).scroll(function (event) {
        if ($("body").height() - $(this).scrollTop() <= $(this).height()) {
            if (refreshing) {
                return;
            }
            refreshing = true;
            $.post('/wx/search', {id: oldest.id, updateTime: oldest.updateTime, old: true}, function (result) {
                refreshing = false;
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
        }
    });

    $('a.search').on('click', function (e) {
        e.preventDefault()
        search()
    })
    $('input.search').on('keyup', function (e) {
        e.preventDefault()
        if (e.which === 13) {
            search()
        }
    })
    function search() {
        var q = $('input.search').val();
        if (q === '') {
            alert('请输入搜索内容')
            return
        }
        window.location.href = '/wx/article/index.html?keyword=' + q;
    }
    $('a.follow').live('click', function (e) {
            var $a = $(this);
            e.preventDefault()
            jewelry.follow($a.attr('data-uid'), function () {
                jewelry.reload()
            })
        })
    //手指下拉刷新页面
    var oldY = lastY = newY = 0;
		var choujiebao = $(".DropRefresh");
		var oStart = function (e) {
					oldY = e.touches[0].clientY;
		}
		var oMove = function(e) {
			newY = e.touches[0].clientY;
			lastY = newY - oldY;
			if(lastY >= 80) {
				window.location.reload();
			}
		}
		$(window).scroll(function() {　　
			var scrollTop = $(this).scrollTop();　　
			if(scrollTop<=0;) {
				document.addEventListener('touchstart',oStart,false)
				document.addEventListener('touchmove', oMove,false)
			} else {
				document.removeEventListener("touchstart",oStart,false);
				document.removeEventListener("touchmove",oMove,false);
			}
		});

	
});

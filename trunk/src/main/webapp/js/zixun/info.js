$(document).ready(function () {
    //提交info中评论的详情
    $(".commitComment").click(function(){
        var content=$("#comment-value").val();
        var articleId=$(".commitComment").attr("id");
        $.post('/wx/comment/save',{content:content,aid:articleId},function(){
            window.location.reload();
        });
    });
    $("#clonePic").on("click", function () {
      if($("#downloadPic").hasClass("pic-show")){
        $("#downloadPic").removeClass("pic-show");
        return;
      }
        $("#downloadPic").hide();
    });
    var imgArr = [];
    if($("#picList")[0]){
      for(var i = 0; i < $("img.article").length; i++) {
        $("#swiperWrapper").append('<li class="swiper-slide"><img src="' + $("img.article").eq(i).attr("src") + '" alt=""></li>')
      }
    }else {
      for(var i = 0; i < $("div.article img").length; i++) {
        imgArr.push($("div.article img").eq(i).attr("src"));
        $("#swiperWrapper").append('<li class="swiper-slide"><img src="' + $("div.article img").eq(i).attr("src") + '" alt=""></li>')
      }
    }
    //判断资讯是否已收藏 更改收藏按钮样式
    $('.btn-favorite').attr('data-favorite') =="true" ? $('.btn-favorite').addClass('bg-red-btn-check') : $('.btn-favorite').removeClass('bg-red-btn-check');
    //判断是否已关注  更改按钮样式
    $('.btn-follow').attr('data-follow') =="true" ? $('.btn-follow').addClass('follow-checked') : $('.btn-follow').removeClass('follow-checked');

    var mySwiper = Swiper('#swiper', {
      loop: true,
      pagination: '#pagination',
      autoplayDisableOnInteraction: false,
      observer: true,
      observeParents:true
    });

    $("div.article img").click(function(e) {
      e.preventDefault();
      e.stopPropagation();
      var index = 0;
      for (var i = 0; i < imgArr.length; i++) {
        if($(this).attr("src") == imgArr[i]){
          index = i;
        }
      }
      $('#downloadPic').show();
      $('a.download').show();
      mySwiper.slideTo(index + 1);
    });

    $("#picList li").click(function(e) {
      e.preventDefault();
      e.stopPropagation();
      var index = $(this).index();
      $('#downloadPic').show();
      $('a.download').show();
      mySwiper.slideTo(index + 1);
    });

    $('i.collect-icon').click(function (e) {
        e.preventDefault()
        e.stopPropagation()
        jewelry.favorite($(this).attr('data-id'), function () {
            jewelry.reload()
        })
    })

    $('i.zx-zan-icon').click(function(e) {
        e.preventDefault()
        e.stopPropagation()
        jewelry.like($(this).attr('data-id'), function () {
            jewelry.reload()
        })
    })

    $('i.share-icon').click(function (e) {
        var $i = $(this)
        e.preventDefault()
        e.stopPropagation()
        jewelry.loading();
        $.post('/wx/article/shareQrcode', {id: $i.attr('data-id')}, function (result) {
            jewelry.loadingDone()
            if (result.code !== 0) {
                alert(result.message)
                return
            }
            alert('生成二维码成功')
        })
    })

    $('a.btn-message').click(function (e) {
        var $a = $(this);
        e.preventDefault();
        jewelry.sendMessage($a.attr('data-uid'), $a.attr('data-nickname'))
    })

    $('a.btn-follow').click(function (e) {
        var $a = $(this)
        e.preventDefault()
        jewelry.follow($a.attr('data-uid'), function () {
            jewelry.reload()
        })
    })

    $('a.btn-favorite').click(function (e) {
        var $a = $(this)
        e.preventDefault()
        jewelry.favorite($a.attr('data-id'), function () {
            jewelry.reload()
        })
    })


    $('a.download').click(function (e) {
        var pic = $('#swiperWrapper li').eq(mySwiper.realIndex + 1).find("img").attr('src'),
            id = parseInt($('div.article').attr('data-id'));
        e.preventDefault();
        $.post('/wx/pic/getDownloadLink', {articleId: id, pic: pic}, function (result) {
            if (result.code !== 0) {
                alert(result.message)
                return
            }
            $("#downloadPic").addClass('pic-show');
            $('img.preview').attr('src', '/wx/pic/view-src?picId=' + result.data + "&t=" + new Date().getTime());
        })
    });

    $(".video-zhezhao").click(function(e) {
          e.preventDefault();
          e.stopPropagation();
          $('#downloadVideo video').attr("src",$('.articleVd').attr('src'));
          $('#downloadVideo video').attr("poster",$('.articleVd').attr('poster'));
          $('#downloadVideo').show();
          $('a.downloadVideo').show();
          return false;
    });

    $("a.cloneVideo").click(function(){
         $('#downloadVideo').hide();
    });

    $("a.downloadVideo").click(function(e){
         id = parseInt($('div.article').attr('data-id'));
            e.preventDefault();
         $.post('/wx/pic/getDownloadVideo', {articleId: id}, function (result) {
             if (result.code !== 0) {
                 alert(result.message)
                 return
             }
             $("#show-video").click(function(){
			$("#show-video").trigger("play");
			$("#show-video").css({"object-fit":"fill","-webkit-object-fit":"fill","width":"100%","height":"100%"})
		})
             $(".downloadVideo").remove();
             $(".mengban").remove();
         })
    });
});

var type;
$(document).ready(function () {
    $("#upload").uploadImg({
        'maxLen': 9
    });

    var wmPos = 0;
    $("#watermarkPst a").on("click", function () {
        $("#watermarkPst a").removeClass("active");
        $(this).addClass("active");
        wmPos = $(this).attr('data-pos');
    });

    $("#radio label").on("click", function () {
        var $label = $(this)
        $label.addClass("active").siblings().removeClass("active");
        $('#wmId').val($label.attr('data-wm'))
    });

    $('#topCategorySel').val($('#topCategorySel').attr('data-id'));
    $('#categorySel').val($('#categorySel').attr('data-id'));
    $('#topCategorySel').change(function () {
        var pid = $(this).val();
        $('#categorySel').find('option').hide();
        $('#categorySel').find('option[value="0"]').show();
        $('#categorySel').find('option[data-pid="' + pid + '"]').show();
        $('#categorySel').val('0');
    });
    $('a.btn-create').click(function (e) {
        var id = $('#id').val(),
            functionType = $('#releaseCategorySel').val(),
            pid = $('#topCategorySel').val(),
            cid = $('#categorySel').val(),
            title = $('#title').val(),
            content = $('#content').val(),
            pics = $('#upload').attr('data-pics'),
            wmId = $('#wmId').val(),
            point = $('#point').val(),
            videoUrl = '';
            videoImg = '';
        e.preventDefault();
        if(type==2){
            videoUrl=pics.replace('jpg','mp4');
            videoImg=pics;
        }
        if (functionType === '99'){
            alert('请选择发布类别')
            return
        }
        if (pid === '0') {
            alert('请选择频道')
            return
        }
        if (cid === '0') {
            alert('请选择分类')
            return
        }
        if (title === '') {
            alert('请输入标题')
            return
        }
        if (content === '') {
            alert('请输入正文')
            return
        }
        if (typeof pics === 'undefined' && typeof type==='undefined' ) {
            pics='';
        }
    /**   if (((typeof pics === 'undefined') || pics === '')&&type===1) {
            alert('请上传资讯图片')
            return
        }
        if (type===2&&videoUrl==='') {
            alert('请上传资讯视频')
            return
        }
    */
        if (wmId === '0'&&type===1) {
            alert('请选择水印')
            return
        }
        jewelry.loading()
        $.post('/wx/article/save', {
            id: id,
            functionType:functionType,
            pid: pid,
            cid: cid,
            title: title,
            content: content,
            pics: pics,
            point: point,
            wmId: wmId,
            wmPos: wmPos,
            videoUrl:videoUrl,
            videoImg:videoImg,
            type: type
        }, function (result) {
            jewelry.loadingDone()
            if (result.code !== 0) {
                alert(result.message)
                return
            }
            dachao.alert('资讯服务', '资讯发布成功', function () {
                window.location.href = '/wx/article/my.html'
            })
        })
    });
    $('.fileImg').click(function(e){
        type=1;
        $('.mask').hide();
        $('.radio-text').show();
        $('.watermark-text').show();
        $('.imgWarn').show();
        $('#upload').html('<label class="up-add up-plus"><img id="img"  src="" alt=""><input class="up-file" type="file" accept="image/*"><span class="up-del"></span></label>');
        $('.up-file').click();
    });
    $('.fileVideo').click(function(e){
        type=2;
        $('.mask').hide();
        $('.videoWarn').show();
        $('#upload').html('<label class="up-add up-plus"><img id="img" src="" alt=""><input class="up-file" type="file" accept="video/*"><span class="up-del"></span></label>');
        $('.up-file').click();
    });
});

function chooseLoad(){
    $('.mask').show();
}

function funcType(v){
     $.post('/wx/article/queryCategory', {
        type: v,
    }, function (result) {
        refreshing = false;
        if (result.code !== 0) {
            return;
        }
        var data = result.data;
        var html="<option value='99' selected>请选择</option>";
        for(var ca in data){
            html+="<option value='"+data[ca].id+"'>"+data[ca].name+"</option>";
        }
        $('#topCategorySel').html(html);
    }, 'json')
}


(function ($) {
    $.fn.uploadImg = function (options) {
        var that = this;
        var dft = {
            addLen: 9,
            maxLen: "infinite"
        };

        var ops = $.extend(dft, options);

        this.each(function () {
            var _this = $(this),
                pics = [];
            // 初始化内容
            function init() {
                var len = pics.length;
                if (len < ops.maxLen) {
                    len = len + 1;
                }
                for (var i = 0; i < len; i++) {
                    addItem(_this);
                }
            };
            function initDataLen() {
                var dataPics = _this.data("pics")
                if (!dataPics) {
                    return;
                }
                var pics1 = dataPics.split(",");

                for (var i = 0; i < pics1.length; i++) {
                    if (pics1[i]) {
                        pics.push(pics1[i]);
                    }
                }
            }

            function initData() {
                var labels = _this.find("label");
                for (var i = 0; i < pics.length; i++) {
                    var label = labels[i];
                    if (!label) {
                        break;
                    }
                    var imgObjArray = $(label).find("img");
                    if (imgObjArray) {
                        $(label).addClass("up-succeed");
                        $(label).removeClass("up-plus")
                        var imgObj = imgObjArray[0];
                        $(imgObj).attr("src", siteConf.resHost + pics[i]);
                    }
                }
            }

            initDataLen();
            init();
            initData();
            function addItem(_this) {
                if (ops.imgWidth) {
                    _this.append('<label style="width:' + ops.imgWidth + 'px; height:' + ops.imgWidth + 'px;" class="up-add up-plus"><img src="" alt=""><input class="up-file" type="file"><span class="up-del"></span></label>');

                } else {
                    _this.append('<label class="up-add up-plus"><img src="" alt=""><input class="up-file" type="file"><span class="up-del"></span></label>');
                }
            }

            _this.on("change", ".up-file", function () {
                var files = FileAPI.getFiles($(this)),
                    oImg = $(this).parent();
                oImg.addClass("up-succeed");
                oImg.removeClass("up-plus");
                var pos = $(oImg).index();
                var data = {};
                if (ops.data) {
                    data = ops.data;
                }
                // 上传图片
                FileAPI.upload({
                    url: '/wx/file/upload',
                    files: {file: files},
                    data : data,
                    imageTransform: {
                        maxWidth: 1500,
                        quality: 0.86
                    },
                    complete: function (err, xhr) {
                        var result = JSON.parse(xhr.responseText);
                        if (result.code === 0) {
                            var url = result.data;
                            pics[pos] = url;
                            _this.attr("data-pics", pics);
                            oImg.find("img").attr("src", siteConf.resHost + url);
                            if (typeof ops.callback === 'function') {
                                ops.callback(result.data)
                            }
                            var labels = _this.find("label");
                            if (_this.find("label").length < ops.addLen&&$(labels.last()).hasClass("up-succeed")) {
                                addItem(_this);
                            }
                        }
                    }
                });
            });

            _this.on("click", ".up-del", function (e) {
                e.preventDefault();
                pics.splice($(this).parent().index(), 1);
                $(this).parent().remove();
                _this.attr("data-pics", pics);
                if (ops.valId) {
                    $("#" + ops.valId).val(pics);
                }
                var labels = _this.find("label");
                if (labels.length < ops.addLen && $(labels.last()).hasClass("up-succeed")) {
                    addItem(_this);
                }
            });
        });
    };
})(jQuery);

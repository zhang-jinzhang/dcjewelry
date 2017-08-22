$(document).ready(function() {
  setFontSize();
  function setFontSize() {
    var winWidth = $(window).width();
    var size = winWidth / 10;
    document.documentElement.style.fontSize = size + 'px';
  }
  $(window).resize(function(){
    setFontSize();
  });
});


var dachao = (function (dc) {
  dc.alert = function (tit, message, afterClose) {
      var alertHtml = '<div class="shade"></div><div class="popup popup-in bg-white"><div class="popup-tit">'+ tit + '<a class="popup-clone" id="alertClone" href="javascript:;"></a></div><div class="popup-text">' + message + '</div><a class="btn" href="javascript:;" id="alertBtn">确定</a></div>';
      if ($("body").hasClass("popup")) {
          return false;
      }
      $("body").append(alertHtml);
      $(".popup").show().addClass("popup-in");
      $("body").on("click", "#alertBtn", function () {
          $("#alertBtn").unbind("click");
          $("#alertClone").unbind("click");
          $(".popup").removeClass("popup-in");
          $(".shade, .popup").remove();
          if (afterClose) {
              afterClose();
          }
      });
      $("#alertClone").on("click", function () {
          $("#alertBtn").unbind("click");
          $("#alertClone").unbind("click");
          $(".popup").removeClass("popup-in");
          $(".shade, .popup").remove();
      });
  };
  dc.textareaAlert = function (tit, afterClose) {
      var alertHtml = '<div class="shade"></div><div class="popup popup-in bg-white"><div class="popup-tit">'+'<div class="title">'+ tit + '</div>'+'<a class="popup-clone" id="alertClone" href="javascript:;"></a></div><div class="popup-textarea"><textarea class="popup-input" name="" id="" cols="30" rows="10"></textarea></div><div class="addpicBtn"> <input type="hidden" id="imgVal" value=""><input class="add-img" type="file" accept="image/*" style="display:none"></div><a class="btn" href="javascript:;" id="alertBtn">确定</a></div>';
      if ($("body").hasClass("popup")) {
          return false;
      }
      $("body").append(alertHtml);
      $(".popup").show().addClass("popup-in");
      $("body").on("click", "#alertBtn", function () {
          var message = $('div.popup-textarea textarea').val()
          if (message === '') {
              return
          }
          var img=$("#imgVal").val();
          $("#alertBtn").unbind("click");
          $("#alertClone").unbind("click");
          $(".popup").removeClass("popup-in");
          $(".shade, .popup").remove();
          if (afterClose) {
              afterClose(message,img);
          }
      });
      $("#alertClone").on("click", function () {
          $("#alertBtn").unbind("click");
          $("#alertClone").unbind("click");
          $(".popup").removeClass("popup-in");
          $(".shade, .popup").remove();
      });
      $('.addpicBtn').click(function (e){
          e.preventDefault();
          $('.add-img').click();
      });
      $('.add-img').change(function (e){
           e.preventDefault();
           var files = FileAPI.getFiles(this);
           FileAPI.upload({
              url: '/wx/file/upload',
              files: {file: files},
              data : {type:1},
              imageTransform: {
                  maxWidth: 3500,
                  quality: 0.86
              },
              complete: function (err, xhr) {
                  var result = JSON.parse(xhr.responseText);
                  if (result.code === 0) {
                      var url = result.data;
                      $('.addpicBtn').css({"background":"url("+url+") no-repeat","background-size": "100% 100%"});
                      $("#imgVal").val(url);
                  }
                  if (result.code === -1) {
                      alert(result.message);
                  }
              }
          });
      });

  };
  return dc;
})(window.dachao || {});

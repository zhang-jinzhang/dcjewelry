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
      var alertHtml = '<div class="shade"></div><div class="popup popup-in bg-white"><div class="popup-tit">'+ tit + '<a class="popup-clone" id="alertClone" href="javascript:;"></a></div><div class="popup-textarea"><textarea name="" id="" cols="30" rows="10"></textarea></div><a class="btn" href="javascript:;" id="alertBtn">私信</a></div>';
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

  return dc;
})(window.dachao || {});

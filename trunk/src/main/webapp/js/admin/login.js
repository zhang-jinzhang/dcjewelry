/**
 * Created by lingh on 2017/4/5.
 */
$(document).ready(function () {
    $('#login-btn').click(function (e) {
        e.preventDefault();
        login();
    });

    $('#username').keyup(function (e) {
        if (e.which == 13) {
            login();
        }
    });

    $('#password').keyup(function (e) {
        if (e.which == 13) {
            login();
        }
    });
});

function login() {
    var username = $('#username').val(),
        password = $('#password').val();
    if (username === '') {
        jewelry.notify('请输入用户名', 'warning');
        $('#username').focus();
        return false;
    }
    if (password === '') {
        jewelry.notify('请输入密码', 'warning');
        $('#password').focus();
        return false;
    }
    $.post('/admin/login', {username: username, password: password}, function (result) {
        if (result.code !== 0) {
            jewelry.notify(result.message, 'error');
            return;
        }

        jewelry.notify('登录成功', function () {
            jewelry.redirect('/admin/index.html');
        });
    });
}
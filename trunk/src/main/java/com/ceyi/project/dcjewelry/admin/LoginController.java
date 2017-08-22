package com.ceyi.project.dcjewelry.admin;

import com.ceyi.project.dcjewelry.admin.user.AdminUser;
import com.ceyi.project.dcjewelry.admin.user.AdminUserService;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/4/3.
 */
@Controller("AdminLoginController")
@RequestMapping("/admin/")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Inject
    private AdminUserService adminUserService;
    @Inject
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (!StringUtils.hasText(username)) {
            return new Result(Result.CODE_ERROR, "请输入用户名");
        }
        if (!StringUtils.hasText(password)) {
            return new Result(Result.CODE_ERROR, "请输入密码");
        }
        AdminUser adminUser = adminUserService.login(username, password);
        if (adminUser == null) {
            return new Result(Result.CODE_ERROR, "用户名或密码错误");
        }


        Mvc.context().sSet(AdminUser.SESSION_KEY, adminUser);
        adminUser = Mvc.context().sGet(AdminUser.SESSION_KEY);
        logger.debug("登录成功 adminUser:{} sid:{}", adminUser, Mvc.context().session().getId());
        Mvc.context().sSet(User.SESSION_KEY, userService.get(User.ADMIN_UID));
        return new Result();
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        Mvc.context().sRemove(AdminUser.SESSION_KEY);
        return "redirect:/admin/login.html";
    }
}

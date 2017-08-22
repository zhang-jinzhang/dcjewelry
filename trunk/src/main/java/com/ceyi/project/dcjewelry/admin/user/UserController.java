package com.ceyi.project.dcjewelry.admin.user;

import com.ceyi.project.dcjewelry.admin.DatatablesPage;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/4/5.
 */
@Controller("AdminUserController")
@RequestMapping("/admin/user/")
public class UserController {
    @Inject
    private UserService userService;

    @RequestMapping("index")
    public String index() {
        return "admin/user/index";
    }

    @RequestMapping("query")
    @ResponseBody
    public DatatablesPage<User> query(UserSearchVo userSearchVo) {
        Page page = new Page();
        if (userSearchVo.getLength() > 0) {
            page.setPageSize(userSearchVo.getLength());
        }
        if (userSearchVo.getStart() > 0) {
            page.setPageNumber((userSearchVo.getStart() + page.getPageSize() - 1) / page.getPageSize() + 1);
        }
        userService.queryUserJoinMerchant(page, userSearchVo.getKeyword(), userSearchVo.getType());
        return new DatatablesPage<>(page);
    }

    @RequestMapping("view")
    public ModelAndView view(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("admin/user/view");
        mav.addObject("user", userService.get(id));
        return mav;
    }

    @RequestMapping("review")
    @ResponseBody
    public Result review(@RequestParam("uid") int uid, @RequestParam("status") int status) {
        if (userService.updateStatus(uid, status) == 1) {
            return new Result();
        } else {
            return new Result(Result.CODE_ERROR, "审核同行失败");
        }
    }
}

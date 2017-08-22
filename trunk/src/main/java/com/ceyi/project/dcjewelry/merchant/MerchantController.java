package com.ceyi.project.dcjewelry.merchant;

import com.ceyi.project.dcjewelry.admin.DatatablesPage;
import com.ceyi.project.dcjewelry.admin.user.UserSearchVo;
import com.ceyi.project.dcjewelry.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/5/7.
 */
@Controller
public class MerchantController {
    @Inject
    private MerchantService merchantService;

    @RequestMapping("/admin/merchant/index")
    public String index() {
        return "admin/merchant/index";
    }

    @RequestMapping("/admin/merchant/query")
    @ResponseBody
    public DatatablesPage<User> query(UserSearchVo userSearchVo) {
        Page page = new Page();
        if (userSearchVo.getLength() > 0) {
            page.setPageSize(userSearchVo.getLength());
        }
        if (userSearchVo.getStart() > 0) {
            page.setPageNumber((userSearchVo.getStart() + page.getPageSize() - 1) / page.getPageSize() + 1);
        }
        page.setData(merchantService.query());
        return new DatatablesPage<>(page);
    }

    @ResponseBody
    @RequestMapping("/admin/merchant/update")
    public Result update(@RequestParam("uid") int uid, @RequestParam("status") int status) {
        if (status < 0 || status > 2) {
            status = 2;
        }
        merchantService.updateStatus(uid, status);
        return new Result();
    }
}

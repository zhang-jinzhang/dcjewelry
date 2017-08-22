package com.ceyi.project.dcjewelry.admin;

import com.ceyi.project.dcjewelry.article.ArticleService;
import com.ceyi.project.dcjewelry.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/4/3.
 */
@Controller("AdminIndexController")
@RequestMapping("/admin/")
public class IndexController {
    @Inject
    private UserService userService;
    @Inject
    private ArticleService articleService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("admin/index");
        mav.addObject("userCount", userService.count());
        mav.addObject("downloadCount", articleService.countDownload());
        mav.addObject("adViewCount", articleService.countAdView());
        return mav;
    }
}

package com.ceyi.project.dcjewelry.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/5/9.
 */
@Controller
public class HotSearchConfigController {
    @Inject
    private SearchService searchService;

    @RequestMapping("/admin/search/hot")
    public ModelAndView hot() {
        ModelAndView mav = new ModelAndView("admin/search/hot");
        mav.addObject("lines", searchService.getAsString());
        return mav;
    }

    @ResponseBody
    @RequestMapping("/admin/search/config")
    public Result config(@RequestParam("lines") String lines) {
        searchService.save(lines);
        return new Result();
    }
}

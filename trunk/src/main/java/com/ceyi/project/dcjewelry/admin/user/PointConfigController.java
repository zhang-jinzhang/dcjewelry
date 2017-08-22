package com.ceyi.project.dcjewelry.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/5/7.
 */
@Controller
@RequestMapping("/admin/point/")
public class PointConfigController {
    @Inject
    private PointConfigService pointConfigService;

    @RequestMapping("config")
    public ModelAndView config() {
        ModelAndView mav = new ModelAndView("admin/point/config");
        mav.addObject("pointConfig", pointConfigService.get());
        return mav;
    }

    @ResponseBody
    @RequestMapping("save")
    public Result save(PointConfig pointConfig) {
        pointConfigService.save(pointConfig);
        return new Result();
    }
}

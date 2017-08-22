package com.ceyi.project.dcjewelry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pw.wecode.project.framework.web.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lingh on 2017/5/6.
 */
@Controller
public class LogController {
    private Logger logger = LoggerFactory.getLogger(LogController.class);

    @RequestMapping("/log")
    public Result log(@RequestParam("level") String level, @RequestParam("log") String log) {
        if ("debug".equals(level)) {
            logger.debug(log);
        } else if ("info".equals(level)) {
            logger.info(log);
        } else if ("warn".equals(level)) {
            logger.warn(log);
        } else {
            logger.error(log);
        }
        return new Result();
    }

    @RequestMapping("/MP_verify_PHlam6RGCrDG69Zw.txt")
    public void weixinValidate(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write("PHlam6RGCrDG69Zw");
            response.getWriter().flush();
        } catch (Exception e) {

        }
    }
}

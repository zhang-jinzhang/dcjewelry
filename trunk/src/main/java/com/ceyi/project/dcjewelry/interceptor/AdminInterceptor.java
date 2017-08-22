package com.ceyi.project.dcjewelry.interceptor;

import com.ceyi.project.dcjewelry.admin.user.AdminUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.interceptor.BaseInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lingh on 2017/4/5.
 */
@Component
@Order(3)
public class AdminInterceptor extends BaseInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    @Override
    protected boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (!uri.startsWith("/admin/") || uri.startsWith("/admin/login")) {
            return true;
        }
        AdminUser adminUser = Mvc.context().sGet(AdminUser.SESSION_KEY);
        logger.debug("admin:{}", adminUser);
        if (adminUser == null) {
            Mvc.context().response().setStatus(302);
            Mvc.context().response().sendRedirect("/admin/login.html");
            return false;
        }
        return true;
    }
}

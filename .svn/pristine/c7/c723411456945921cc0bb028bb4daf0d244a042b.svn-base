package com.ceyi.project.dcjewelry.interceptor;

import com.ceyi.project.dcjewelry.article.ArticleCategory;
import com.ceyi.project.dcjewelry.article.ArticleCategoryService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.interceptor.BaseInterceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by lingh on 2017/3/31.
 */
@Component
@Order(1)
public class RequestInitInterceptor extends BaseInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestInitInterceptor.class);

    @Value("${host}")
    private String host;
    @Value("${resHost}")
    private String resHost;
    @Value("${env}")
    private String env;
    @Value("${version}")
    private String version;
    @Inject
    private ArticleCategoryService articleCategoryService;
    @Inject
    private Gson gson;

    @Override
    protected boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Mvc.init(request, response);
        logger.debug("收到请求: ip:{} url:{} sid:{}", Mvc.getClientIp(), Mvc.getRequestURL(), Mvc.context().session().getId());
        if ("dev".equals(env)) {
            request.setAttribute("v", new Date().getTime());
        } else {
            request.setAttribute("v", version);
        }
        request.setAttribute("host", host);
        request.setAttribute("resHost", resHost);
        Map<Integer, ArticleCategory> articleCategoryMap = articleCategoryService.getAllAsMap();
        request.setAttribute("categoryMap", articleCategoryMap);
        request.setAttribute("categoryMapJson", gson.toJson(articleCategoryMap));
        //request.setAttribute("categoryMap", articleCategoryService.getTopCategory());

        return true;
    }

    @Override
    protected void afterRender(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("请求处理结束:{} sid:{}", Mvc.getRequestURL(), Mvc.context().session().getId());
    }
}

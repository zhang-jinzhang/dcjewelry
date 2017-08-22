package com.ceyi.project.dcjewelry.interceptor;

import com.ceyi.project.dcjewelry.article.ArticleCommentService;
import com.ceyi.project.dcjewelry.config.Config;
import com.ceyi.project.dcjewelry.config.ConfigService;
import com.ceyi.project.dcjewelry.follow.UserFollowService;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.utils.UserAgentUtils;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;
import pw.wecode.project.framework.web.interceptor.BaseInterceptor;
import pw.wecode.project.framework.weixin.AuthedUserInfoResponse;
import pw.wecode.project.framework.weixin.WeixinService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

/**
 * Created by lingh on 2017/3/31.
 */
@Component
@Order(2)
public class WeixinInterceptor extends BaseInterceptor implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(WeixinInterceptor.class);
    public static final String SESSION_WEIXIN_AUTHING = "weixin_authing";
    @Value("host")
    private String host;
    @Value("${env}")
    private String env;
    @Inject
    private WeixinService weixinService;
    @Inject
    private UserService userService;
    @Inject
    private ConfigService configService;
    @Inject
    private UserFollowService userFollowService;
    @Inject
    private ArticleCommentService articleCommentService;
    @Inject
    private Gson gson;
    private long pv;
    private Date pvSaveTime = new Date();

    @Override
    public void afterPropertiesSet() throws Exception {
        Config config = configService.get("pv");
        if (config != null) {
            this.pv = gson.fromJson(config.getValue(), int.class);
        }
        logger.debug("网站当前pv:{}", this.pv);
    }

    @Override
    protected boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        incPv(request);

        User user = Mvc.context().sGet(User.SESSION_KEY);
        if (!UserAgentUtils.isWeixin(Mvc.getUserAgent())) {
            logger.debug("不是微信, {}", Mvc.getUserAgent());
            if ("dev".equals(env)) {
                if (user == null) {
                    String uid = request.getParameter("uid");
                    if (!StringUtils.hasText(uid)) {
                        uid = "1";
                    }
                    user = userService.get(Integer.valueOf(uid));
                    user.setFavoriteCount(articleCommentService.countFavorite(user.getId()));
                    user.setFollowCount(userFollowService.countByUser(user.getId()));
                    Mvc.context().sSet(User.SESSION_KEY, user);
                }
                request.setAttribute("user", user);
            }
            request.setAttribute("isWeixin", false);
            return true;
        }

        request.setAttribute("isWeixin", true);
        request.setAttribute("jsApi", weixinService.getJsApi(Mvc.getRequestURL()));

        logger.debug("user: {}", user);
        if (user == null) {
            String loginToken = Mvc.context().getCookieValue(User.COOKIE_TOKEN);
            if (StringUtils.hasText(loginToken)) {
                user = userService.loginByToken(loginToken);
            }
            if (user == null) {
                Boolean authing = Mvc.context().sGet(SESSION_WEIXIN_AUTHING);
                if (authing != null && authing) {
                    Mvc.context().sSet(SESSION_WEIXIN_AUTHING, false);
                    String code = Mvc.getParameter("code");
                    String state = Mvc.getParameter("state");
                    logger.debug("微信授权：code{}，state:{}", code, state);
                    if (StringUtils.hasText(code) && Objects.equals("STATE", state)) {
                        Result<AuthedUserInfoResponse> result = weixinService.authInfo(code);
                        if (result.isSuccess()) {
                            AuthedUserInfoResponse authedUserInfoResponse = result.getData();
                            logger.debug("微信授权结果: {}", authedUserInfoResponse);
                            user = userService.getUserByOpenid(authedUserInfoResponse.getOpenid());
                            if (user != null) {
                                // 登录
                                logger.debug("登录, openid:{}", user.getOpenid());
                                user.setFavoriteCount(articleCommentService.countFavorite(user.getId()));
                                user.setFollowCount(userFollowService.countByUser(user.getId()));
                            } else {
                                // 注册
                                user = userService.register(authedUserInfoResponse);
                                logger.debug("新用户注册, user:{}", user);
                            }
                            Mvc.context().addCookie(User.COOKIE_TOKEN, user.getLoginToken(), 7 * 24 * 3600);
                        }
                    }
                } else {
                    Mvc.context().sSet(SESSION_WEIXIN_AUTHING, true);
                    String redirectUrl = weixinService.auth(Mvc.getRequestURL(), null);
                    logger.debug("进行微信授权, redirectUrl: {}", redirectUrl);
                    response.sendRedirect(redirectUrl);
                    return false;
                }
            } else {
                logger.debug("通过cookie登录成功, user: {}", user);
                user.setFavoriteCount(articleCommentService.countFavorite(user.getId()));
                user.setFollowCount(userFollowService.countByUser(user.getId()));
            }
        }

        if (user != null) {
            logger.debug("session user: {}", user);
            Mvc.context().sSet(User.SESSION_KEY, user);
            request.setAttribute("user", user);
        }

        return true;
    }

    private void incPv(HttpServletRequest request) {
        Date now = new Date();
        synchronized (this) {
            this.pv++;
        }

        if (now.getTime() - pvSaveTime.getTime() > 5 * 60 * 1000) {
            synchronized (this) {
                if (now.getTime() - pvSaveTime.getTime() > 5 * 60 * 1000) {
                    configService.save(new Config("pv", this.pv + ""));
                    pvSaveTime = now;
                }
            }
        }

        request.setAttribute("pv", this.pv);
    }
}

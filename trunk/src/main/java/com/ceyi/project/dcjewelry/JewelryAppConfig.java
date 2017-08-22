package com.ceyi.project.dcjewelry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pw.wecode.project.framework.web.AppConfig;
import pw.wecode.project.framework.weixin.*;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/3/29.
 */
@Configuration
@ComponentScan(basePackageClasses = {JewelryAppConfig.class})
public class JewelryAppConfig extends AppConfig {
    @Inject
    private Environment environment;

    @Bean
    public WeixinRepository weixinRepository() {
        return new WeixinServletContextRepository();
    }

    @Bean
    public WeixinService weixinService() {
        WeixinConfig weixinConfig = new WeixinConfig();
        weixinConfig.setAppId(environment.getRequiredProperty("weixin.appId"));
        weixinConfig.setAppSecret(environment.getRequiredProperty("weixin.appSecret"));
        weixinConfig.setToken(environment.getRequiredProperty("weixin.token"));
        weixinConfig.setHost(environment.getRequiredProperty("host"));
        weixinConfig.setImgHost(environment.getRequiredProperty("resHost"));
        WeixinService weixinService = new WeixinService(weixinConfig);
        weixinService.setWeixinRepository(weixinRepository());
        weixinService.setWeixinReplyProvider(new DefaultWeixinReplyProvider(environment.getRequiredProperty("weixin.welcome")));
        weixinService.setGson(gson());
        weixinService.setHttpApi(httpApi());
        return weixinService;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
}

package com.ceyi.project.dcjewelry;

import com.ceyi.project.dcjewelry.picture.PictureService;
import com.ceyi.project.dcjewelry.upload.UploadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pw.wecode.project.framework.utils.FileUtils;
import pw.wecode.project.framework.web.WebConfig;
import pw.wecode.project.framework.web.controller.WeixinController;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by lingh on 2017/3/29.
 */
@Configuration
@EnableWebMvc
@EnableSpringHttpSession
@ComponentScan(basePackageClasses = {JewelryWebConfig.class, WeixinController.class})
public class JewelryWebConfig extends WebConfig {
    @Inject
    private Environment environment;
    @Inject
    private WebApplicationContext webApplicationContext;

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

    @Bean
    public UploadService uploadService() {
        UploadService uploadService = new UploadService();
        uploadService.setPath(environment.getProperty("upload.path"));
        uploadService.setDir(environment.getProperty("upload.dir"));
        return uploadService;
    }

    @Bean
    public PictureService pictureService() {
        String path = environment.getProperty("upload.path");
        if (!StringUtils.hasText(path)) {
            path = webApplicationContext.getServletContext().getRealPath("/");
        }
        String dir = environment.getProperty("upload.dir");
        if (!StringUtils.hasText(dir)) {
            dir = "/upload/";
        }
        return new PictureService(path, dir);
    }

    @Bean
    public ConfigHolder configHolder() {
        ConfigHolder configHolder = new ConfigHolder();
        configHolder.setWebroot(webApplicationContext.getServletContext().getRealPath("/"));
        if (!FileUtils.exists(configHolder.getWebroot() + "/upload/")) {
            new File(configHolder.getWebroot() + "/upload/").mkdirs();
        }
        return configHolder;
    }
}

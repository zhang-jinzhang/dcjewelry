package com.ceyi.project.dcjewelry.admin.user;

import com.ceyi.project.dcjewelry.config.Config;
import com.ceyi.project.dcjewelry.config.ConfigService;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/5/7.
 */
@Component
public class PointConfigService {
    private static final String CONFIG_POINT = "积分配置";
    @Inject
    private ConfigService configService;
    @Inject
    private Gson gson;

    public PointConfig get() {
        Config config = configService.get(CONFIG_POINT);
        if (config == null) {
            return new PointConfig();
        }
        return gson.fromJson(config.getValue(), PointConfig.class);
    }

    public void save(PointConfig pointConfig) {
        configService.save(new Config(CONFIG_POINT, gson.toJson(pointConfig)));
    }
}

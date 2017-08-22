package com.ceyi.project.dcjewelry.user;

import com.ceyi.project.dcjewelry.config.Config;
import com.ceyi.project.dcjewelry.config.ConfigService;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.utils.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lingh on 2017/5/6.
 */
@Component
public class UserCategoryService {
    private static final String CONFIG_CATEGORY = "分类订阅:";
    @Inject
    private ConfigService configService;

    public Set<Integer> getSubscribed(int uid) {
        Set<Integer> idSet = new HashSet<>();
        Config config = configService.get(CONFIG_CATEGORY + uid);
        if (config == null) {
            return idSet;
        }

        List<String> ids = StringUtils.split(config.getValue(), ",");
        for (String id : ids) {
            idSet.add(Integer.valueOf(id));
        }

        return idSet;
    }

    public void subscribe(int uid, int id, boolean subscribe) {
        List<String> ids;
        Config config = configService.get(CONFIG_CATEGORY + uid);
        if (config != null) {
            ids = StringUtils.split(config.getValue(), ",");
            int index = ids.indexOf(id + "");
            if (subscribe) {
                if (index == -1) {
                    ids.add(id + "");
                }
            } else {
                if (index != -1) {
                    ids.remove(index);
                }
            }
        } else {
            config = new Config();
            config.setName(CONFIG_CATEGORY + uid);
            ids = new ArrayList<>();
            ids.add(id + "");
        }
        config.setValue(StringUtils.join(ids));
        configService.save(config);
    }
}

package com.ceyi.project.dcjewelry.config;

import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/4/16.
 */
@Component
public class ConfigService {
    @Inject
    private Dao dao;

    public void delete(final String name) {
        dao.delete("DELETE FROM config WHERE name = :name", new ParamMap<>("name", name));
    }

    public void save(Config config) {
        this.delete(config.getName());
        dao.create(config);
    }

    public Config get(String name) {
        return dao.get(Config.class, new ParamMap<String, Object>("name", name));
    }
}

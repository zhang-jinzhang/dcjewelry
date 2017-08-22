package com.ceyi.project.dcjewelry.banner;

import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lingh on 2017/4/7.
 */
@Component
public class BannerService {
    @Inject
    private Dao dao;

    public void create(Banner banner) {
        dao.create(banner);
    }

    public Banner get(int id) {
        return dao.get(Banner.class, id);
    }

    public void save(Banner banner) {
        if (banner.getId() > 0) {
            dao.update(banner);
        } else {
            dao.create(banner);
        }
    }

    public List<Banner> getAll() {
        return dao.query(Banner.class, "SELECT * FROM banner ORDER BY pos, updateTime DESC", new ParamMap<String, Object>());
    }

    public List<Banner> top() {
        return dao.query(Banner.class, "SELECT * FROM banner WHERE status = :status ORDER BY pos, updateTime DESC LIMIT 0,8", new ParamMap<String, Object>("status", Banner.STATUS_ON));
    }

    public void delete(int[] ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            dao.delete("DELETE FROM banner WHERE id = :id", new ParamMap<String, Object>("id", id));
        }
    }
}

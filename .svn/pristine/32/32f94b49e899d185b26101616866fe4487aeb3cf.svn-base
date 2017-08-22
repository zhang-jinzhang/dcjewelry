package com.ceyi.project.dcjewelry.index;

import com.ceyi.project.dcjewelry.article.Article;
import com.ceyi.project.dcjewelry.merchant.Merchant;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserHis;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by dz on 2017/8/10.
 */
@Component
public class IndexService {
    @Inject
    private Dao dao;

    public List<AboutUs> queryAboutUs() {
        String sql = "SELECT * FROM aboutus WHERE 1 = 1";
        List<AboutUs> aboutus = dao.query(AboutUs.class, sql);
        return aboutus;
    }

    public void save(AboutUs aboutUs) {
        if (aboutUs.getId() > 0) {
            dao.update(aboutUs);
        } else {
            dao.create(aboutUs);
        }
    }
}

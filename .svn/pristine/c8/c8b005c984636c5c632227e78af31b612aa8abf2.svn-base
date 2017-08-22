package com.ceyi.project.dcjewelry.admin.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.Md5Utils;

import javax.inject.Inject;

/**
 * Created by lingh on 2017/4/5.
 */
@Component
public class AdminUserService {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserService.class);
    @Inject
    private Dao dao;

    public void create(AdminUser adminUser) {
        adminUser.setPassword(Md5Utils.hash(adminUser.getPassword() + adminUser.getSalt()));
        dao.create(adminUser);
    }

    public AdminUser login(String username, String password) {
        AdminUser adminUser = null;
        if (username.length() == 11) {
            adminUser = dao.get(AdminUser.class, new ParamMap<>("phone", username));
        }
        if (adminUser == null) {
            adminUser = dao.get(AdminUser.class, new ParamMap<>("username", username));
        }
        if (adminUser == null) {
            logger.error("用户不存在：username:{}", username);
            return null;
        }
        if (adminUser.getPassword().equals(Md5Utils.hash(password + adminUser.getSalt()))) {
            return adminUser;
        } else {
            logger.error("用户密码错误：username:{}", username);
            return null;
        }
    }
}

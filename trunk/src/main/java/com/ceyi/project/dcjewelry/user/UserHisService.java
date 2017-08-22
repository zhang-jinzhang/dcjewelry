package com.ceyi.project.dcjewelry.user;

import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by lingh on 2017/4/10.
 */
@Component
public class UserHisService {
    @Inject
    private Dao dao;

    public void create(UserHis userHis) {
        dao.create(userHis);
    }

    public UserHis get(int uid, String type, int tid) {
        return dao.get(UserHis.class, "SELECT * FROM user_his WHERE uid = :uid AND type = :type AND tid = :tid", new ParamMap<String, Object>("uid", uid).add("type", type).add("tid", tid));
    }

    public List<UserHis> query(int uid, int id, Date createTime) {
        ParamMap<String, Object> paramMap = new ParamMap<>("uid", uid);
        StringBuilder sql = new StringBuilder("SELECT * FROM user_his WHERE uid = :uid");
        if (id > 0) {
            sql.append(" AND ((id < :id AND createTime = :createTime) OR (createTime < :createTime))");
            paramMap.add("id", id).add("createTime", createTime);
        }
        sql.append(" ORDER BY createTime DESC, id DESC");
        return dao.query(UserHis.class, sql.toString(), paramMap);
    }
}

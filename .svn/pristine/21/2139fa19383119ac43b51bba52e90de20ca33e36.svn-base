package com.ceyi.project.dcjewelry.request;

import com.ceyi.project.dcjewelry.follow.UserFollow;
import com.ceyi.project.dcjewelry.follow.UserFollowService;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lingh on 2017/5/6.
 */
@Component
public class RequestService {
    @Inject
    private Dao dao;
    @Inject
    private UserService userService;
    @Inject
    private UserFollowService userFollowService;

    public void create(Request request) {
        dao.create(request);
    }

    public List<Request> query(int uid, int id, Date createTime) {
        ParamMap<String, Object> paramMap = new ParamMap<>("uid", uid);
        StringBuilder sql = new StringBuilder("SELECT * FROM requests WHERE 1=1");
        if (id > 0) {
            sql.append(" AND ((id < :id AND createTime = :createTime) OR (createTime < :createTime))");
            paramMap.add("id", id).add("createTime", createTime);
        }
        sql.append(" ORDER BY createTime DESC, id DESC");
        List<Request> requests = dao.query(Request.class, sql.toString(), paramMap);
        refresh(uid, requests);
        return requests;
    }

    private void refresh(int uid, List<Request> requests) {
        Set<String> uidSet = new HashSet<>();
        for (Request request : requests) {
            uidSet.add(request.getUid() + "");
        }
        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        Map<Integer, UserFollow> userFollowMap = userFollowService.loadByUids(uid, uidSet);
        for (Request request : requests) {
            User user = userMap.get(request.getUid());
            if (user != null) {
                if (userFollowMap.keySet().contains(user.getId())) {
                    user.setFollowed(true);
                }
                request.setUser(user);
            }
        }
    }
}

package com.ceyi.project.dcjewelry.follow;

import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.StringUtils;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lingh on 2017/5/2.
 */
@Component
public class UserFollowService {
    @Inject
    private Dao dao;
    @Inject
    private UserService userService;

    public int countByUser(int uid) {
        return dao.count(UserFollow.class, new ParamMap<>("followerUid", uid));
    }

    public boolean follow(int uid, int followerUid) {
        try {
            UserFollow userFollow = new UserFollow();
            userFollow.setUid(uid);
            userFollow.setFollowerUid(followerUid);
            dao.create(userFollow);
            return true;
        } catch (Exception e) {
            // 忽略
            return false;
        }
    }

    public int unfollow(int uid, int followerUid) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUid(uid);
        userFollow.setFollowerUid(followerUid);
        return dao.delete(userFollow);
    }

    public UserFollow get(int uid, int followerUid) {
        return dao.get(UserFollow.class, new ParamMap<String, Object>("uid", uid).add("followerUid", followerUid));
    }

    public Map<Integer, UserFollow> loadByUids(int uid, Set<String> uidSet) {
        String uids = StringUtils.join(uidSet);
        if (!StringUtils.hasText(uids)) {
            return new HashMap<>();
        }
        String sql = "SELECT * FROM user_follow WHERE uid in (" + uids + ") AND followerUid = :uid";
        List<UserFollow> userFollowList = dao.query(UserFollow.class, sql, new ParamMap<String, Object>("uid", uid));
        HashMap<Integer, UserFollow> followHashMap = new HashMap<>();
        for (UserFollow userFollow : userFollowList) {
            followHashMap.put(userFollow.getUid(), userFollow);
        }
        return followHashMap;
    }

    public List<UserFollow> query(int uid, int id, Date createTime) {
        ParamMap<String, Object> paramMap = new ParamMap<>("uid", uid);
        StringBuilder sql = new StringBuilder("SELECT * FROM user_follow WHERE followerUid = :uid");
        if (id > 0) {
            sql.append(" AND ((uid < :uid AND createTime = :createTime) OR (createTime < :createTime))");
            paramMap.add("uid", id).add("createTime", createTime);
        }
        sql.append(" ORDER BY createTime DESC, uid DESC");
        List<UserFollow> userFollows = dao.query(UserFollow.class, sql.toString(), paramMap);
        if (userFollows.size() == 0) {
            return userFollows;
        }
        Set<String> uidSet = new HashSet<>();
        for (UserFollow userFollow : userFollows) {
            uidSet.add(userFollow.getUid() + "");
        }

        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        for (UserFollow userFollow : userFollows) {
            userFollow.setUser(userMap.get(userFollow.getUid()));
        }
        return userFollows;
    }
}

package com.ceyi.project.dcjewelry.user;

import com.ceyi.project.dcjewelry.ConfigHolder;
import com.ceyi.project.dcjewelry.merchant.Merchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.http.HttpApi;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.FileUtils;
import pw.wecode.project.framework.utils.Md5Utils;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Result;
import pw.wecode.project.framework.weixin.AuthedUserInfoResponse;

import javax.inject.Inject;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lingh on 2017/3/31.
 */
@Component
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    private Dao dao;
    @Inject
    private HttpApi httpApi;
    @Inject
    private ConfigHolder configHolder;
    @Inject
    private UserHisService userHisService;

    public User get(int id) {
        User user = dao.get(User.class, id);
        if (user != null) {
            Merchant merchant = dao.get(Merchant.class, new ParamMap<>("uid", user.getId()));
            user.setMerchant(merchant);
        }
        return user;
    }

    public User getUserByOpenid(final String openid) {
        return dao.get(User.class, new ParamMap<>("openid", openid));
    }

    public User getUserByPhone(final String phone) {
        return dao.get(User.class, new ParamMap<>("phone", phone));
    }

    public User register(AuthedUserInfoResponse authedUserInfoResponse) {
        User user = new User();
        user.setOpenid(authedUserInfoResponse.getOpenid());
        user.setNickname(StringUtils.filterUtf8mb4(authedUserInfoResponse.getNickname()));
        user.setHeadimg(authedUserInfoResponse.getHeadimgurl());
        user.setSubscribe(authedUserInfoResponse.getSubscribe());
        user.setSex(authedUserInfoResponse.getSex());
        StringBuilder addressBuilder = new StringBuilder();
        if (StringUtils.hasText(authedUserInfoResponse.getProvince())) {
            addressBuilder.append(authedUserInfoResponse.getProvince());
        }
        if (StringUtils.hasText(authedUserInfoResponse.getCity())) {
            addressBuilder.append(authedUserInfoResponse.getCity());
        }
        user.setAddress(addressBuilder.toString());
        dao.create(user);
        logger.debug("用户注册成功:{}", user);
        return user;
    }

    public User loginByToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        String[] parts = token.split("-");
        if (parts.length != 3 || !StringUtils.hasText(parts[0]) || !StringUtils.hasText(parts[1]) || !StringUtils.hasText(parts[2])) {
            return null;
        }
        User user = dao.get(User.class, Integer.valueOf(parts[1]));
        if (user == null) {
            return null;
        }

        String expectedHash = Md5Utils.hash(parts[0] + "-" + user.getId() + "-" + user.getSalt());
        if (parts[2].equals(expectedHash)) {
            logger.debug("通过token登录: {}", user);
            return user;
        } else {
            logger.error("通过token登录失败，expectedHash:{} token:{}", expectedHash, token);
            return null;
        }
    }

    public int getTotal() {
        return dao.get(int.class, "SELECT COUNT(1) FROM users");
    }

    public int getMerchantTotal() {
        return dao.get(int.class, "SELECT COUNT(1) FROM users u INNER JOIN user_merchant um ON um.uid = u.id WHERE um.status=1");
    }

    public void queryUserJoinMerchant(Page<User> page, String keyword, int type) {
        Map<String, Object> paramMap = new ParamMap<String, Object>("offset", page.getOffset()).add("pageSize", page.getPageSize());
        StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM users u LEFT JOIN user_merchant um on um.uid = u.id WHERE 1=1");
        StringBuilder sql = new StringBuilder("SELECT u.*, um.uid, um.cid, um.name AS merchantName, um.area, um.boss, um.email, um.businessPic, um.status, um.createTime AS merchantCreateTime, um.updateTime AS merchantUpdateTime FROM users u LEFT JOIN user_merchant um on um.uid = u.id WHERE 1=1");
        if (StringUtils.hasText(keyword)) {
            countSql.append(" AND (u.nickname LIKE :keyword OR um.name LIKE :keyword)");
            sql.append(" AND (u.nickname LIKE :keyword OR um.name LIKE :keyword)");
            paramMap.put("keyword", "%" + keyword + "%");
        }
        if (type == User.TYPE_USER) {
            countSql.append(" AND um.uid IS NULL");
            sql.append(" AND um.uid IS NULL");
        } else if (type == User.TYPE_MERCHANT) {
            countSql.append(" AND um.uid IS NOT NULL");
            sql.append(" AND um.uid IS NOT NULL");
        }
        sql.append(" LIMIT :offset,:pageSize");
        int total = dao.get(int.class, countSql.toString(), paramMap);
        page.setRecordCount(total);
        if (total == 0) {
            return;
        }

        List<User> users = dao.query(sql.toString(), paramMap, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setSubscribe(rs.getInt("subscribe"));
                user.setAddress(rs.getString("address"));
                user.setNickname(rs.getString("nickname"));
                user.setHeadimg(rs.getString("headimg"));
                user.setPhone(rs.getString("phone"));
                user.setCreateTime(rs.getTimestamp("createTime"));
                user.setUpdateTime(rs.getTimestamp("updateTime"));
                user.setJobTitle(rs.getString("jobTitle"));
                user.setLevel(rs.getInt("level"));
                user.setOpenid(rs.getString("openid"));
                user.setPoint(rs.getInt("point"));
                user.setSalt(rs.getString("salt"));
                user.setSex(rs.getInt("sex"));
                user.setSignature(rs.getString("signature"));
                user.setWechatId(rs.getString("wechatId"));
                String merchantName = rs.getString("merchantName");
                if (StringUtils.hasText(merchantName)) {
                    Merchant merchant1 = new Merchant();
                    merchant1.setUid(rs.getInt("uid"));
                    merchant1.setCid(rs.getInt("cid"));
                    merchant1.setName(rs.getString("merchantName"));
                    merchant1.setArea(rs.getString("area"));
                    merchant1.setBoss(rs.getString("boss"));
                    merchant1.setEmail(rs.getString("email"));
                    merchant1.setBusinessPic(rs.getString("businessPic"));
                    merchant1.setStatus(rs.getInt("status"));
                    merchant1.setCreateTime(rs.getTimestamp("merchantCreateTime"));
                    merchant1.setUpdateTime(rs.getTimestamp("merchantUpdateTime"));
                    user.setMerchant(merchant1);
                }

                return user;
            }
        });
        page.setData(users);
    }

    public int updateStatus(int uid, int status) {
        return dao.update("UPDATE user_merchant SET status=:status WHERE uid = :uid AND status != :status", new ParamMap<String, Object>("uid", uid).add("status", status));
    }

    public Result incPoint(User user, int increment, String type, int tid) {
        User dbUser = this.get(user.getId());
        if (dbUser.getPoint() + increment < 0) {
            return new Result(Result.CODE_ERROR, "积分不足");
        }
        dbUser.setPoint(dbUser.getPoint() + increment);
        dao.update(dbUser);
        UserHis userHis1 = new UserHis();
        userHis1.setUid(dbUser.getId());
        userHis1.setPoint(dbUser.getPoint());
        userHis1.setAmount(increment);
        userHis1.setTid(tid);
        userHis1.setType(type);
        userHisService.create(userHis1);
        user.setPoint(dbUser.getPoint());
        return new Result();
    }

    public Map<Integer, User> loadByUids(Set<String> uidSet) {
        String uids = StringUtils.join(uidSet);
        if (!StringUtils.hasText(uids)) {
            return new HashMap<>();
        }
        String sql = "SELECT * FROM users WHERE id in (" + uids + ")";
        List<User> users = dao.query(User.class, sql, new ParamMap<String, Object>());
        String msql = "SELECT * FROM user_merchant WHERE uid in (" + uids + ")";
        List<Merchant> merchants = dao.query(Merchant.class, msql, new ParamMap<String, Object>());
        Map<Integer, User> userMap = new HashMap<>(users.size());
        Map<Integer, Merchant> merchantMap = new HashMap<>(merchants.size());
        for (Merchant merchant:merchants){
            merchantMap.put(merchant.getUid(),merchant);
        }
        for (User user : users) {
            user.setMerchant(merchantMap.get(user.getId()));
            userMap.put(user.getId(), user);
        }
        return userMap;
    }

    private void updateHeadimg(int uid, String headimg) {
        User user = new User();
        user.setId(uid);
        user.setHeadimg(headimg);
        dao.update(user, true);
    }

    public void updateInfo(User user) {
        User updateVo = new User();
        updateVo.setId(user.getId());
        updateVo.setSex(user.getSex());
        updateVo.setNickname(user.getNickname());
        updateVo.setPhone(user.getPhone());
        updateVo.setAddress(user.getAddress());
        updateVo.setHeadimg(user.getHeadimg());
        dao.update(updateVo, true);
    }

    public void downloadHeadimg() {
        String sql = "SELECT * FROM users WHERE headimg LIKE :headimg ORDER BY updateTime DESC, id DESC LIMIT 0, 100";
        List<User> users = dao.query(User.class, sql, new ParamMap<String, Object>("headimg", "http:%"));
        if (users.size() == 0) {
            return;
        }
        for (User user : users) {
            try {
                byte[] bytes = httpApi.get(user.getHeadimg()).bytes();
                String md5 = Md5Utils.hash(bytes);
                File file = new File(configHolder.getWebroot() + "/upload/" + md5);
                if (!file.exists()) {
                    FileUtils.writeAll(configHolder.getWebroot() + "/upload/" + md5, bytes);
                }
                updateHeadimg(user.getId(), "/upload/" + md5);
                logger.debug("下载用户头像成功, id:{} nickname:{} old:{} new:{}", user.getId(), user.getNickname(), user.getHeadimg(), "/upload/" + md5);
            } catch (Exception e) {
                logger.error("下载头像出错, id:{} nickname:{} headimg:{} e:{}", user.getId(), user.getNickname(), user.getHeadimg(), e);
            }
        }
    }

    public void updateBgUrl(User user) {
        User update = new User();
        update.setId(user.getId());
        update.setBgUrl(user.getBgUrl());
        dao.update(update, true);
    }

    public int count() {
        return dao.count(User.class);
    }

    public List<User> getFollowers(int uid) {
        String sql = "SELECT u.* FROM users u INNER JOIN user_follow uf ON u.id = uf.followerUid WHERE u.id = :uid";
        return dao.query(User.class, sql, new ParamMap<String, Object>("uid", uid));
    }

    public List<User> getUsers() {
        String sql = "SELECT * FROM users";
        return dao.query(User.class, sql);
    }
}

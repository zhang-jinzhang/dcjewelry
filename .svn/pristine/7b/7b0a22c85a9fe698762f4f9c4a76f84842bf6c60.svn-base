package com.ceyi.project.dcjewelry.message;

import com.ceyi.project.dcjewelry.config.Config;
import com.ceyi.project.dcjewelry.config.ConfigService;
import com.ceyi.project.dcjewelry.follow.UserFollow;
import com.ceyi.project.dcjewelry.follow.UserFollowService;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.common.SyncInfo;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.OrderList;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.weixin.TemplateMessage;
import pw.wecode.project.framework.weixin.WeixinService;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lingh on 2017/5/2.
 */
@Component
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final String CONFIG_NOTIFY_MESSAGE = "wx_notify_message:";
    @Value("${tid}")
    private String tid;
    @Value("${host}")
    private String host;
    @Inject
    private Dao dao;
    @Inject
    private WeixinService weixinService;
    @Inject
    private ConfigService configService;
    @Inject
    private Gson gson;
    @Inject
    private UserService userService;
    @Inject
    private UserFollowService userFollowService;

    public void create(Message message) {
        dao.create(message);
    }

    public List<Message> query(int uid, int id, Date createTime) {
        ParamMap<String, Object> paramMap = new ParamMap<>("uid", uid);
        StringBuilder sql = new StringBuilder("SELECT * FROM private_message WHERE (toUid = :uid OR fromUid = :uid)");
        if (id > 0) {
            sql.append(" AND ((id < :id AND createTime = :createTime) OR (createTime < :createTime))");
            paramMap.add("id", id).add("createTime", createTime);
        }
        sql.append(" ORDER BY createTime DESC, id DESC");
        List<Message> messages = dao.query(Message.class, sql.toString(), paramMap);
        refresh(uid, messages);
        return messages;
    }

    public void read(int id) {
        Message message = new Message();
        message.setId(id);
        message.setStatus(Message.STATUS_READ);
        dao.update(message, true);
    }

    public int countUnread(int uid) {
        return dao.count(Message.class, new ParamMap<String, Object>("toUid", uid).add("status", 0));
    }

    public Message getLastestMessage(int uid, SyncInfo syncInfo) {
        List<Message> messages = dao.query(Message.class, new ParamMap<String, Object>("uid", uid).add("status", 0), new OrderList("createTime", OrderList.DESC), new Page(1, 1));
        if (messages.size() > 0) {
            return messages.get(0);
        } else {
            return null;
        }
    }

    public void notifyByWx() {
        List<Message> messages = dao.query(Message.class, new ParamMap<String, Object>("status", 0), new OrderList("createTime", OrderList.DESC), new Page(1, 1000));
        if (messages.size() == 0) {
            return;
        }
        logger.debug("有{}条未阅读私信", messages.size());
        Map<Integer, Message> messageMap = new HashMap<>();
        for (Message message : messages) {
            if (!messageMap.keySet().contains(message.getToUid())) {
                messageMap.put(message.getToUid(), message);
            }
        }
        for (Message message : messageMap.values()) {
            SyncInfo syncInfo = new SyncInfo(message.getId(), new Date(message.getCreateTime().getTime() - 1));
            Config config = configService.get(CONFIG_NOTIFY_MESSAGE + message.getToUid());
            if (config != null) {
                syncInfo = gson.fromJson(config.getValue(), SyncInfo.class);
            }
            if (syncInfo.getTime().getTime() >= message.getCreateTime().getTime()) {
                continue;
            }
            User user = userService.get(message.getToUid());
            if (user == null) {
                logger.warn("用户不存在, message:{}", message);
                continue;
            }
            TemplateMessage templateMessage = new TemplateMessage();
            templateMessage.setTouser(user.getOpenid());
            templateMessage.setTemplate_id(tid);
            templateMessage.setUrl(host + "/wx/message/index.html");
            Map<String, Object> paramMap = new ParamMap<String, Object>("first", new ParamMap<String, Object>("value", "您有收到私信，请及时回复").add("color", "#173177"))
                    .add("keyword1", new ParamMap<String, Object>("value", "系统").add("color", "#173177"))
                    .add("keyword2", new ParamMap<String, Object>("value", message.getCreateTime()).add("color", "#173177"))
                    .add("remark", new ParamMap<String, Object>("value", "点击详情并及时处理").add("color", "#173177"));
            templateMessage.setData(paramMap);
            weixinService.sendTempalteMessage(templateMessage);
            syncInfo.setId(message.getId());
            syncInfo.setTime(message.getCreateTime());
            configService.save(new Config(CONFIG_NOTIFY_MESSAGE + message.getToUid(), gson.toJson(syncInfo)));
            logger.debug("通知用户有未读私信, message: {} templateMessage:{}", message, templateMessage);
        }
    }

    private void refresh(int uid, List<Message> messages) {
        Set<String> uidSet = new HashSet<>();
        for (Message message : messages) {
            uidSet.add(message.getFromUid() + "");
        }
        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        Map<Integer, UserFollow> userFollowMap = userFollowService.loadByUids(uid, uidSet);
        for (Message message : messages) {
            User fromUser = userMap.get(message.getFromUid());
            if (fromUser != null && userFollowMap.keySet().contains(fromUser.getId())) {
                fromUser.setFollowed(true);
            }
            message.setFromUser(userMap.get(message.getFromUid()));
        }
    }
}

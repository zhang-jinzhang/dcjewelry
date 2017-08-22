package com.ceyi.project.dcjewelry.notice;

import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.OrderList;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.StringUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lingh on 2017/5/5.
 */
@Component
public class NoticeService {
    @Inject
    private Dao dao;

    public void save(Notice notice) {
        if (notice.getId() > 0) {
            dao.update(notice);
        } else {
            dao.create(notice);
        }
    }

    public void read(int uid, int nid) {
        try {
            UserNotice userNotice = new UserNotice();
            userNotice.setUid(uid);
            userNotice.setNid(nid);
            dao.create(userNotice);
        } catch (Exception e) {
            // ignore
        }
    }

    public void delete(int[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        for (int id : ids) {
            dao.delete("DELETE FROM notice WHERE id = :id", new ParamMap<String, Object>("id", id));
        }
    }

    public Page<Notice> query(Page page) {
        int total = dao.count(Notice.class);
        page.setRecordCount(total);
        if (total <= 0) {
            return page;
        }
        List<Notice> notices = dao.query(Notice.class, new ParamMap<String, Object>(), new OrderList("updateTime", OrderList.DESC).add("id", OrderList.DESC), page);
        page.setData(notices);
        return page;
    }

    public List<Notice> query(int uid, int id, Date updateTime) {
        ParamMap<String, Object> paramMap = new ParamMap<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM notice WHERE 1 = 1");
        if (id > 0) {
            sql.append(" AND ((id < :id AND updateTime = :updateTime) OR (updateTime < :updateTime))");
            paramMap.add("id", id).add("updateTime", updateTime);
        }
        sql.append(" ORDER BY updateTime DESC, id DESC LIMIT 0, 10");
        List<Notice> notices = dao.query(Notice.class, sql.toString(), paramMap);
        refresh(uid, notices);
        return notices;
    }

    private void refresh(int uid, List<Notice> notices) {
        if (notices.size() == 0) {
            return;
        }

        Set<String> nidSet = new HashSet<>();
        for (Notice notice : notices) {
            nidSet.add(notice.getId() + "");
        }
        String nids = StringUtils.join(nidSet);
        String sql = "SELECT * FROM user_notice WHERE uid = :uid AND nid IN (" + nids + ")";
        List<UserNotice> userNotices = dao.query(UserNotice.class, sql, new ParamMap<String, Object>("uid", uid));
        Set<Integer> readSet = new HashSet<>();
        for (UserNotice userNotice : userNotices) {
            readSet.add(userNotice.getNid());
        }
        for (Notice notice : notices) {
            if (readSet.contains(notice.getId())) {
                notice.setRead(true);
            }
        }
    }
}

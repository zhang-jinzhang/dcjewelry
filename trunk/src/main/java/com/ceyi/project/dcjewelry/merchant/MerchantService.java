package com.ceyi.project.dcjewelry.merchant;

import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lingh on 2017/5/7.
 */
@Component
public class MerchantService {

    @Inject
    private Dao dao;
    @Inject
    private UserService userService;

    public List<Merchant> query() {
        String sql = "SELECT * FROM user_merchant WHERE status != 1";
        List<Merchant> merchants = dao.query(Merchant.class, sql);
        refresh(merchants);
        return merchants;
    }


    public Merchant get(int uid) {
        return dao.get(Merchant.class, new ParamMap<String, Object>("uid", uid));
    }

    public String queryByUid(int uid) {
        String sql = "SELECT * FROM user_merchant WHERE uid= "+uid;
        List<Merchant> merchants = dao.query(Merchant.class, sql);
        refresh(merchants);
        String cids="";
        if(merchants!=null){
            for(Merchant merchant:merchants){
                cids+=merchant.getCid()+",";
            }
        }
        return cids;
    }

    public void save(Merchant merchant) {
        Merchant exists = this.get(merchant.getUid());
        if (exists != null) {
            dao.update(merchant);
        } else {
            dao.create(merchant);
        }
    }

    public void updateStatus(int uid, int status) {
        String sql = "UPDATE user_merchant SET status = :status WHERE uid = :uid";
        dao.update(sql, new ParamMap<String, Object>("uid", uid).add("status", status));
    }

    private void refresh(List<Merchant> merchants) {
        Set<String> uidSet = new HashSet<>();
        for (Merchant merchant : merchants) {
            uidSet.add(merchant.getUid() + "");
        }
        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        for (Merchant merchant : merchants) {
            merchant.setUser(userMap.get(merchant.getUid()));
        }
    }
}

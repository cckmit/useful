package me.andpay.ac.wpn.srv.dao;

import me.andpay.ac.wpn.api.model.db.cond.QueryWxBindUserCond;
import me.andpay.ac.wpn.api.model.db.WxBindUser;
import me.andpay.ac.wpn.srv.helper.WxBindUserMigrateHelper;
import me.andpay.ti.daf.dao.hibernate.HibernateBatchDao;
import me.andpay.ti.daf.dao.hibernate.HibernateGenericDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by cen on 16/11/2.
 * 微信公众号绑定用户查询
 */
public class WxBindUserDao extends HibernateBatchDao<WxBindUser, Long, QueryWxBindUserCond> {

    @Autowired
    private WxBindUserMigrateHelper wxBindUserMigrateHelper;

    @Override
    public Long add(WxBindUser wxBindUser) {
        wxBindUserMigrateHelper.convertInWxBindUser(wxBindUser);
        return super.add(wxBindUser);
    }

    @Override
    public void update(WxBindUser wxBindUser) {
        wxBindUserMigrateHelper.convertInWxBindUser(wxBindUser);
        super.update(wxBindUser);
    }

    @Override
    public WxBindUser getFirst(QueryWxBindUserCond cond) {
        wxBindUserMigrateHelper.convertInQueryWxBindUserCond(cond);
        return super.getFirst(cond);
    }

    @Override
    public List<WxBindUser> query(QueryWxBindUserCond cond, long firstResult, int maxResults) {
        wxBindUserMigrateHelper.convertInQueryWxBindUserCond(cond);
        return super.query(cond, firstResult, maxResults);
    }
}

package me.andpay.ac.wpn.migrate;

import me.andpay.ac.wpn.api.model.db.WxBindUser;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxBindUserCond;
import me.andpay.ac.wpn.srv.dao.WxBindUserDao;
import me.andpay.ac.wpn.srv.helper.MigrateHelper;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.daf.dao.IterateCallback;
import me.andpay.ti.daf.dao.SessionContext;
import me.andpay.ti.util.Counter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QUYONG on 17/12/21.
 */
public class WxBindUserMigrator {

    private static final long BATCH_SIZE = 50000;

    @Autowired
    private WxBindUserDao wxBindUserDao;

    @Autowired
    private MigrateHelper migrateHelper;

    public void migrateAllWxBindUsers() {
        QueryWxBindUserCond queryWxBindUserCond = new QueryWxBindUserCond();
        queryWxBindUserCond.setOrders("bindUserId+");
        doMigrate(queryWxBindUserCond);
    }

    public void migrateWxBindUser(String userName) {
        QueryWxBindUserCond queryWxBindUserCond = new QueryWxBindUserCond();
        queryWxBindUserCond.setOrders("bindUserId+");
        queryWxBindUserCond.setUserName(userName);
        doMigrate(queryWxBindUserCond);
    }

    public void doMigrate(QueryWxBindUserCond queryWxBindUserCond) {
        try {
            final Counter counter = new Counter();
            final List<WxBindUser> wxBindUsers = new ArrayList<WxBindUser>();
            wxBindUserDao.quickIterate(queryWxBindUserCond, new IterateCallback<WxBindUser>() {
                @Override
                public boolean doElement(SessionContext sessionContext, WxBindUser wxBindUser) throws AppBizException {
                    wxBindUser.setUserName(migrateHelper.convertInUserName(wxBindUser.getUserName()));
                    wxBindUsers.add(wxBindUser);

                    counter.incCount();
                    if (wxBindUsers.size() >= BATCH_SIZE) {
                        wxBindUserDao.batchUpdate(wxBindUsers, "userName");
                        wxBindUsers.clear();
                        System.out.println("migrate " + counter.getCount());
                    }

                    return true;
                }
            });

            if (wxBindUsers.size() > 0) {
                wxBindUserDao.batchUpdate(wxBindUsers, "userName");
                wxBindUsers.clear();
            }

            System.out.println("migrate " + counter.getCount());
        } catch (AppBizException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

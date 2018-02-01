package me.andpay.ac.wpn.srv.helper;

import me.andpay.ac.wpn.api.model.db.WxBindUser;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxBindUserCond;
import me.andpay.ti.util.CollectionUtil;
import me.andpay.ti.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by QUYONG on 17/12/21.
 */
public class WxBindUserMigrateHelper {

    @Autowired
    private MigrateHelper migrateHelper;

    public void convertInWxBindUser(WxBindUser wxBindUser) {
        if (wxBindUser != null) {
            wxBindUser.setUserName(migrateHelper.convertInUserName(wxBindUser.getUserName()));
        }

    }

    public void convertOutWxBindUser(WxBindUser wxBindUser) {
        if (wxBindUser != null) {
            wxBindUser.setUserName(migrateHelper.convertOutUserName(wxBindUser.getUserName()));
        }

    }

    public void convertInQueryWxBindUserCond(QueryWxBindUserCond cond) {
        if (cond != null) {
            if (StringUtil.isNotBlank(cond.getUserName())) {
                cond.addUserName(cond.getUserName());
                cond.setUserName(null);
            }
            if (CollectionUtil.isNotEmpty(cond.getUserNames())) {
                for (String userName : cond.getUserNames()) {
                    cond.addUserName(migrateHelper.convertInUserName(userName));
                }
            }
        }
    }
}

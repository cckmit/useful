package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.AbstractOrderableCondition;
import me.andpay.ti.daf.dao.simplequery.annotation.Condition;

/**
 * Created by cen on 16/11/2.
 * 微信公众号查询条件
 */
@Condition
public class QueryWxPublicNumberCond extends AbstractOrderableCondition {


    /**
     * 公众号编号
     */
    private String appId;

    /**
     * app别名
     */
    private String appAlais;

    public String getAppAlais() {
        return appAlais;
    }

    public void setAppAlais(String appAlais) {
        this.appAlais = appAlais;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

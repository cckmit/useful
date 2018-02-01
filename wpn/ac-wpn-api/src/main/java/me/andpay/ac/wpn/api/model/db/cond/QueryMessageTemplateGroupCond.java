package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.AbstractOrderableCondition;
import me.andpay.ti.daf.dao.simplequery.annotation.Condition;

/**
 * 查询模板消息组条件
 * Created by cen on 2017/7/6.
 */
@Condition
public class QueryMessageTemplateGroupCond extends AbstractOrderableCondition{


    /**
     * 所属应用
     */
    private String appId;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

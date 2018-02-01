package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.annotation.Condition;

/**
 * Created by cen on 2017/2/22.
 */
@Condition
public class QueryWeixinNotifyInfoCond {


    /**
     * 消息散列值
     */
    private String messageHashCode;

    public String getMessageHashCode() {
        return messageHashCode;
    }

    public void setMessageHashCode(String messageHashCode) {
        this.messageHashCode = messageHashCode;
    }
}

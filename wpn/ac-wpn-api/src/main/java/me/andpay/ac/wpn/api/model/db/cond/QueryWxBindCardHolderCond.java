package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.AbstractOrderableCondition;
import me.andpay.ti.daf.dao.simplequery.annotation.Condition;

/**
 * Created by cen on 2017/2/22.
 */
@Condition
public class QueryWxBindCardHolderCond extends AbstractOrderableCondition {

    /**
     * 卡号令牌
     */
    private String cardNOToken;

    /**
     * 微信用户编号
     */
    private Long userId;

    /**
     * 绑定状态
     *
     */
    private String bindStatus;

    public String getCardNOToken() {
        return cardNOToken;
    }

    public void setCardNOToken(String cardNOToken) {
        this.cardNOToken = cardNOToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }
}

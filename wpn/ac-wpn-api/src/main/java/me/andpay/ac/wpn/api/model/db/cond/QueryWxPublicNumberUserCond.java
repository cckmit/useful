package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.AbstractOrderableCondition;
import me.andpay.ti.daf.dao.simplequery.annotation.Condition;
import me.andpay.ti.daf.dao.simplequery.annotation.Expression;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by cen on 16/11/4.
 */
@Condition
public class QueryWxPublicNumberUserCond extends AbstractOrderableCondition {

    private Long userId;
    /**
     * 微信公众号openId
     */
    private String openid;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 机构编号
     */
    private String partyId;

    /**
     * 公众号编号
     */
    private String appid;



    @Expression(operator = "in",property = "userId")
    private Set<Long> userIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

}

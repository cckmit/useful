package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.AbstractOrderableCondition;
import me.andpay.ti.daf.dao.simplequery.annotation.Condition;
import me.andpay.ti.daf.dao.simplequery.annotation.Expression;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by cen on 16/11/2.
 * 微信绑定用户查询条件
 */
@Condition
public class QueryWxBindUserCond extends AbstractOrderableCondition {

    /**
     * 微信绑定用户Id
     */
    private Long userId;
    /**
     * 微信公众号openId
     */
    public String openid;
    /**
     * 用户名
     */
    public String userName;
    /**
     * 机构编号
     */
    private String partyId;
    /**
     * 绑定类型
     */
    private String bindType;

    /**
     * 微信绑定用户Id
     */
    private Long bindUserId;

    /**
     * 绑定状态
     */
    private String status;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Expression(operator = "in", property = "userName")
    private Set<String> userNames;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public Long getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(Long bindUserId) {
        this.bindUserId = bindUserId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(Set<String> userNames) {
        this.userNames = userNames;
    }

    public void addUserName(String userName) {
        if (userNames == null) {
            userNames = new HashSet<String>();
        }
        userNames.add(userName);

    }
}

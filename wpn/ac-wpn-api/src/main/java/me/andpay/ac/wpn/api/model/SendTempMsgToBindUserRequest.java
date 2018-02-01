package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * Created by cen on 2016/11/16.
 * 模板消息发送请求
 */
public class SendTempMsgToBindUserRequest extends SendTempMsgBaseRequest{

    /**
     * 用户名
     */
    @Size(max = 64)
    private String userName;

    /**
     * 机构编号
     */
    @NotNull
    @Size(max = 32)
    private String partyId;


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


}

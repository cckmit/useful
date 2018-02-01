package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendTempMsgToCardHolderRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.Callback;
import me.andpay.ti.lnk.annotaion.LnkMethod;
import me.andpay.ti.lnk.annotaion.LnkService;

/**
 * Created by cen on 16/11/3.
 * 微信消息服务
 */

@LnkService(serviceGroup = ServiceGroups.AC_WPN_MESSAGE_SRV)
public interface WeixinMessageService {

    /**
     * 发送微信模板消息
     * @param sendTempMsgToBindUserRequest
     * @param sendWeixinMessageCallback
     * @throws AppBizException
     */
    @LnkMethod(async = true)
    void sendTemplateMessageToBindUser(SendTempMsgToBindUserRequest sendTempMsgToBindUserRequest,@Callback SendWeixinMessageCallback sendWeixinMessageCallback) throws AppBizException;

    /**
     * 发送模板消息给持卡人
     * @param sendTempMsgToCardHolderRequest
     * @param sendWeixinMessageCallback
     */
    @LnkMethod(async = true)
    void sendTemplateMessageToCardHolder(SendTempMsgToCardHolderRequest sendTempMsgToCardHolderRequest,@Callback SendWeixinMessageCallback sendWeixinMessageCallback);

}

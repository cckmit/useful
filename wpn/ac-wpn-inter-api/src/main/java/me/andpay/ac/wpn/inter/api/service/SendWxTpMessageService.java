package me.andpay.ac.wpn.inter.api.service;

import me.andpay.ac.wpn.inter.api.dto.WxTpMessage;
import me.andpay.ti.base.AppBizException;

/**
 * Created by cen on 2016/11/17.
 */
public interface SendWxTpMessageService {

    /**
     * 发送模板消息
     * @param wxTpMessage
     * @param sendWxTpMessageCallback
     */
    public void sendTemplateMessage(WxTpMessage wxTpMessage,
                                    SendWxTpMessageCallback sendWxTpMessageCallback)
                                    throws AppBizException;

    public void sendTemplateMessage2(WxTpMessage wxTpMessage,
                                     SendWxTpMessageCallback sendWxTpMessageCallback) throws AppBizException;

}
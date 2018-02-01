package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.WeixinNotifyRequest;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.LnkService;

/**
 * 微信消息接收器
 * Created by cen on 2017/2/16.
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_NOTIFY_RECEIVE_SRV)
public interface WeixinNotifyReceiverService {


    /**
     * 接受通知消息
     * @param weixinNotifyRequest
     */
    String receive(WeixinNotifyRequest weixinNotifyRequest) throws AppBizException;
}

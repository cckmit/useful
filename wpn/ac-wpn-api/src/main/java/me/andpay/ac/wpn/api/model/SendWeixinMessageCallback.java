package me.andpay.ac.wpn.api.model;

import me.andpay.ti.lnk.annotaion.LnkAbstractService;
import me.andpay.ti.lnk.annotaion.LnkMethod;

/**
 * 微信发送回调接口
 * Created by cen on 2017/2/8.
 */
@LnkAbstractService
public interface SendWeixinMessageCallback {

    @LnkMethod(async = true)
    void onResult(SendWeixinMessageResult sendWeixinMessageResult);
}

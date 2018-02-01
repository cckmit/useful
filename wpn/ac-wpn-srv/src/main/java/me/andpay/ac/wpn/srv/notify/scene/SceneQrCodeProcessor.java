package me.andpay.ac.wpn.srv.notify.scene;

import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.srv.notify.NotifyContext;

/**
 * 场景处理器
 * Created by cen on 2017/2/24.
 */
public interface SceneQrCodeProcessor {


    String process(WeixinQRCode weixinQRCode, NotifyContext notifyContext);
}

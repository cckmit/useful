package me.andpay.ac.wpn.srv.notify;

import java.util.Map;

/**
 * 消息处理器接口
 * Created by cen on 2017/2/23.
 */
public interface WeixinNotifyProcessor {

    /**
     * 处理消息
     * @param notifyContext
     * @return
     */
    String process(NotifyContext notifyContext);
}

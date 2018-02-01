package me.andpay.ac.wpn.srv.notify.queue;

import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ac.wpn.srv.notify.WeixinNotifyProcessor;
import me.andpay.ti.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知队列事件处理器
 * Created by cen on 2017/2/24.
 */
public class NotifyQueueEventHandler {

    /**
     * 处理器
     */
    private Map<String,WeixinNotifyProcessor> processores = new HashMap<String, WeixinNotifyProcessor>();


    /**
     * 消息处理
     * @param notifyContext
     */
    public void handle(NotifyContext notifyContext) {

        Map<String,String> xmlData  = notifyContext.getXmlData();

        String msgType = xmlData.get(WeiXinXmlKeys.MSG_TYPE);
        String event = xmlData.get(WeiXinXmlKeys.EVENT);
        if(StringUtil.isNotBlank(msgType)) {
            msgType += "_"+event;
        }

        WeixinNotifyProcessor weixinNotifyProcessor = processores.get(msgType);
        if(weixinNotifyProcessor != null) {
            weixinNotifyProcessor.process(notifyContext);
        }
    }


    public void setProcessores(Map<String, WeixinNotifyProcessor> processores) {
        this.processores = processores;
    }
}

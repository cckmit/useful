package me.andpay.ac.wpn.srv.notify;

import me.andpay.ac.wpn.api.consts.TiQueueBeanNames;
import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.srv.notify.queue.NotifyQueueEventHandler;
import me.andpay.ti.queue.amqp.AmqpQueue;
import me.andpay.ti.queue.api.QueueListener;
import me.andpay.ti.util.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信消息处理器
 * Created by cen on 2017/2/23.
 */
public class WeiXinMsgHandler implements InitializingBean {

    /**
     * 微信消息同步处理器
     */
    private Map<String,WeixinNotifyProcessor> synchProcessores = new HashMap<String, WeixinNotifyProcessor>();


    @Autowired
    private NotifyQueueEventHandler notifyQueueEventHandler;

    @Autowired
    @Qualifier(TiQueueBeanNames.WEIXIN_NOTIFY_QUEUE)
    private AmqpQueue<NotifyContext> weixinNotifyInfoAmqpQueue;
    /**
     * 处理消息
     * @param notifyContext
     * @return
     */
    public String handle(final NotifyContext notifyContext) {

        Map<String,String> xmlData  = notifyContext.getXmlData();

        String msgType = xmlData.get(WeiXinXmlKeys.MSG_TYPE);
        String event = xmlData.get(WeiXinXmlKeys.EVENT);
        if(StringUtil.isNotBlank(msgType)) {
            msgType += "_"+event;
        }

        WeixinNotifyProcessor weixinNotifyProcessor = synchProcessores.get(msgType);
        if(weixinNotifyProcessor != null) {
            return weixinNotifyProcessor.process(notifyContext);
        }


        weixinNotifyInfoAmqpQueue.enqueue(notifyContext);
        return NotifySupport.NOTIFY_COMPLITE;
    }

    public Map<String, WeixinNotifyProcessor> getSynchProcessores() {
        return synchProcessores;
    }

    public void setSynchProcessores(Map<String, WeixinNotifyProcessor> synchProcessores) {
        this.synchProcessores = synchProcessores;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        weixinNotifyInfoAmqpQueue.listen(new QueueListener<NotifyContext>() {
            @Override
            public void onMessage(NotifyContext notifyContextData) {
                notifyQueueEventHandler.handle(notifyContextData);
            }
        },15);
    }
}

package me.andpay.ac.wpn.srv.base.event.ecs;

import me.andpay.ac.event.ecs.order.EcsOrderDeliverEvent;
import me.andpay.ac.event.ecs.order.EcsOrderPaySuccessEvent;
import me.andpay.ac.wpn.srv.base.event.ecs.processor.EcsOrderDeliverEventProcessor;
import me.andpay.ti.event.api.EventConsumer;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Tangzhiqiang on 17/9/11.
 */
public class EcsOrderDeliverEventConsumer implements EventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EcsOrderDeliverEventConsumer.class);


    @Autowired
    private EcsOrderDeliverEventProcessor ecsOrderDeliverEventProcessor;


    @Override
    public void onEvent(String eventName, Object eventData) {

        if (eventData instanceof EcsOrderDeliverEvent) {
            EcsOrderDeliverEvent eode = (EcsOrderDeliverEvent)eventData;
            if(StringUtil.isBlank(eode.getBuyerPartyId())) {
                logger.warn("EcsOrderDeliverEventConsumer OrderNo=[{}] buyPartyId is null",new Object[]{eode.getOrderNo()});
                return;
            }

            ecsOrderDeliverEventProcessor.dealEvent(eode);

        }
    }

}

package me.andpay.ac.wpn.srv.base.event.cmmks;

import me.andpay.ac.event.acq.cmmks.RealtimeProfitSettleEvent;
import me.andpay.ac.wpn.srv.base.event.cmmks.processor.RealtimeProfitSettleEventProcessor;
import me.andpay.ti.event.api.EventConsumer;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Tangzhiqiang on 17/9/7.
 */
public class RealtimeProfitSettleEventConsumer implements EventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RealtimeProfitSettleEventConsumer.class);


    @Autowired
    private RealtimeProfitSettleEventProcessor realtimeProfitSettleEventProcessor;


    @Override
    public void onEvent(String eventName, Object eventData) {

        if (eventData instanceof RealtimeProfitSettleEvent) {
            RealtimeProfitSettleEvent awe = (RealtimeProfitSettleEvent)eventData;
            if(StringUtil.isBlank(awe.getSettlePartyId())) {
                logger.warn("settleAccountNo=[{}] partyId is null",new Object[]{awe.getSettleAccountNo()});
                return;
            }
            realtimeProfitSettleEventProcessor.dealEvent(awe);

        }
    }

}

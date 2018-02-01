package me.andpay.ac.wpn.srv.base.event.cmmks;

import me.andpay.ac.event.acq.cmmks.DailyProfitSettleEvent;
import me.andpay.ac.wpn.srv.base.event.cmmks.processor.DailyProfitSettleEventProcessor;
import me.andpay.ti.event.api.EventConsumer;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Tangzhiqiang on 17/9/7.
 */
public class DailyProfitSettleEventConsumer implements EventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RealtimeProfitSettleEventConsumer.class);


    @Autowired
    private DailyProfitSettleEventProcessor dailyProfitSettleEventProcessor;


    @Override
    public void onEvent(String eventName, Object eventData) {

        if (eventData instanceof DailyProfitSettleEvent) {
            DailyProfitSettleEvent dpse = (DailyProfitSettleEvent)eventData;
            if(StringUtil.isBlank(dpse.getSettlePartyId())) {
                logger.warn("settleAccountNo=[{}] settlePartyId is null",new Object[]{dpse.getSettleAccountNo()});
                return;
            }

            dailyProfitSettleEventProcessor.dealEvent(dpse);

        }
    }
}

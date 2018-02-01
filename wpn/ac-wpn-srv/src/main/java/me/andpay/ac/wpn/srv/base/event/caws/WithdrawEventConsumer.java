package me.andpay.ac.wpn.srv.base.event.caws;

import me.andpay.ac.event.acq.caws.AccountWithdrawEvent;
import me.andpay.ac.wpn.srv.base.event.caws.processor.WithdrawEventProcessor;
import me.andpay.ti.event.api.EventConsumer;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Tangzhiqiang on 17/9/7.
 */
public class WithdrawEventConsumer implements EventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawEventConsumer.class);


    @Autowired
    private WithdrawEventProcessor withdrawEventProcessor;


    @Override
    public void onEvent(String eventName, Object eventData) {

        if (eventData instanceof AccountWithdrawEvent) {
            AccountWithdrawEvent awe = (AccountWithdrawEvent)eventData;
            if(StringUtil.isBlank(awe.getUserName())) {
                logger.warn("userName=[{}] or party=[{}] is null",new Object[]{awe.getUserName()});
                return;
            }
            withdrawEventProcessor.dealEvent(awe);

        }
    }
}

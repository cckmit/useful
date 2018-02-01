package me.andpay.ac.wpn.srv.notify.queue;

import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ac.wpn.srv.notify.NotifyTestSupport;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by cen on 2017/2/27.
 */
@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/notify/ac-wpn-srv-test-notify-config.xml" })
public class NotifyQueueEventHandlerTest {

    @Autowired
    private NotifyQueueEventHandler notifyQueueEventHandler;

    @Test
    public void test() {
        NotifyContext notifyContext = NotifyTestSupport.createNotifyContext();
        notifyQueueEventHandler.handle(notifyContext);

    }
}

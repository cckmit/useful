package me.andpay.ac.wpn.srv.notify;

import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import me.andpay.ti.util.SleepUtil;
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
public class WeiXinMsgHandlerTest {


    @Autowired
    private WeiXinMsgHandler weiXinMsgHandler;

    @Test
    public void test() {

        NotifyContext notifyContext = NotifyTestSupport.createNotifyContext();
        String result = weiXinMsgHandler.handle(notifyContext);

        SleepUtil.sleep(10000l);
    }
}

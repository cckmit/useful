package me.andpay.ac.wpn.srv.notify;

import me.andpay.ac.wpn.api.model.WeixinNotifyRequest;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.service.WeixinNotifyReceiverService;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by cen on 2017/2/23.
 */

@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/notify/ac-wpn-srv-test-notify-config.xml" })
public class WeixinNotifyReceiverServiceTest {

    @Autowired
    private WeixinNotifyReceiverService weixinNotifyReceiverService;

    @Autowired
    private DataInitService dataInitService;
    @Test
    public void test() throws Exception{

        WxPublicNumber publicNumber = dataInitService.createPublicNumber();

        WeixinNotifyRequest weixinNotifyRequest = new WeixinNotifyRequest();
        weixinNotifyRequest.setWxPublicNumberId(publicNumber.getWxPublicNumberId());
        weixinNotifyRequest.setNotifyContent("1911687713");
        weixinNotifyRequest.setEchostr(null);
        weixinNotifyRequest.setSignature("127fbda7d9072f22a67748627fb15578cdc3e4e3");
        weixinNotifyRequest.setTimestamp("1488196341");
        weixinNotifyRequest.setNonce("1911687713");
        weixinNotifyRequest.setNotifyContent("<xml><ToUserName><![CDATA[gh_09b7d3a2fd2a]]></ToUserName><FromUserName><![CDATA[oLjPPs-djjn7LvZiwv1ynUUGEbSs]]></FromUserName><CreateTime>1488196341</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[SCAN]]></Event><EventKey><![CDATA[1]]></EventKey><Ticket><![CDATA[gQHM8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyQWo5STFlS1BiZlAxdzlMUU5vMTgAAgT5B7RYAwQQJwAA]]></Ticket></xml>");

        String result = weixinNotifyReceiverService.receive(weixinNotifyRequest);

    }
}

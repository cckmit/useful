package me.andpay.ac.wpn.srv;

import me.andpay.ac.wpn.api.service.WeixinBaseService;
import me.andpay.ac.wpn.srv.service.DataInitServiceImpl;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import me.andpay.ti.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by cen on 2017/2/16.
 */
@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/ac-wpn-srv-test-base-config.xml" })
public class WeixinBaseServiceTest {

    @Autowired
    private WeixinBaseService weixinBaseService;

    @Test
    public void test() throws Exception {
        String longUrlReq = "http://www.baidu.com";
        String shortUrl = weixinBaseService.shortUrlToLongUrl(longUrlReq, DataInitServiceImpl.APPID);
        Assert.assertNotNull(shortUrl);
    }
}

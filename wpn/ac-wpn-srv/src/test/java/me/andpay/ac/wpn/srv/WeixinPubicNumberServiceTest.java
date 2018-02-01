package me.andpay.ac.wpn.srv;

import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.model.WxPublicNumberResponse;
import me.andpay.ac.wpn.api.service.WeixinPubicNumberService;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

/**
 * Created by cen on 16/11/4.
 */
@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/ac-wpn-srv-test-weixinpublicNumber-config.xml" })
public class WeixinPubicNumberServiceTest {

    @Autowired
    private WeixinPubicNumberService weixinPubicNumberService;
    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;

    @Test
    public void obtainWeiPublicNumber() throws Exception {

        String appid = "wx422ed696491f0e35";

        WxPublicNumber wxPublicNumber = new WxPublicNumber();
        wxPublicNumber.setAppId(appid);
        wxPublicNumber.setCreateTime(new Date());
        wxPublicNumber.setUpdateTime(new Date());
        wxPublicNumber.setName("我的测试号");
        wxPublicNumber.setSecret("5607542770dfd2572e0fbb10ab26b80a");
        wxPublicNumberDao.add(wxPublicNumber);

        WxPublicNumberResponse wxPublicNumberResponse =  weixinPubicNumberService.obtainWeiPublicNumber(1l);
        Assert.assertNotNull(wxPublicNumberResponse.getAppId());
        Assert.assertNotNull(wxPublicNumberResponse.getName());
        Assert.assertEquals(wxPublicNumber.getAppId(),appid);
    }

}

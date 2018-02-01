package me.andpay.ac.wpn.srv.notify.scene;

import me.andpay.ac.consts.wpn.AppAlais;
import me.andpay.ac.consts.wpn.QRCodeBizTypes;
import me.andpay.ac.consts.wpn.QRCodeParamNames;
import me.andpay.ac.consts.wpn.QRCodeTypes;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.api.model.db.WxBindUser;
import me.andpay.ac.wpn.api.model.db.WxPublicNumberUser;
import me.andpay.ac.wpn.srv.dao.WeixinQRCodeDao;
import me.andpay.ac.wpn.srv.dao.WxBindUserDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberUserDao;
import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ac.wpn.srv.service.DataInitServiceImpl;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import me.andpay.ti.util.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static me.andpay.ac.wpn.srv.notify.NotifyTestSupport.createNotifyContext;

/**
 * Created by cen on 2017/3/29.
 */
@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:spring-config/notify/ac-wpn-srv-test-notify-config.xml"})
public class BindPartySceneProcessorTest {

    @Autowired
    @Qualifier("ac-wpn.BindPartySceneProcessor")
    private BindPartySceneProcessor bindPartySceneProcessor;

    @Autowired
    private DataInitService dataInitService;

    @Autowired
    private WeixinQRCodeDao weixinQRCodeDao;

    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;

    @Autowired
    private WxBindUserDao wxBindUserDao;

    @Test
    public void test() {

        WeixinQRCode weixinQRCode = createWeixinQrCode();

        NotifyContext notifyContext = createNotifyContext();
        Map<String,String> xmlData = notifyContext.getXmlData();

        bindPartySceneProcessor.process(weixinQRCode,notifyContext);

        WxBindUser wxBindUser = wxBindUserDao.get(1l);
        Assert.assertNotNull(wxBindUser);

        WxPublicNumberUser wxPublicNumberUser =  wxPublicNumberUserDao.get(1l);
        Assert.assertNotNull(wxPublicNumberUser);
        Assert.assertEquals(wxPublicNumberUser.getUserId(),wxBindUser.getUserId());


    }

    public WeixinQRCode createWeixinQrCode() {

        WeixinQRCode weixinQRCode = new WeixinQRCode();
        weixinQRCode.setAppId(DataInitServiceImpl.APPID);
        weixinQRCode.setQrCodeShortUrl("http://weixinqr/121323");
        weixinQRCode.setCreateTime(new Date());
        weixinQRCode.setUpdateTime(weixinQRCode.getCreateTime());
        weixinQRCode.setSceneId("12312313");
        weixinQRCode.setTicket("1241231312");
        weixinQRCode.setQrCodeBizType(QRCodeBizTypes.QRBT_BIND_PARTY_CODE);
        weixinQRCode.setQrCodeType(QRCodeTypes.QRT_SCAN);
        weixinQRCode.setQrCodeContent("http://weixin.qq.com/q/02KCk506KPbfP1XV4rho12");

        Map<String,String> jsonMap = new HashMap<String, String>();
        jsonMap.put(QRCodeParamNames.PARTY_ID,"1213131413123123123");
        jsonMap.put(QRCodeParamNames.USERNAME,"12313123123");
        jsonMap.put(QRCodeParamNames.BIND_PARTY_TARGET, AppAlais.ANDPAY_SERVICE_PLATFORM);
        weixinQRCode.setParamJson(JSON.getDefault().toJSONString(jsonMap));

        Long  qrCodeParmaId = weixinQRCodeDao.add(weixinQRCode);
        weixinQRCode.setWeixinQRCodeId(qrCodeParmaId);
        return weixinQRCode;
    }

}

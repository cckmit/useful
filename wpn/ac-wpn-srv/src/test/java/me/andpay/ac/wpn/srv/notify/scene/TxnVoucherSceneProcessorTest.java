package me.andpay.ac.wpn.srv.notify.scene;

import me.andpay.ac.consts.wpn.BindUserStatuses;
import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.api.model.db.WxBindCardHolder;
import me.andpay.ac.wpn.api.model.db.WxPublicNumberUser;
import me.andpay.ac.wpn.srv.dao.WxBindCardHolderDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberUserDao;
import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ac.wpn.srv.notify.NotifySupport;
import me.andpay.ac.wpn.srv.notify.NotifyTestSupport;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ac.wpn.srv.service.DataInitServiceImpl;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

/**
 * Created by cen on 2017/2/27.
 */
@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:spring-config/notify/ac-wpn-srv-test-notify-config.xml"})
public class TxnVoucherSceneProcessorTest {


    @Autowired
    @Qualifier("ac-wpn.TxnVoucherSceneProcessor")
    private SceneQrCodeProcessor txnVoucherSceneProcessor;

    @Autowired
    private DataInitService dataInitService;

    @Autowired
    private TestEventConsumer testEventConsumer;

    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;

    @Autowired
    private WxBindCardHolderDao wxBindCardHolderDao;

    @Test
    public void test() {

        WeixinQRCode weixinQRCode = dataInitService.createWeixinQrCode();

        NotifyContext notifyContext = NotifyTestSupport.createNotifyContext();
        Map<String,String> xmlData = notifyContext.getXmlData();
        testEventConsumer.setData(weixinQRCode,notifyContext);

        String result = txnVoucherSceneProcessor.process(weixinQRCode,notifyContext);
        assertEquals(NotifySupport.NOTIFY_COMPLITE,result);

        WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.get(1l);
        WxBindCardHolder cardHolder = wxBindCardHolderDao.get(1l);

        assertEquals(wxPublicNumberUser.getAppid(),DataInitServiceImpl.APPID);
        assertEquals(wxPublicNumberUser.getOpenid(),xmlData.get(WeiXinXmlKeys.FROM_USER_NAME));
        assertNotNull(wxPublicNumberUser.getCreateTime());
        assertNotNull(wxPublicNumberUser.getUpdateTime());

        assertEquals(cardHolder.getUserId(),wxPublicNumberUser.getUserId());
        assertEquals(cardHolder.getBindStatus(), BindUserStatuses.UBDS_BIND);
        assertNotNull(cardHolder.getCardNOToken());
        assertNotNull(cardHolder.getCreateTime());
        assertNotNull(cardHolder.getUpdateTime());

    }


}

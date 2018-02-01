package me.andpay.ac.wpn.srv;

import me.andpay.ac.consts.SystemIds;
import me.andpay.ac.consts.wpn.AppAlais;
import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendTempMsgToCardHolderRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ac.wpn.api.model.SendWeixinMessageResult;
import me.andpay.ac.wpn.api.model.db.WxBindCardHolder;
import me.andpay.ac.wpn.api.model.db.WxMessageTemplate;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.srv.dao.WxBindCardHolderDao;
import me.andpay.ac.wpn.srv.dao.WxBindUserDao;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ac.wpn.srv.service.WxDbInitData;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import me.andpay.ti.vault.client.SimpleTokenClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cen on 2016/11/16.
 */

@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/ac-wpn-srv-test-base-config.xml" })
public class WeixinMessageServiceTest {


    @Autowired
    private WeixinMessageService weixinMessageService;

    @Autowired
    private WxBindUserDao wxBindUserDao;

    @Autowired
    private DataInitService dataInitService;

    @Autowired
    @Qualifier("ac-wpn.CardNoTokenClient")
    private SimpleTokenClient simpleTokenClient;

    @Autowired
    private WxBindCardHolderDao wxBindCardHolderDao;

    @Test
    public void testSendMessage() throws Exception{

        WxDbInitData wxDbInitData = dataInitService.createInitData();
        WxMessageTemplate wxMessageTemplate = dataInitService.createWxTemplateMessage();

        final SendTempMsgToBindUserRequest sendTempMsgToBindUserRequest = new SendTempMsgToBindUserRequest();
        sendTempMsgToBindUserRequest.setTemplateName(wxMessageTemplate.getTemplateName());
        sendTempMsgToBindUserRequest.setPartyId(wxDbInitData.getWxBindUser().getPartyId());
        sendTempMsgToBindUserRequest.setUserName(wxDbInitData.getWxBindUser().getUserName());
        sendTempMsgToBindUserRequest.setSourceSystemId(SystemIds.AC_WPN);
        sendTempMsgToBindUserRequest.setTraceNo("123456789");
        sendTempMsgToBindUserRequest.setTarget(AppAlais.ANDPAY_PARTNER);

        Map<String,String> urlparams = new HashMap<String, String>();
        urlparams.put("test","001");
        sendTempMsgToBindUserRequest.setUrlParams(urlparams);

//        sendTempMsgRequest.setUrl("http://www.baidu.com");
        Map<String,String> params = new HashMap<String, String>();
        params.put("amt#0","1000000.00");
        params.put("amt#1","999.00");
        params.put("content","好哈");


        sendTempMsgToBindUserRequest.setParams(params);

        weixinMessageService.sendTemplateMessageToBindUser(sendTempMsgToBindUserRequest, new SendWeixinMessageCallback() {
            @Override
            public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                assertTrue(sendWeixinMessageResult.isSuccess());
                assertEquals(sendTempMsgToBindUserRequest.getTraceNo(),sendWeixinMessageResult.getTraceNo());
            }
        });

        WxBindCardHolder wxBindCardHolder = wxDbInitData.getWxBindCardHolder();

        SendTempMsgToCardHolderRequest sendTempMsgToCardHolderRequest = new SendTempMsgToCardHolderRequest();
        sendTempMsgToCardHolderRequest.setCardNo(DataInitService.CARDNO);
        sendTempMsgToCardHolderRequest.setSourceSystemId(SystemIds.AC_WPN);
        sendTempMsgToCardHolderRequest.setTraceNo("123456789");
        sendTempMsgToCardHolderRequest.setParams(params);
        sendTempMsgToCardHolderRequest.setTemplateName(wxMessageTemplate.getTemplateName());
        sendTempMsgToCardHolderRequest.setUrlParams(urlparams);
        sendTempMsgToCardHolderRequest.setTarget(AppAlais.ANDPAY_PARTNER);



        weixinMessageService.sendTemplateMessageToCardHolder(sendTempMsgToCardHolderRequest, new SendWeixinMessageCallback() {
            @Override
            public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                assertTrue(sendWeixinMessageResult.isSuccess());
                assertEquals(sendTempMsgToBindUserRequest.getTraceNo(),sendWeixinMessageResult.getTraceNo());
            }
        });



    }
}

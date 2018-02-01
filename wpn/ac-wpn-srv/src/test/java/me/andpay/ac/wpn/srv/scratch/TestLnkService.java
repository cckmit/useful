package me.andpay.ac.wpn.srv.scratch;

import me.andpay.ac.consts.SystemIds;
import me.andpay.ac.consts.wpn.*;
import me.andpay.ac.wpn.api.consts.MessageAppIds;
import me.andpay.ac.wpn.api.consts.RedisPrefixKeys;
import me.andpay.ac.wpn.api.consts.SwitchConfigTargetTypes;
import me.andpay.ac.wpn.api.model.*;
import me.andpay.ac.wpn.api.service.MessageSwitchConfigService;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.api.service.WeixinQRCodeService;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ac.wpn.srv.service.DataInitServiceImpl;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.data.redis.RedisTemplate;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cen on 2017/2/13.
 */
public class TestLnkService {

    @Lnkwired
    private WeixinMessageService messageService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Lnkwired
    private WeixinQRCodeService qrCodeService;


    @Lnkwired
    private MessageSwitchConfigService switchConfigService;



    public void test() throws Exception {
        String redisKey = RedisPrefixKeys.REDIS_ACCESS_TOKEN_PREFIX+ DataInitServiceImpl.APPID;

//        redisTemplate.removeValue(redisKey);
//        fahuoTongzhi();

//        daoZhangtongzhi();
//        createQrCode();
//        accountChange();

        swichConfigTest();

        System.exit(0);
    }


    private void swichConfigTest() throws AppBizException {
        QuerySwitchListReq querySwitchListReq = new QuerySwitchListReq();
        querySwitchListReq.setAppId(MessageAppIds.MSA_PTN);
        querySwitchListReq.setTarget(DataInitService.PATYID);
        querySwitchListReq.setTargetType(SwitchConfigTargetTypes.SCT_PARTY);

        List<SwitchItem> switchItemList = switchConfigService.querySwitchList(querySwitchListReq);

        UpdateSwitchConfigReq updateSwitchConfigReq = new UpdateSwitchConfigReq();
        updateSwitchConfigReq.setAppId(MessageAppIds.MSA_PTN);
        updateSwitchConfigReq.setTarget(DataInitService.PATYID);
        updateSwitchConfigReq.setTargetType(SwitchConfigTargetTypes.SCT_PARTY);
        updateSwitchConfigReq.setSwitchItems(switchItemList);
        switchItemList.get(0).setOpen(false);
        switchConfigService.updateSwitchConfig(updateSwitchConfigReq);


        switchItemList = switchConfigService.querySwitchList(querySwitchListReq);
        System.out.print(switchItemList);
    }


    private void accountChange() throws Exception {

        final SendTempMsgToBindUserRequest request = new SendTempMsgToBindUserRequest();
        request.setTarget(AppAlais.ANDPAY_PARTNER);
        request.setTemplateName(TemplateNames.AC_OSS_DELIVER_GOODS_NOTIFY_TEMPLATE);
        request.setPartyId("1014816000006915");
        request.setUserName("15618691111");
        request.setSourceSystemId(SystemIds.AC_WPN);
        request.setTraceNo("123456789");

        Map<String,String> params = new HashMap<String, String>();
        params.put("first","岑谱洲");
        params.put("keyword1","2017/02/21 22:00");
        params.put("keyword2","300.00");
        params.put("keyword3","300.00");
        //        params.put("remark","谢谢");

        request.setParams(params);

        String redisKey = RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX+"wx422ed696491f0e35";

        messageService.sendTemplateMessageToBindUser(request, new SendWeixinMessageCallback() {
            @Override
            public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                System.out.print(sendWeixinMessageResult.isSuccess());
            }
        });
    }

    private void createQrCode() throws Exception{
        CreateSceneQRCodeRequest sceneQRCodeRequest = new CreateSceneQRCodeRequest();
        sceneQRCodeRequest.setQrCodeBizType(QRCodeBizTypes.QRBT_TXN_VOUCHER_CODE);
        sceneQRCodeRequest.setExpireSeconds(10000l);
        sceneQRCodeRequest.setAppAlais(AppAlais.ANDPAY_PARTNER);
        sceneQRCodeRequest.setQrCodeType(QRCodeTypes.QRT_SCAN);
        Map<String,String> paramData = new HashMap<String, String>();
        paramData.put(QRCodeParamNames.TXN_ID,"001212");
        paramData.put(QRCodeParamNames.CARD_NO_TOKEN,"21313123123");
        sceneQRCodeRequest.setQrParams(paramData);
        QRCodeResult qrCodeResult = qrCodeService.createSceneQRCode(sceneQRCodeRequest);
        System.out.print(qrCodeResult);
    }

    private void fahuoTongzhiToCardHolder() throws Exception{

        final SendTempMsgToCardHolderRequest request = new SendTempMsgToCardHolderRequest();
        request.setTemplateName(TemplateNames.AC_OSS_DELIVER_GOODS_NOTIFY_TEMPLATE);
        request.setCardNo("21313123123");
        request.setSourceSystemId(SystemIds.AC_WPN);
        request.setTraceNo("123456789");

        Map<String,String> params = new HashMap<String, String>();
        params.put("first","岑谱洲");
        params.put("keyword1","NPOS 8S");
        params.put("keyword2","顺丰物流");
        params.put("keyword3","WX112312313");
        params.put("keyword4","上海上海 岑谱洲");
        //        params.put("remark","谢谢");

        request.setParams(params);

        String redisKey = RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX+"wx422ed696491f0e35";

        messageService.sendTemplateMessageToCardHolder(request, new SendWeixinMessageCallback() {
            @Override
            public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                System.out.print(sendWeixinMessageResult.isSuccess());
            }
        });
    }


    private void fahuoTongzhi() throws Exception{
            SendTempMsgToBindUserRequest request = new SendTempMsgToBindUserRequest();

            final SendTempMsgToBindUserRequest sendTempMsgToBindUserRequest = new SendTempMsgToBindUserRequest();
            request.setTemplateName(TemplateNames.AC_OSS_DELIVER_GOODS_NOTIFY_TEMPLATE);
            request.setTarget(AppAlais.ANDPAY_PARTNER);
            request.setPartyId("1014816000006915");
            request.setUserName("15618691111");
            request.setSourceSystemId(SystemIds.AC_WPN);
            request.setTraceNo("123456789");

            Map<String,String> params = new HashMap<String, String>();
            params.put("first","岑谱洲");
            params.put("keyword1","NPOS 8S");
            params.put("keyword2","顺丰物流");
            params.put("keyword3","WX112312313");
            params.put("keyword4","上海上海 岑谱洲");
    //        params.put("remark","谢谢");

            request.setParams(params);


            messageService.sendTemplateMessageToBindUser(request, new SendWeixinMessageCallback() {
                @Override
                public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                    System.out.print(sendWeixinMessageResult.isSuccess());
                }
            });
        }

    private void daoZhangtongzhi() throws Exception {
        SendTempMsgToBindUserRequest request = new SendTempMsgToBindUserRequest();


        final SendTempMsgToBindUserRequest sendTempMsgToBindUserRequest = new SendTempMsgToBindUserRequest();
        request.setTemplateName("ac-caws.withdraw-succ-notify-template");
        request.setPartyId("1014816000006915");
        request.setUserName("15618691111");
        request.setSourceSystemId(SystemIds.AC_WPN);
        request.setTraceNo("123456789");

        Map<String,String> params = new HashMap<String, String>();
        params.put("first","岑谱洲");
        params.put("keyword1","2017-2-13");
        params.put("keyword2","招商银行");
        params.put("keyword3","100.00");
        params.put("keyword4","1.00");
        params.put("keyword5","99.00");
//        params.put("remark","谢谢");



        request.setParams(params);

        String redisKey = RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX+"wx422ed696491f0e35";

        messageService.sendTemplateMessageToBindUser(request, new SendWeixinMessageCallback() {
            @Override
            public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                System.out.print(sendWeixinMessageResult.isSuccess());

            }
        });
    }
}

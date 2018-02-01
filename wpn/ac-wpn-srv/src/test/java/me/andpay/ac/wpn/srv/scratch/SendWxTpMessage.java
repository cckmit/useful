package me.andpay.ac.wpn.srv.scratch;

import me.andpay.ac.consts.SystemIds;
import me.andpay.ac.consts.wpn.TemplateNames;
import me.andpay.ac.wpn.api.consts.RedisPrefixKeys;
import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ac.wpn.api.model.SendWeixinMessageResult;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.inter.api.dto.WxTpMessage;
import me.andpay.ac.wpn.inter.api.service.SendWxTpMessageCallback;
import me.andpay.ac.wpn.inter.api.service.SendWxTpMessageService;
import me.andpay.ti.data.redis.RedisTemplate;
import me.andpay.ti.util.JsonpathContext;
import me.andpay.ti.util.SleepUtil;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cen on 2016/11/17.
 */
public class SendWxTpMessage {

    public static void main(String[] args) throws Exception{

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring-config/ac-wpn-srv-config.xml" });
        WeixinMessageService weixinMessageService = context.getBean(WeixinMessageService.class);
        SendTempMsgToBindUserRequest request = new SendTempMsgToBindUserRequest();

        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);

        final SendTempMsgToBindUserRequest sendTempMsgToBindUserRequest = new SendTempMsgToBindUserRequest();
        request.setTemplateName("ac-caws.withdraw-succ-notify-template");
        request.setPartyId("1014816000006915");
        request.setUserName("15618691111");
        request.setSourceSystemId(SystemIds.AC_WPN);
        request.setTraceNo("123456789");

        Map<String,String> params = new HashMap<String, String>();
        params.put("amt","1000000.00");
        request.setParams(params);

        String redisKey = RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX+"wx422ed696491f0e35";
        redisTemplate.removeValue(redisKey);

        weixinMessageService.sendTemplateMessageToBindUser(request, new SendWeixinMessageCallback() {
            @Override
            public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                System.out.print(sendWeixinMessageResult.isSuccess());
            }
        });



    }
}

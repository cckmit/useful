package me.andpay.ac.wpn.srv.scratch;

import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ti.util.XmlUtils;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by cen on 2017/2/13.
 */
public class TestLnkMain {



    public static void main(String[] args) throws Exception {

       AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring-config/ac-wpn-srv-test-simple-config.xml" });

       TestLnkService testLnkService = context.getBean(TestLnkService.class);


       testLnkService.test();


//        String msg = "<xml>" +
//                "<ToUserName><![CDATA[toUser]]></ToUserName>" +
//                "<FromUserName><![CDATA[fromUser]]></FromUserName>" +
//                "<CreateTime>1348831860</CreateTime>" +
//                "<MsgType><![CDATA[text]]></MsgType>" +
//                "<Content><![CDATA[this is a test]]></Content>" +
//                "<MsgId>1234567890123456</MsgId>" +
//                "</xml>";
//
//        XmlUtils xmlUtils = new XmlUtils("xml");
//        Map<String,String> resultData = xmlUtils.parse(msg);
//
//        System.out.print(resultData);

    }






}

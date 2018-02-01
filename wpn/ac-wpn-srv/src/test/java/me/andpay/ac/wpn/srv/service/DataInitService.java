package me.andpay.ac.wpn.srv.service;

import me.andpay.ac.wpn.api.model.db.*;

import java.util.List;


/**
 * Created by cen on 2016/11/16.
 * 测试数据初始化服务
 */
public interface DataInitService {

    String  CARDNO = "622322312313123213";

    String APPID = "wx422ed696491f0e35";

    String SECRET = "5607542770dfd2572e0fbb10ab26b80a";


    String PATYID = "1012222000004168";


    WxDbInitData createInitData();

    WxPublicNumber createPublicNumber();

    WxBindUser createBindUser(Long userId);

    WxPublicNumberUser createPublicNumberUser();

    WxMessageTemplate createWxTemplateMessage();

    WeixinQRCode createWeixinQrCode();

    WxBindCardHolder createWxBindCardHolder(Long userId);

    List<MessageSwitchConfig> createMessageSwitchConfigList();
}
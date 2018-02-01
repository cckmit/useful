package me.andpay.ac.wpn.srv.mock;


import me.andpay.ac.wpn.srv.dao.*;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cen on 2017/6/12.
 */
public class SpyDaos extends MockBeans{

    @Autowired
    @Spy
    protected MessageSwitchConfigDao switchConfigDao;

    @Autowired
    @Spy
    protected MessageRecordDao messageRecordDao;

    @Autowired
    @Spy
    protected MessageTemplateGroupDao messageTemplateGroupDao;

    @Autowired
    @Spy
    protected WeixinNotifyInfoDao weixinNotifyInfoDao;

    @Autowired
    @Spy
    protected WeixinQRCodeDao weixinQRCodeDao;

    @Autowired
    @Spy
    protected WxBindCardHolderDao wxBindCardHolderDao;

    @Autowired
    @Spy
    protected WxBindUserDao wxBindUserDao;

    @Autowired
    @Spy
    protected WxMessageTemplateDao wxMessageTemplateDao;

    @Autowired
    @Spy
    protected WxPublicNumberDao wxPublicNumberDao;

    @Autowired
    @Spy
    protected WxPublicNumberUserDao wxPublicNumberUserDao;

}


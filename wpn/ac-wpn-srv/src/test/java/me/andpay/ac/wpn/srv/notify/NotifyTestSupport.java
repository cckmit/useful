package me.andpay.ac.wpn.srv.notify;

import me.andpay.ac.wpn.api.consts.WeiXinEventNames;
import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.srv.service.DataInitServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cen on 2017/2/27.
 */
public class NotifyTestSupport {

    public static NotifyContext createNotifyContext() {

        NotifyContext notifyContext = new NotifyContext();
        Map<String,String> xmlData = new HashMap<String, String>();
        xmlData.put(WeiXinXmlKeys.FROM_USER_NAME,"cen123456");
        xmlData.put(WeiXinXmlKeys.EVENT, WeiXinEventNames.SUBSCIBE);
        xmlData.put(WeiXinXmlKeys.MSG_TYPE,"event");
        notifyContext.setXmlData(xmlData);
        notifyContext.setAppId(DataInitServiceImpl.APPID);

        return notifyContext;
    }
}

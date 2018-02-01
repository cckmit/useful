package me.andpay.ac.wpn.srv.base.util;

import me.andpay.ac.wpn.api.consts.AppExCodes;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.util.JsonpathContext;
import me.andpay.ti.util.StringUtil;

/**
 * 微信相关工具
 * Created by cen on 2017/2/17.
 */
public class WeiXinUtil {

    /**
     * 判断返回结果是否正常，并返回json上下文
     * @param exCode
     * @param respMsg
     * @return
     */
    public static JsonpathContext responseDeal(String exCode,String respMsg){
        JsonpathContext ctx = JsonpathContext.parse(respMsg);
        String errorCode = ctx.getValue("$.errcode",String.class);
        if(StringUtil.isNotBlank(errorCode)&&!"0".equals(errorCode)){
            throw new AppRtException(exCode,respMsg);
        }
        return ctx;
    }
}

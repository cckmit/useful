package me.andpay.ac.wpn.srv.service;

import me.andpay.ac.wpn.api.consts.AppExCodes;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.api.service.WeixinBaseService;
import me.andpay.ac.wpn.srv.base.util.WeiXinUtil;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信基础服务
 * Created by cen on 2017/2/16.
 */
public class WeixinBaseServiceImpl implements WeixinBaseService {


    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 长链接转短链接
     */
    private static final String ACTION_LONG_TO_SHORT = "long2short";
    /**
     * 短链接转长链接,暂时不支持
     */
    private static final String ACTION_SHORT_TO_LONG = "short2long";
    /**
     * 微信主域
     */
    private String weixinDomain;

    @Autowired
    private WeixinAuthService weixinAuthService;

    @Autowired
    private SimpleHttpClient simpleHttpClient;


    private String postToWeixin(String action,String targetUrl,String appId) throws AppBizException {
        String accessToken = weixinAuthService.obtainAccessToken(appId);
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);

        SimpleHttpClient.HttpReq httpReq = new SimpleHttpClient.HttpReq();
        httpReq.setUrl(URLUtil.assembleUrl(weixinDomain,
                "https://", "/cgi-bin/shorturl", params));

        Map<String,String> postData = new HashMap<String, String>();
        postData.put("action",action);
        if(ACTION_LONG_TO_SHORT.equals(action)) {
            postData.put("long_url",targetUrl);
        }else {
            postData.put("short_url",targetUrl);
        }
        httpReq.setContent(JSON.getDefault().toJSONString(postData));
        SimpleHttpClient.HttpResp httpResp  = simpleHttpClient.doPost(httpReq);

        JsonpathContext ctx = WeiXinUtil.responseDeal(AppExCodes.LONG_SHORT_URL_CONVERT_ERROR,httpResp.getContent());

        String resultUrl = "";
        if(ACTION_LONG_TO_SHORT.equals(action)) {
            return ctx.getValue("$.short_url",String.class);
        }else {
            return ctx.getValue("$.long_url",String.class);
        }

    }

    @Override
    public String shortUrlToLongUrl(String longUrl,String appId) throws AppBizException {
        logger.info("shorturl to longurl longUrl=[{}],appid=[{}]",new String[]{longUrl,appId});
        return postToWeixin(ACTION_LONG_TO_SHORT,longUrl,appId);
    }




    public void setWeixinDomain(String weixinDomain) {
        this.weixinDomain = weixinDomain;
    }
}

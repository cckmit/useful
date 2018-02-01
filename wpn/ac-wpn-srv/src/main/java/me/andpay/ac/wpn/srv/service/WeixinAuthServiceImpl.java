package me.andpay.ac.wpn.srv.service;

import me.andpay.ac.wpn.api.consts.AppExCodes;
import me.andpay.ac.wpn.api.consts.RedisPrefixKeys;
import me.andpay.ac.wpn.api.model.*;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberCond;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.api.service.WeixinPubicNumberService;
import me.andpay.ac.wpn.inter.api.util.SimpleDigestUtil;
import me.andpay.ac.wpn.srv.base.util.WeiXinUtil;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.data.redis.RedisTemplate;
import me.andpay.ti.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

/**
 * Created by cen on 16/11/2.
 * 微信授权服务
 */
public class WeixinAuthServiceImpl implements WeixinAuthService {


    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SimpleHttpClient simpleHttpClient;

    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;

    @Autowired
    private WeixinPubicNumberService weixinPubicNumberService;


    /**
     * redis的超时时间(秒)
     */
    private int redisTimeout = 6000;

    /**
     * 微信主域
     */
    private String weixinDomain;


    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    private String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 生成签名数据
     *
     * @param noncestr
     * @param timestamp
     * @param ticket
     * @param url
     * @return
     */
    private String genSign(String noncestr, String timestamp, String ticket, String url) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("noncestr", noncestr);
        treeMap.put("timestamp", timestamp);
        treeMap.put("jsapi_ticket", ticket);
        treeMap.put("url", url);

        StringBuffer strbut = new StringBuffer();
        for (String key : treeMap.keySet()) {
            strbut.append(key).append("=").append(treeMap.get(key)).append("&");
        }
        strbut.deleteCharAt(strbut.toString().length() - 1);

        String sign = SimpleDigestUtil.simpleDigest("SHA1", strbut.toString());

        return sign;
    }



    /**
     * 创建网页授权token返回值
     * @param ctx
     * @return
     */
    private AuthTwoAccessTokenResult createAuthTwoAccessTokenResult(JsonpathContext ctx){
        AuthTwoAccessTokenResult authTwoAccessTokenResult = new AuthTwoAccessTokenResult();
        authTwoAccessTokenResult.setAccessToken(ctx.getValue("$.access_token",String.class));
        authTwoAccessTokenResult.setExpiresIn(ctx.getValue("$.expires_in",Integer.class));
        authTwoAccessTokenResult.setOpenId(ctx.getValue("$.openid",String.class));
        authTwoAccessTokenResult.setRefreshToken(ctx.getValue("$.refresh_token",String.class));
        authTwoAccessTokenResult.setScope(ctx.getValue("$.scope",String.class));
        authTwoAccessTokenResult.setUnionid(ctx.getValue("$.unionid",String.class));
        return authTwoAccessTokenResult;
    }

    public String obtainAccessToken(String appId) throws AppBizException {

        logger.info("start obtain weixin access token,appId=[{}]", new Object[]{appId});

        try {

            String redisKey = RedisPrefixKeys.REDIS_ACCESS_TOKEN_PREFIX + appId;
            String cacheAccessToken = redisTemplate.getValue(redisKey);
            if (StringUtil.isNotBlank(cacheAccessToken)) {
                logger.info("obtain Weixin cache token=[{}], appId=[{}]", new Object[]{cacheAccessToken, appId});
                return cacheAccessToken;
            }

            QueryWxPublicNumberCond wpnCond = new QueryWxPublicNumberCond();
            wpnCond.setAppId(appId);
            List<WxPublicNumber> wxPublicNumbers = wxPublicNumberDao.query(wpnCond,0,-1);
            if (wxPublicNumbers.size() == 0) {
                throw new AppBizException(AppExCodes.WEIXIN_ACCESS_TOKEN_ERROR, "wxPublicNumber not found appId="+appId);
            }
            WxPublicNumber wxPublicNumber = wxPublicNumbers.get(0);
            Map<String, String> params = new HashMap<String, String>();
            params.put("grant_type", "client_credential");
            params.put("appid", appId);
            params.put("secret", wxPublicNumber.getSecret());

            String response = simpleHttpClient.doGet(URLUtil.assembleUrl(weixinDomain, "https://", "/cgi-bin/token", params));
            JsonpathContext ctx = WeiXinUtil.responseDeal(AppExCodes.WEIXIN_ACCESS_TOKEN_ERROR,response);

            String accessToken = ctx.getValue("$.access_token",String.class);
            if (StringUtil.isBlank(accessToken)) {
                throw new AppBizException(AppExCodes.WEIXIN_ACCESS_TOKEN_ERROR, response);
            }

            redisTemplate.setValue(redisKey, accessToken, redisTimeout);

            logger.info("obtain Weixin access token=[{}],appId=[{}] ", new Object[]{accessToken, appId});

            return accessToken;

        } catch (AppBizException ex) {
            throw ex;
        } catch (AppRtException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppRtException(AppExCodes.SYS_ERROR, e);
        }
    }

    public String obtainJsapiTicket(String appId) throws AppBizException {

        logger.info("start obtain Weixin JSAPI ticket,appid=[{}]", new Object[]{appId});

        try {
            String redisKey = RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX + appId;
            String cacheJSapiTicket = redisTemplate.getValue(redisKey);
            if (StringUtil.isNotBlank(cacheJSapiTicket)) {
                logger.info("obtain Weixin JSAPI cache ticket=[{}],appId=[{}]", new Object[]{cacheJSapiTicket, appId});

                return cacheJSapiTicket;
            }

            String accessToken = obtainAccessToken(appId);

            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", accessToken);
            params.put("type", "jsapi");

            String response = simpleHttpClient.doGet(URLUtil.assembleUrl(weixinDomain, "https://", "/cgi-bin/ticket/getticket", params));
            JsonpathContext ctx = WeiXinUtil.responseDeal(AppExCodes.WEIXIN_JSAPI_SIGN_ERROR,response);

            String ticket = ctx.getValue("$.ticket",String.class);
            if (StringUtil.isBlank(ticket)) {
                throw new AppBizException(AppExCodes.WEIXIN_API_TICKET_ERROR, response);
            }

            redisTemplate.setValue(redisKey, ticket, redisTimeout);

            logger.info("obtain Weixin JSAPI ticket=[{}],appId=[{}]", new Object[]{ticket, appId});
            return ticket;
        } catch (AppBizException ex) {
            throw ex;
        } catch (AppRtException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppRtException(AppExCodes.SYS_ERROR, e);
        }
    }

    public WxJSAPISignResult jsApiSign(String requestUrl, String appId) throws AppBizException {

        logger.info("start obtain WeixinApi requestUrl=[{}],appid=[{}] ", new Object[]{requestUrl, appId});

        String ticket = obtainJsapiTicket(appId);
        String noncestr = getRandomString(16);
        String timestamp = String.valueOf(System.currentTimeMillis());

        String sign = genSign(noncestr, timestamp, ticket, requestUrl);

        if (StringUtil.isBlank(sign)) {
            throw new AppBizException(AppExCodes.WEIXIN_JSAPI_SIGN_ERROR, requestUrl);
        }

        WxJSAPISignResult weixinJSAPISignResult = new WxJSAPISignResult();
        weixinJSAPISignResult.setNoncestr(noncestr);
        weixinJSAPISignResult.setTimestamp(timestamp);
        weixinJSAPISignResult.setSign(sign);

        logger.info("start obtain WeixinApi sign=[{}],requestUrl=[{}],appid=[{}]", new Object[]{sign, requestUrl, appId});

        return  weixinJSAPISignResult;
    }

    public WxJSAPISignResult jsApiSignWithAlias(String requestUrl, String alias) throws AppBizException {

        logger.info("start obtain WeixinApi requestUrl=[{}], alias=[{}]", new Object[]{requestUrl, alias});

        WxPublicNumberResponse wxPublicNumberResponse = weixinPubicNumberService.obtainWeiPublicNumberWithAlias(alias);

        String appId = wxPublicNumberResponse.getAppId();

        if(appId != null) {

            logger.info("start sign with appId = [{}]", new Object[]{appId});

            WxJSAPISignResult wxJSAPISignResult = this.jsApiSign(requestUrl, appId);

            wxJSAPISignResult.setAppId(appId);

            return wxJSAPISignResult;
        }

        logger.info("sign with Alias : no AppId found!");

        return new WxJSAPISignResult();
    }

    public AuthTwoAccessTokenResult obtainAuthTwoAccessToken(String appId, String code) throws AppBizException {

        logger.info("start obtain AuthTwoAccessToken appId=[{}],code=[{}] ", new Object[]{appId, code});
        if(StringUtil.isBlank(appId) && StringUtil.isBlank(code)) {
            throw new IllegalArgumentException("appid or code is null");
        }

        QueryWxPublicNumberCond wpnCond = new QueryWxPublicNumberCond();
        wpnCond.setAppId(appId);
        List<WxPublicNumber> wxPublicNumbers = wxPublicNumberDao.query(wpnCond,0,-1);
        if (wxPublicNumbers.size() == 0) {
            throw new AppBizException(AppExCodes.WEIXIN_ACCESS_TOKEN_ERROR, "wxPublicNumber not found appId="+appId);
        }
        WxPublicNumber wxPublicNumber = wxPublicNumbers.get(0);

        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appId);
        params.put("secret",wxPublicNumber.getSecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        String response = simpleHttpClient.doGet(URLUtil.assembleUrl(weixinDomain, "https://", "/sns/oauth2/access_token", params));
        JsonpathContext ctx = WeiXinUtil.responseDeal(AppExCodes.AUTH_TOW_ACCESS_TOKEN_ERROR,response);


        return createAuthTwoAccessTokenResult(ctx);
    }

    public AuthTwoAccessTokenResult refreshAuthTwoAccessToken(String refreshToken,String appId) throws AppBizException {

        logger.info("start refresh AuthTwoAccessKoken refreshToken=[{}],appId=[{}] ", new Object[]{refreshToken, appId});


        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appId);
        params.put("grant_type", "refresh_token");
        params.put("REFRESH_TOKEN", refreshToken);


        String response = simpleHttpClient.doGet(URLUtil.assembleUrl(weixinDomain, "https://", "/sns/oauth2/refresh_token", params));
        JsonpathContext ctx = WeiXinUtil.responseDeal(AppExCodes.REFRESH_AUTH_TOW_ACCESS_TOKEN_ERROR,response);

        return createAuthTwoAccessTokenResult(ctx);
    }



    public AuthUserInfo getAuthUserInfo(String accessToken,String openid) throws AppBizException{

        logger.info("start getAuthUserInfo accessToken=[{}],openid=[{}] ", new Object[]{accessToken, openid});


        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("openid", openid);
        params.put("lang", "zh_CN");

        String response = simpleHttpClient.doGet(URLUtil.assembleUrl(weixinDomain,
                "https://", "/sns/userinfo", params));

        JsonpathContext ctx = WeiXinUtil.responseDeal(AppExCodes.GET_AUTH_USER_ERROR,response);

        AuthUserInfo authUserInfo = JSON.getDefault().parseToObject(response,AuthUserInfo.class);
        authUserInfo.setSourceJson(response);
        return authUserInfo;
    }

    public boolean checkAccessToken(String accessToken,String openid) throws AppBizException{

        logger.info("start checkAccessToken accessToken=[{}],openid=[{}] ", new Object[]{accessToken, openid});


        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("openid", openid);
        params.put("lang", "zh_CN");

        String response = simpleHttpClient.doGet(URLUtil.assembleUrl(weixinDomain,
                "https://", "/sns/auth", params));

        JsonpathContext ctx = JsonpathContext.parse(response);
        String errorCode = ctx.getValue("$.errcode",String.class);
        if(StringUtil.isNotBlank(errorCode)&&!"0".equals(errorCode)){
           return  false;
        }

        return true;
    }

    public void setRedisTimeout(int redisTimeout) {
        this.redisTimeout = redisTimeout;
    }

    public void setWeixinDomain(String weixinDomain) {
        this.weixinDomain = weixinDomain;
    }
}

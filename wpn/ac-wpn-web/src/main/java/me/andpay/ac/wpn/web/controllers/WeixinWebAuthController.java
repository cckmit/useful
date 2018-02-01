package me.andpay.ac.wpn.web.controllers;

import me.andpay.ac.web.lib.cookies.CookieData;
import me.andpay.ac.web.lib.cookies.CookieHelper;
import me.andpay.ac.wpn.api.consts.AppExCodes;
import me.andpay.ac.wpn.api.model.WxPublicNumberResponse;
import me.andpay.ac.wpn.api.service.WeixinPubicNumberService;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.util.URLUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cen on 2017/2/13.
 */
@Controller
public class WeixinWebAuthController {


    /**
     * 微信主域名
     */
    private String wexinDomain;
    @Lnkwired
    private WeixinPubicNumberService pubicNumberService;


    private String createAuthUrl(WxPublicNumberResponse wxPublicNumberResponse,String redirect,String scope) {
       try {
           Map<String,String> params = new LinkedHashMap<String, String>();
           params.put("appid",wxPublicNumberResponse.getAppId());
           params.put("redirect_uri",URLEncoder.encode(redirect,"utf-8"));
           params.put("response_type","code");
           params.put("scope",scope);
           params.put("state",wxPublicNumberResponse.getAppId()+"#wechat_redirect");
           String authUrl = URLUtil.assembleUrl(wexinDomain,"https://","/connect/oauth2/authorize",params);
           return authUrl;
       }catch (Exception ex){
          throw new RuntimeException(ex);
       }

    }

    private void httpRedirect(String url,HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.sendRedirect(url);
        }catch (Exception ex) {
            throw new AppRtException(AppExCodes.SYS_ERROR,"send redirect error");
        }
    }

    @RequestMapping("wxauth/{refId}")
    public ModelAndView weixinStart(HttpServletRequest request,
                                    HttpServletResponse response,
                                    HttpSession httpSession,
                                    @PathVariable Long refId, String redirect,String scope) throws AppBizException {
//        //不重复授权
//        CookieData cookieData = CookieHelper.getCookieData(request.getCookies());
//        if(cookieData.getAppId() != null && cookieData.getOpenId()!=null) {
//            httpRedirect(redirect,response);
//            return null;
//        }

        WxPublicNumberResponse wxPublicNumberResponse = pubicNumberService.obtainWeiPublicNumber(refId);
        String authUrl = createAuthUrl(wxPublicNumberResponse,redirect,scope);
        httpRedirect(authUrl,response);

        return null;
    }



    public void setWexinDomain(String wexinDomain) {
        this.wexinDomain = wexinDomain;
    }
}

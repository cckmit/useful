package me.andpay.ac.wpn.web.controllers;

import me.andpay.ac.cif.api.model.Contact;
import me.andpay.ac.cif.api.model.PartyContact;
import me.andpay.ac.cif.api.party.PartyService;
import me.andpay.ac.consts.*;
import me.andpay.ac.consts.wpn.BindTypes;
import me.andpay.ac.ums.api.UserAuthService;
import me.andpay.ac.ums.api.UserLoginRequest;
import me.andpay.ac.ums.api.UserLoginResponse;
import me.andpay.ac.ums.api.UserManagementService;
import me.andpay.ac.ums.api.model.User;
import me.andpay.ac.ums.api.model.UserParty;
import me.andpay.ac.web.lib.cookies.CookieData;
import me.andpay.ac.web.lib.cookies.CookieHelper;
import me.andpay.ac.web.lib.cookies.CookieKeys;
import me.andpay.ac.wpn.api.model.AuthTwoAccessTokenResult;
import me.andpay.ac.wpn.api.model.AuthUserInfo;
import me.andpay.ac.wpn.api.model.WxBindUserInfo;
import me.andpay.ac.wpn.api.model.WxUserBindRequest;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.api.service.WeixinUserBindService;
import me.andpay.ac.wpn.web.model.AndpayBindRequest;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cen on 2017/2/21.
 */
@Controller
public class AndpayPublicNumberBindController {


    @Lnkwired
    private UserManagementService userManagementService;

    @Lnkwired
    private WeixinUserBindService weixinUserBindService;

    @Lnkwired
    private WeixinAuthService weixinAuthService;

    @Lnkwired
    private UserAuthService authService;

    @Lnkwired
    private PartyService partyService;

    @RequestMapping("andpayBind/index")
    public ModelAndView bindIndex(HttpServletRequest request,HttpServletResponse response,String code,String state) throws AppBizException {


        String appId = state;
        String openId;

        if(StringUtil.isNotBlank(code)&&StringUtil.isNotBlank(appId)) {

            AuthTwoAccessTokenResult authTwoAccessTokenResult = weixinAuthService.obtainAuthTwoAccessToken(appId,code);
            if(authService == null) {
                ModelAndView modelAndView = new ModelAndView("error");
                return modelAndView;
            }
            openId = authTwoAccessTokenResult.getOpenId();

            //保存cookie
            Cookie cookie = new Cookie(CookieKeys.CK_APP_ID, state);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie(CookieKeys.CK_OPEN_ID, authTwoAccessTokenResult.getOpenId());
            cookie.setPath("/");
            response.addCookie(cookie);
        }else {
            CookieData cookieData = CookieHelper.getCookieData(request.getCookies());
            openId = cookieData.getOpenId();
            appId = cookieData.getAppId();
        }

        
        WxBindUserInfo wxBindUserInfo = weixinUserBindService.obtainOneBindUser(appId,openId,BindTypes.BDT_APU);
        if(wxBindUserInfo != null) {
            //已经授权
            ModelAndView modelAndView = new ModelAndView("accountBind/bind_success");
            return  modelAndView;
        }else {
            //授权页面
            ModelAndView modelAndView = new ModelAndView("accountBind/account_bind");
            modelAndView.addObject("appId",appId);
            modelAndView.addObject("openId",openId);
            return modelAndView;
        }

    }

    @ResponseBody
    @RequestMapping("andpayBind/binding")
    public void bind(HttpServletRequest httpServletRequest, AndpayBindRequest andpayBindRequest) throws AppBizException {

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName(andpayBindRequest.getUserName());
        userLoginRequest.setPassword(andpayBindRequest.getPassword());
        Set<String> userTypes = new HashSet<String>();
        userTypes.add(UserTypes.TERM_USER);
        userLoginRequest.setUserTypes(userTypes);

        UserLoginResponse userLoginResponse = authService.userLogin(userLoginRequest);
        User user = userLoginResponse.getUser();

        for (UserParty userParty:userLoginResponse.getUserParties()) {
           //终端事件
           if(userParty.getUserType().equals(UserTypes.TERM_USER)) {
               //判断是否
               PartyContact partyContact = partyService.getPartyContact(userParty.getPartyId(),
                                                                        ContactMechTypes.MOBILE,
                                                                        ContactPurposes.SYS_ADMIN);
               if(partyContact!=null && user.getUserName().equals(partyContact.getContactMech())) {
                   WxUserBindRequest wxUserBindRequest = new WxUserBindRequest();
                   wxUserBindRequest.setUserName(user.getUserName());
                   wxUserBindRequest.setAppid(andpayBindRequest.getAppId());
                   wxUserBindRequest.setPartyId(userParty.getPartyId());
                   wxUserBindRequest.setBindType(BindTypes.BDT_APU);
                   wxUserBindRequest.setOpenid(andpayBindRequest.getOpenId());
                   weixinUserBindService.weixinUserBind(wxUserBindRequest);
               }


           }
        }

    }

    /**
     * 绑定成功页
     * @param request
     * @param resp
     * @return
     * @throws AppBizException
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/andpayBind/success")
    public ModelAndView accountBindSuccess(HttpServletRequest request, HttpServletResponse resp)
            throws AppBizException {
        ModelAndView modelAndView = new ModelAndView("accountBind/bind_success");

        return modelAndView;
    }
}

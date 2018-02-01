package me.andpay.ac.wpn.srv.service;


import me.andpay.ac.consts.wpn.BindUserStatuses;
import me.andpay.ac.wpn.api.model.WxBindUserInfo;
import me.andpay.ac.wpn.api.model.WxUserBindRequest;
import me.andpay.ac.wpn.api.model.db.WxBindUser;
import me.andpay.ac.wpn.api.model.db.WxPublicNumberUser;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxBindUserCond;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberUserCond;
import me.andpay.ac.wpn.api.service.WeixinUserBindService;
import me.andpay.ac.wpn.srv.dao.WxBindUserDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberUserDao;
import me.andpay.ac.wpn.srv.helper.WxBindUserMigrateHelper;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.BeanUtil;
import me.andpay.ti.util.JSON;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cen on 16/11/2.
 * 微信用户绑定
 */
public class WeixinUserBindServiceImpl implements WeixinUserBindService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WxBindUserDao wxBindUserDao;
    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;

    @Autowired
    private WxBindUserMigrateHelper wxBindUserMigrateHelper;

    private static JSON jsonutil = JSON.getDefault();

    private WxBindUser addWxBindUser(WxUserBindRequest wxBindRequest, Long userId) {
        WxBindUser wxBindUser = new WxBindUser();
        BeanUtil.copyProperties(wxBindRequest, wxBindUser);
        wxBindUser.setStatus(BindUserStatuses.UBDS_BIND);
        wxBindUser.setUpdateTime(new Date());
        wxBindUser.setCreateTime(new Date());
        wxBindUser.setUserId(userId);
        if (wxBindRequest.getBindAttr() != null && wxBindRequest.getBindAttr().size() > 0) {
            wxBindUser.setBindAttrs(jsonutil.toJSONString(wxBindRequest.getBindAttr()));
        }
        wxBindUserDao.add(wxBindUser);
        return wxBindUser;
    }

    private WxBindUserInfo createWxBindUserInfo(WxBindUser wxBindUser, WxPublicNumberUser wxPublicNumberUser) {
        WxBindUserInfo wxBindUserInfo = new WxBindUserInfo();
        BeanUtil.copyProperties(wxPublicNumberUser, wxBindUserInfo);
        BeanUtil.copyProperties(wxBindUser, wxBindUserInfo);
        if (StringUtil.isNotBlank(wxBindUser.getBindAttrs())) {
            wxBindUserInfo.setBindAttr(jsonutil.parseToObject(wxBindUser.getBindAttrs(), HashMap.class));
        }

        return wxBindUserInfo;
    }

    @Transactional
    public WxBindUserInfo weixinUserBind(WxUserBindRequest wxBindRequest) throws AppBizException {

        logger.info("user start bind username=[{}],partyId=[{}],openId=[{}],appid=[{}]",
                new String[]{wxBindRequest.getUserName(), wxBindRequest.getPartyId(),
                        wxBindRequest.getOpenid(), wxBindRequest.getAppid()});

        if (StringUtil.isBlank(wxBindRequest.getAppid()) || StringUtil.isBlank(wxBindRequest.getOpenid()) ||
                StringUtil.isBlank(wxBindRequest.getPartyId()) || StringUtil.isBlank(wxBindRequest.getUserName()) ||
                StringUtil.isBlank(wxBindRequest.getBindType())) {
            throw new IllegalArgumentException(JSON.getDefault().toJSONString(wxBindRequest));
        }


        QueryWxPublicNumberUserCond queryWxPublicNumberUserCond = new QueryWxPublicNumberUserCond();
        queryWxPublicNumberUserCond.setAppid(wxBindRequest.getAppid());
        queryWxPublicNumberUserCond.setOpenid(wxBindRequest.getOpenid());

        WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.getUniqueForUpdate(queryWxPublicNumberUserCond);
        if (wxPublicNumberUser == null) {
            //创建公众号微信用户
            wxPublicNumberUser = new WxPublicNumberUser();
            BeanUtil.copyProperties(wxBindRequest, wxPublicNumberUser);
            wxPublicNumberUser.setCreateTime(new Date());
            wxPublicNumberUser.setUpdateTime(wxPublicNumberUser.getCreateTime());
            wxPublicNumberUserDao.add(wxPublicNumberUser);
        }
        //更新用户信息
        if (wxBindRequest.getAuthUserInfo() != null) {
            wxPublicNumberUser.setAuthUserInfo(wxBindRequest.getAuthUserInfo());
            wxPublicNumberUser.setUpdateTime(new Date());
            wxPublicNumberUserDao.update(wxPublicNumberUser);
        }

        //绑定用户 一个party只能绑定一个公众号，绑定类型不做区分
        QueryWxBindUserCond wxBindUserCond = new QueryWxBindUserCond();
        wxBindUserCond.setUserName(wxBindRequest.getUserName());
        wxBindUserCond.setPartyId(wxBindRequest.getPartyId());
        wxBindUserCond.setOrders("updateTime-");
        WxBindUser wxBindUser = wxBindUserDao.getFirst(wxBindUserCond);

        if (wxBindUser == null) {
            wxBindUser = addWxBindUser(wxBindRequest, wxPublicNumberUser.getUserId());
        } else {
            wxBindUser.setPartyId(wxBindRequest.getPartyId());
            wxBindUser.setUserName(wxBindRequest.getUserName());
            wxBindUser.setUserId(wxPublicNumberUser.getUserId());
            wxBindUser.setStatus(BindUserStatuses.UBDS_BIND);
            wxBindUser.setBindType(wxBindRequest.getBindType());

            String storeAttrs = wxBindUser.getBindAttrs();
            if (wxBindRequest.getBindAttr() != null && wxBindRequest.getBindAttr().size() > 0) {
                if (storeAttrs == null) {
                    wxBindUser.setBindAttrs(jsonutil.toJSONString(wxBindRequest.getBindAttr()));
                } else {
                    Map<String, String> storeAttrsMap = jsonutil.parseToObject(storeAttrs, HashMap.class);
                    for (String key : wxBindRequest.getBindAttr().keySet()) {
                        storeAttrsMap.put(key, wxBindRequest.getBindAttr().get(key));
                    }
                    wxBindUser.setBindAttrs(jsonutil.toJSONString(storeAttrsMap));
                }
            }
            wxBindUser.setUpdateTime(new Date());
            wxBindUserDao.update(wxBindUser);
        }


        //返回信息
        return createWxBindUserInfo(wxBindUser, wxPublicNumberUser);

    }

    public WxBindUserInfo obtainOneBindUser(String appid, String openId, String bindType) throws AppBizException {

        logger.info("user obtainOneBindUser openId=[{}],appid=[{}] bindType=[{}]",
                new String[]{openId, appid, bindType});


        if (StringUtil.isBlank(appid) || StringUtil.isBlank(openId) || StringUtil.isBlank(bindType)) {
            throw new IllegalArgumentException("appid=" + appid + ",openId=" + openId + ",bindType=" + bindType);
        }

        QueryWxPublicNumberUserCond queryWxPublicNumberUserCond = new QueryWxPublicNumberUserCond();
        queryWxPublicNumberUserCond.setAppid(appid);
        queryWxPublicNumberUserCond.setOpenid(openId);
        WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.getUnique(queryWxPublicNumberUserCond);
        if (wxPublicNumberUser == null) {
            return null;
        }
        QueryWxBindUserCond wxBindUserCond = new QueryWxBindUserCond();
        wxBindUserCond.setStatus(BindUserStatuses.UBDS_BIND);
        wxBindUserCond.setBindType(bindType);
        wxBindUserCond.setUserId(wxPublicNumberUser.getUserId());
        wxBindUserCond.setOrders("updateTime-");//找最近绑定的用户

        List<WxBindUser> wxBindUsers = wxBindUserDao.query(wxBindUserCond, 0, 1);

        if (wxBindUsers.size() == 0) {
            return null;
        }
        WxBindUser wxBindUser = wxBindUsers.get(0);
        //还原
        wxBindUserMigrateHelper.convertOutWxBindUser(wxBindUser);
        WxBindUserInfo wxBindUserInfo = new WxBindUserInfo();
        BeanUtil.copyProperties(wxPublicNumberUser, wxBindUserInfo);
        BeanUtil.copyProperties(wxBindUser, wxBindUserInfo);
        if (StringUtil.isNotBlank(wxBindUser.getBindAttrs())) {
            wxBindUserInfo.setBindAttr(jsonutil.parseToObject(wxBindUser.getBindAttrs(), HashMap.class));
        }
        return wxBindUserInfo;
    }

    @Transactional
    public void unBindUser(Long bindUserId) throws AppBizException {

        logger.info("user unBindUser bindUserId=[{}]",
                new Object[]{bindUserId});

        WxBindUser wxBindUser = wxBindUserDao.getForUpdate(bindUserId);
        wxBindUser.setStatus(BindUserStatuses.UBDS_UNBIND);
        wxBindUser.setUpdateTime(new Date());
        wxBindUserDao.update(wxBindUser);
    }


    public WxBindUserInfo obtainBindUserWithParty(String partyId) throws AppBizException {
        if (StringUtil.isBlank(partyId)) {
            throw new IllegalArgumentException("party is null");
        }


        QueryWxBindUserCond wxBindUserCond = new QueryWxBindUserCond();
        wxBindUserCond.setStatus(BindUserStatuses.UBDS_BIND);
        wxBindUserCond.setPartyId(partyId);
        List<WxBindUser> wxBindUsers = wxBindUserDao.query(wxBindUserCond, 0, 1);

        if (wxBindUsers.size() == 0) {
            return null;
        }
        WxBindUser wxBindUser = wxBindUsers.get(0);
        //还原
        wxBindUserMigrateHelper.convertOutWxBindUser(wxBindUser);
        WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.get(wxBindUser.getUserId());
        if (wxPublicNumberUser == null) {
            return null;
        }

        return createWxBindUserInfo(wxBindUser, wxPublicNumberUser);
    }


}

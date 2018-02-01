package me.andpay.ac.wpn.srv;

import me.andpay.ac.consts.wpn.BindTypes;
import me.andpay.ac.consts.wpn.BindUserStatuses;
import me.andpay.ac.wpn.api.model.db.WxBindUser;
import me.andpay.ac.wpn.api.model.WxBindUserInfo;
import me.andpay.ac.wpn.api.model.db.WxPublicNumberUser;
import me.andpay.ac.wpn.api.model.WxUserBindRequest;
import me.andpay.ac.wpn.api.service.WeixinUserBindService;
import me.andpay.ac.wpn.srv.dao.WxBindUserDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberUserDao;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import me.andpay.ti.util.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cen on 16/11/5.
 */

@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/ac-wpn-srv-test-base-config.xml" })
public class WeixinUserBindServiceTest {

    @Autowired
    private WeixinUserBindService weixinUserBindService;

    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;

    @Autowired
    private WxBindUserDao wxBindUserDao;

    private  static JSON jsonutil = JSON.getDefault();


    private void assertWeixinUserBind(WxUserBindRequest wxUserBindRequest, WxBindUserInfo wxBindUserInfo) {

        Assert.assertEquals(wxBindUserInfo.getAppid(),wxUserBindRequest.getAppid());
        Assert.assertEquals(wxBindUserInfo.getAuthUserInfo(),wxUserBindRequest.getAuthUserInfo());
        Assert.assertEquals(wxBindUserInfo.getBindType(),wxUserBindRequest.getBindType());
        Assert.assertEquals(wxBindUserInfo.getOpenid(),wxUserBindRequest.getOpenid());
        Assert.assertEquals(wxBindUserInfo.getPartyId(),wxUserBindRequest.getPartyId());
        Assert.assertEquals(wxBindUserInfo.getUserName(),wxUserBindRequest.getUserName());
        Assert.assertNotNull(wxBindUserInfo.getBindAttr());

        WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.get(wxBindUserInfo.getUserId());
        Assert.assertEquals(wxPublicNumberUser.getAppid(),wxUserBindRequest.getAppid());
        Assert.assertEquals(wxPublicNumberUser.getOpenid(),wxUserBindRequest.getOpenid());
        Assert.assertEquals(wxPublicNumberUser.getAuthUserInfo(),wxUserBindRequest.getAuthUserInfo());
        Assert.assertEquals(wxPublicNumberUser.getUnionid(),wxUserBindRequest.getUnionid());
        Assert.assertNotNull(wxPublicNumberUser.getCreateTime());
        Assert.assertNotNull(wxPublicNumberUser.getUpdateTime());

        WxBindUser wxBindUser = wxBindUserDao.get(wxBindUserInfo.getBindUserId());
        Assert.assertEquals(wxBindUser.getBindType(),wxBindUserInfo.getBindType());
        Assert.assertEquals(wxBindUser.getUserName(),wxBindUserInfo.getUserName());
        Assert.assertEquals(wxBindUser.getPartyId(),wxBindUserInfo.getPartyId());
        Assert.assertEquals(wxBindUser.getUserId(),wxPublicNumberUser.getUserId());
        Assert.assertEquals(wxBindUser.getStatus(), BindUserStatuses.UBDS_BIND);
        Assert.assertNotNull(wxBindUser.getBindAttrs());

        Assert.assertNotNull(wxBindUser.getCreateTime());
        Assert.assertNotNull(wxBindUser.getUpdateTime());
    }


    private void checkWxBindUserInfo(WxBindUserInfo wxBindUserInfo,WxUserBindRequest wxUserBindRequest,Map<String,String> bindAttr) {
        Assert.assertEquals(wxBindUserInfo.getAppid(),wxUserBindRequest.getAppid());
        Assert.assertEquals(wxBindUserInfo.getAuthUserInfo(),wxUserBindRequest.getAuthUserInfo());
        Assert.assertEquals(wxBindUserInfo.getBindType(),wxUserBindRequest.getBindType());
        Assert.assertEquals(wxBindUserInfo.getOpenid(),wxUserBindRequest.getOpenid());
        Assert.assertEquals(wxBindUserInfo.getPartyId(),wxUserBindRequest.getPartyId());
        Assert.assertEquals(wxBindUserInfo.getUserName(),wxUserBindRequest.getUserName());
        Assert.assertEquals(jsonutil.toJSONString(bindAttr),jsonutil.toJSONString(wxBindUserInfo.getBindAttr()));

    }

    @Test
    public void weixinUserBindTest() throws AppBizException{
        //创建全新用户
        WxUserBindRequest wxUserBindRequest = new WxUserBindRequest();
        wxUserBindRequest.setBindType(BindTypes.BDT_PTN);
        wxUserBindRequest.setUserName("15618691708");
        wxUserBindRequest.setAppid("12345678");
        wxUserBindRequest.setOpenid("1111111123232333");
        wxUserBindRequest.setPartyId("12311313");
        wxUserBindRequest.setUnionid("123123");
        wxUserBindRequest.setAuthUserInfo("{\"openid\":\"oLjPPs-djjn7LvZiwv1ynUUGEbSs\",\"nickname\":\"洲\"}");
        Map<String,String> bindAttr = new HashMap<String, String>();
        bindAttr.put("password","123456");
        wxUserBindRequest.setBindAttr(bindAttr);

        WxBindUserInfo wxBindUserInfo = weixinUserBindService.weixinUserBind(wxUserBindRequest);
        assertWeixinUserBind(wxUserBindRequest,wxBindUserInfo);
        Assert.assertEquals(jsonutil.toJSONString(bindAttr),jsonutil.toJSONString(wxBindUserInfo.getBindAttr()));

        //已经创建微信用户，更新authUserInfo
        //partyId 和 userName 不同绑定一个新用户
        wxUserBindRequest.setAuthUserInfo("test");
        wxUserBindRequest.setPartyId("1345678999");
        wxUserBindRequest.setUserName("15216780073");
        bindAttr.put("password2","123456");

        wxBindUserInfo =
                weixinUserBindService.weixinUserBind(wxUserBindRequest);

        assertWeixinUserBind(wxUserBindRequest,wxBindUserInfo);

        Assert.assertEquals(jsonutil.toJSONString(bindAttr),jsonutil.toJSONString(wxBindUserInfo.getBindAttr()));

        WxBindUserInfo wxBindUserInfo2 =
                weixinUserBindService.obtainOneBindUser(wxUserBindRequest.getAppid(),wxUserBindRequest.getOpenid(),BindTypes.BDT_PTN);
        checkWxBindUserInfo(wxBindUserInfo2,wxUserBindRequest,bindAttr);

        wxBindUserInfo2 = weixinUserBindService.obtainBindUserWithParty(wxUserBindRequest.getPartyId());
        checkWxBindUserInfo(wxBindUserInfo2,wxUserBindRequest,bindAttr);
        //解绑用户
        weixinUserBindService.unBindUser(wxBindUserInfo2.getBindUserId());
        WxBindUser wxBindUser =  wxBindUserDao.get(wxBindUserInfo2.getBindUserId());
        Assert.assertEquals(wxBindUser.getStatus(), BindUserStatuses.UBDS_UNBIND);




    }



}

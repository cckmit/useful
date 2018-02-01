package me.andpay.ac.wpn.srv;

/**
 * Created by cen on 16/11/4.
 */
import me.andpay.ac.wpn.api.consts.RedisPrefixKeys;
import me.andpay.ac.wpn.api.model.WxJSAPISignResult;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.data.redis.RedisTemplate;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

/**
 * Created by cen on 16/10/26.
 */
@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/ac-wpn-srv-test-weixinauth-config.xml" })
public class WeixinAuthServiceTest {

    @Autowired
    private WeixinAuthService weixinAuthService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DataInitService dataInitService;

    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;

    private String appId;

    @Before
    public void before(){
        WxPublicNumber wxPublicNumber = dataInitService.createPublicNumber();
        appId = wxPublicNumber.getAppId();
        String redisKey = RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX+appId;
        redisTemplate.removeValue(redisKey);

    }
    @After
    public void after(){
        wxPublicNumberDao.delete(1l);
    }



    @Test
    public void testObtainJsapiTicket() throws AppBizException{

        String redisKey = RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX+appId;

        String ticket = weixinAuthService.obtainJsapiTicket(appId);
        Assert.assertNotNull(ticket);

        String cacheTicket = redisTemplate.getValue(redisKey);
        Assert.assertEquals(ticket,cacheTicket);

        redisTemplate.removeValue(redisKey);

    }

    @Test
    public void testJsApiSign() throws AppBizException{
        WxJSAPISignResult wxJSAPISignResult =  weixinAuthService.jsApiSign("http://www.andpay.me",appId);
        Assert.assertNotNull(wxJSAPISignResult);
        Assert.assertNotNull(wxJSAPISignResult.getNoncestr());
        Assert.assertNotNull(wxJSAPISignResult.getSign());
        Assert.assertNotNull(wxJSAPISignResult.getTimestamp());

    }


    @Test
    public  void testObtainAuthTwoAccessTokenAndRefreshToken() throws AppBizException{
        //code参数只能在微信客户端访问获取，所以无法进行单元测试
//        String code = "0110yTbM0Rn9V52D15cM0vsPbM00yTb9";
//        AuthTwoAccessTokenResult authTwoAccessTokenResult =  weixinAuthService.obtainAuthTwoAccessToken(appId,code);
//        Assert.assertNotNull(authTwoAccessTokenResult);
//        authTwoAccessTokenResult = weixinAuthService.refreshAuthTwoAccessKoken(authTwoAccessTokenResult.getRefreshToken(),appId);
//        Assert.assertNotNull(authTwoAccessTokenResult);
//        AuthUserInfo authUserInfo = weixinAuthService.getAuthUserInfo(authTwoAccessTokenResult.getAccessToken(),authTwoAccessTokenResult.getOpenId());
//        Assert.assertNotNull(authUserInfo);

    }




}

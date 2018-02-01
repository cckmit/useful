package me.andpay.ac.wpn.srv;

/**
 * Created by cen on 16/11/4.
 */

import me.andpay.ac.wpn.api.consts.RedisPrefixKeys;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.data.redis.RedisTemplate;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class WeixinAuthServiceObtainAccessTokenTest {

    @Autowired
    private WeixinAuthService weixinAuthService;

    @Autowired
    private RedisTemplate redisTemplate;

    private String appId;


    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;


    @Autowired
    private DataInitService dataInitService;

    @Before
    public void before(){
        WxPublicNumber wxPublicNumber = dataInitService.createPublicNumber();
        appId = wxPublicNumber.getAppId();
    }
    @After
    public void after(){
        wxPublicNumberDao.delete(1l);
    }

    @Test
    public void testObtainAccessToken() throws AppBizException{

        String errorAppid = "asdas123123";
        try {
            weixinAuthService.obtainAccessToken(errorAppid);
        }catch (Exception ex){
            Assert.assertEquals(ex.getLocalizedMessage(),"wxPublicNumber not found appId="+errorAppid);
        }
        String redisKey = RedisPrefixKeys.REDIS_ACCESS_TOKEN_PREFIX+appId;
        redisTemplate.removeValue(redisKey);

        String accessToken = weixinAuthService.obtainAccessToken(appId);
        Assert.assertNotNull(accessToken);

        String cacheRedisToken = redisTemplate.getValue(redisKey);
        Assert.assertEquals(accessToken,cacheRedisToken);

        redisTemplate.removeValue(redisKey);

    }









}

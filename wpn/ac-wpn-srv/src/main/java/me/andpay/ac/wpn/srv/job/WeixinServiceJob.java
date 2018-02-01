package me.andpay.ac.wpn.srv.job;

import me.andpay.ac.wpn.api.consts.QuartzJobGroups;
import me.andpay.ac.wpn.api.consts.RedisPrefixKeys;
import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberCond;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ti.data.redis.RedisTemplate;
import me.andpay.ti.lnk.annotaion.LnkService;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.lnk.api.LnkClientContextAccessor;
import me.andpay.ti.quartz.*;
import org.quartz.CronTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by cen on 16/11/9.
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_SRV, serviceInterface = QuartzRemoteJob.class)
public class WeixinServiceJob implements QuartzRemoteJob, InitializingBean {


    private static final Logger logger = LoggerFactory.getLogger(WeixinServiceJob.class);
    /**
     * 定时服务
     */
    @Lnkwired
    private QuartzService quartzService;
    /**
     * 微信服务
     */
    @Autowired
    private WeixinAuthService weixinService;

    /**
     * 微信公众号存储
     */
    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定时任务名称
     */
    private final String QUARTZ_JOB_NAME = "weixin_service_fresh";

    private String cronExpression;

    @Override
    public void afterPropertiesSet() throws Exception {

        QuartzJobKey jobKey = QuartzJobKey.jobKey(QUARTZ_JOB_NAME, QuartzJobGroups.AC_WPN_WEIXIN_SRV_FRESHCACHE_JOB);

        if (quartzService.existJob(jobKey)) {
            logger.info("Job {} already exists, cancel this one", QUARTZ_JOB_NAME);
            quartzService.cancelJob(jobKey);
        }

        QuartzJobDetail jobDetail = QuartzJobDetail
                .newJobDetail(QUARTZ_JOB_NAME, QuartzJobGroups.AC_WPN_WEIXIN_SRV_FRESHCACHE_JOB);
        CronTrigger trigger = newTrigger().withSchedule(cronSchedule(cronExpression)).build();

        QuartzRemoteJob remoteJobProxy = LnkClientContextAccessor.newCallbackForLnkService(QuartzRemoteJob.class,
                WeixinServiceJob.class);

        quartzService.scheduleJob(jobDetail, trigger, remoteJobProxy);
        logger.info("Job {} initialized, cron=[{}],", new Object[]{QUARTZ_JOB_NAME, cronExpression});

    }

    /**
     * 刷新微信token缓存
     */
    @Override
    public void execute(QuartzJobExeContext quartzJobExeContext) {
        try {
            QueryWxPublicNumberCond cond = new QueryWxPublicNumberCond();
            List<WxPublicNumber> wxPublicNumbers = wxPublicNumberDao.query(cond,0,1000);
            for (WxPublicNumber wxPublicNumber:wxPublicNumbers){
                redisTemplate.removeValue(RedisPrefixKeys.REDIS_ACCESS_TOKEN_PREFIX);
                weixinService.obtainAccessToken(wxPublicNumber.getAppId());
                redisTemplate.removeValue(RedisPrefixKeys.REDIS_JSAPI_TICKET_PREFIX);
                weixinService.obtainJsapiTicket(wxPublicNumber.getAppId());
            }
        }catch (Exception ex){
            logger.error("weixinSerivce invoke error",ex);
        }
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}

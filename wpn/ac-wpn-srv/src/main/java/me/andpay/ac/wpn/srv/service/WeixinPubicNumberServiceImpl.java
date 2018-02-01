package me.andpay.ac.wpn.srv.service;

import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.model.WxPublicNumberResponse;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberCond;
import me.andpay.ac.wpn.api.service.WeixinPubicNumberService;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cen on 16/11/4.
 */
public class WeixinPubicNumberServiceImpl implements WeixinPubicNumberService {


    Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;


    public WxPublicNumberResponse obtainWeiPublicNumber(Long wxPublicNumberId)  throws AppBizException{

        logger.info("start obtainWeiPublicNumber wxPublicNumberId=[{}]",
                new Object[]{wxPublicNumberId});


        if(wxPublicNumberId == null) {
            throw new IllegalArgumentException("wxPublicNumberId is null");
        }

        WxPublicNumber wxPublicNumber =  wxPublicNumberDao.get(wxPublicNumberId);
        if(wxPublicNumber == null) {
            return null;
        }
        WxPublicNumberResponse wxPublicNumberResponse = new WxPublicNumberResponse();
        wxPublicNumberResponse.setAppId(wxPublicNumber.getAppId());
        wxPublicNumberResponse.setName(wxPublicNumber.getName());

        return wxPublicNumberResponse;
    }

    public WxPublicNumberResponse obtainWeiPublicNumberWithAlias(String appAlias) throws AppBizException {

        logger.info("start obtainWeiPublicNumberWithAlias appAlias=[{}]",
                new Object[]{appAlias});


        if(StringUtil.isBlank(appAlias)) {
            throw new IllegalArgumentException("appAlias is null");
        }

        QueryWxPublicNumberCond queryWxPublicNumberCond = new QueryWxPublicNumberCond();
        queryWxPublicNumberCond.setAppAlais(appAlias);

        WxPublicNumber wxPublicNumber =  wxPublicNumberDao.getFirst(queryWxPublicNumberCond);
        if(wxPublicNumber == null) {
            return null;
        }

        logger.info("wxPublicNumber With AppId:[{}], name:[{}]", new Object[]{wxPublicNumber.getAppId(), wxPublicNumber.getName()});

        WxPublicNumberResponse wxPublicNumberResponse = new WxPublicNumberResponse();
        wxPublicNumberResponse.setAppId(wxPublicNumber.getAppId());
        wxPublicNumberResponse.setName(wxPublicNumber.getName());

        return wxPublicNumberResponse;

    }

}

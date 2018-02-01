package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.QuerySwitchListReq;
import me.andpay.ac.wpn.api.model.SwitchItem;
import me.andpay.ac.wpn.api.model.UpdateSwitchConfigReq;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.LnkService;

import java.util.List;

/**
 * 消息配置缓存
 * Created by cen on 2017/7/6.
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_CONFIG_SRV)
public interface MessageSwitchConfigService {

    /**
     * 项目配置
     * @param updateSwitchConfigReq
     */
    void updateSwitchConfig(UpdateSwitchConfigReq updateSwitchConfigReq) throws AppBizException;

    /**
     * 获取开关配置
     * @return
     */
    List<SwitchItem> querySwitchList(QuerySwitchListReq switchListReq) throws AppBizException;
}

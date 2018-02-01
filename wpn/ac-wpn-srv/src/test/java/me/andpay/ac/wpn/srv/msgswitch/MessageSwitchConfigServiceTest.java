package me.andpay.ac.wpn.srv.msgswitch;

import me.andpay.ac.wpn.api.consts.MessageAppIds;
import me.andpay.ac.wpn.api.consts.SwitchConfigTargetTypes;
import me.andpay.ac.wpn.api.model.QuerySwitchListReq;
import me.andpay.ac.wpn.api.model.SwitchItem;
import me.andpay.ac.wpn.api.model.UpdateSwitchConfigReq;
import me.andpay.ac.wpn.srv.mock.SpyDaos;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ti.base.AppBizException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cen on 2017/7/6.
 */
@Transactional
public class MessageSwitchConfigServiceTest extends SpyDaos {

    @Autowired
    private DataInitService dataInitService;

    @InjectMocks
    private MessageSwitchConfigServiceImpl switchConfigService;

    @Test
    public void test() throws AppBizException{

        dataInitService.createMessageSwitchConfigList();

        QuerySwitchListReq querySwitchListReq = new QuerySwitchListReq();
        querySwitchListReq.setAppId(MessageAppIds.MSA_PTN);
        querySwitchListReq.setTarget(DataInitService.PATYID);
        querySwitchListReq.setTargetType(SwitchConfigTargetTypes.SCT_PARTY);

        List<SwitchItem> switchItemList = switchConfigService.querySwitchList(querySwitchListReq);
        Assert.assertFalse(switchItemList.get(0).isOpen());
        Assert.assertTrue(switchItemList.get(1).isOpen());
        Assert.assertTrue(switchItemList.get(2).isOpen());


        UpdateSwitchConfigReq updateSwitchConfigReq = new UpdateSwitchConfigReq();
        updateSwitchConfigReq.setAppId(MessageAppIds.MSA_PTN);
        updateSwitchConfigReq.setTarget(DataInitService.PATYID);
        updateSwitchConfigReq.setTargetType(SwitchConfigTargetTypes.SCT_PARTY);
        switchItemList.get(0).setOpen(true);
        switchItemList.get(1).setOpen(false);
        switchItemList.get(2).setOpen(false);
        updateSwitchConfigReq.setSwitchItems(switchItemList);
        switchConfigService.updateSwitchConfig(updateSwitchConfigReq);

        switchItemList = switchConfigService.querySwitchList(querySwitchListReq);
        Assert.assertTrue(switchItemList.get(0).isOpen());
        Assert.assertFalse(switchItemList.get(1).isOpen());
        Assert.assertFalse(switchItemList.get(2).isOpen());

    }
}

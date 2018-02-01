package me.andpay.ac.wpn.srv.msgswitch;

import me.andpay.ac.wpn.api.model.QuerySwitchListReq;
import me.andpay.ac.wpn.api.model.SwitchItem;
import me.andpay.ac.wpn.api.model.UpdateSwitchConfigReq;
import me.andpay.ac.wpn.api.model.db.MessageSwitchConfig;
import me.andpay.ac.wpn.api.model.db.MessageTemplateGroup;
import me.andpay.ac.wpn.api.model.db.cond.QueryMessageSwitchConfigCond;
import me.andpay.ac.wpn.api.model.db.cond.QueryMessageTemplateGroupCond;
import me.andpay.ac.wpn.api.service.MessageSwitchConfigService;
import me.andpay.ac.wpn.srv.dao.MessageSwitchConfigDao;
import me.andpay.ac.wpn.srv.dao.MessageTemplateGroupDao;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 消息开关配置服务
 * Created by cen on 2017/7/6.
 */
public class MessageSwitchConfigServiceImpl implements MessageSwitchConfigService{

    @Autowired
    private MessageSwitchConfigDao messageSwitchConfigDao;

    @Autowired
    private MessageTemplateGroupDao messageTemplateGroupDao;

    @Override
    @Transactional
    public void updateSwitchConfig(UpdateSwitchConfigReq updateSwitchConfigReq) throws AppBizException {

        ValidateUtil.validate(updateSwitchConfigReq);

        Set<Long> groupIds = new HashSet<Long>();
        for(SwitchItem switchItem : updateSwitchConfigReq.getSwitchItems()) {
            groupIds.add(switchItem.getMessageTemplateGroupId());
        }
        QueryMessageSwitchConfigCond switchConfigCond = new QueryMessageSwitchConfigCond();
        switchConfigCond.setMessageTemplateGroupIds(groupIds);
        List<MessageSwitchConfig> messageSwitchConfigList = messageSwitchConfigDao.queryForUpdate(switchConfigCond);
        Map<Long,MessageSwitchConfig> messageSwitchConfigMap = new HashMap<Long, MessageSwitchConfig>();
        for (MessageSwitchConfig messageSwitchConfig : messageSwitchConfigList) {
            messageSwitchConfigMap.put(messageSwitchConfig.getMessageTemplateGroupId(),messageSwitchConfig);
        }

        for(SwitchItem switchItem : updateSwitchConfigReq.getSwitchItems()) {
            MessageSwitchConfig messageSwitchConfig =  messageSwitchConfigMap.get(switchItem.getMessageTemplateGroupId());
            if(messageSwitchConfig == null) {

                //开关关闭插入数据
                if(!switchItem.isOpen()) {
                    MessageSwitchConfig addConfig = new MessageSwitchConfig();
                    addConfig.setOpen(false);
                    addConfig.setMessageTemplateGroupId(switchItem.getMessageTemplateGroupId());
                    addConfig.setTarget(updateSwitchConfigReq.getTarget());
                    addConfig.setTargetType(updateSwitchConfigReq.getTargetType());
                    addConfig.setCreateTime(new Date());
                    messageSwitchConfigDao.add(addConfig);
                }
            }else {
                messageSwitchConfig.setOpen(switchItem.isOpen());
            }
        }
        //批量更新
        messageSwitchConfigDao.batchUpdate(messageSwitchConfigList,"open");

    }

    @Override
    public List<SwitchItem> querySwitchList(QuerySwitchListReq querySwitchListReq) throws AppBizException{

        ValidateUtil.validate(querySwitchListReq);

        QueryMessageSwitchConfigCond configCond = new QueryMessageSwitchConfigCond();
        configCond.setTarget(querySwitchListReq.getTarget());
        configCond.setTargetType(querySwitchListReq.getTargetType());
        List<MessageSwitchConfig> messageSwitchConfigs = messageSwitchConfigDao.query(configCond,0,100);
        Map<Long,MessageSwitchConfig> messageSwitchConfigMap = new HashMap<Long, MessageSwitchConfig>();
        for(MessageSwitchConfig messageSwitchConfig:messageSwitchConfigs) {
            messageSwitchConfigMap.put(messageSwitchConfig.getMessageTemplateGroupId(),messageSwitchConfig);
        }
        QueryMessageTemplateGroupCond groupCond = new QueryMessageTemplateGroupCond();
        groupCond.setAppId(querySwitchListReq.getAppId());
        groupCond.setOrders("priority+");

        List<SwitchItem> switchItemList = new ArrayList<SwitchItem>();
        List<MessageTemplateGroup> templateGroups = messageTemplateGroupDao.query(groupCond,0,100);
        for(MessageTemplateGroup templateGroup : templateGroups) {
            SwitchItem switchItem = new SwitchItem();
            switchItem.setGroupName(templateGroup.getGroupName());
            switchItem.setMessageTemplateGroupId(templateGroup.getMessageTemplateGroupId());
            switchItem.setOpen(true);//默认打开
            MessageSwitchConfig messageSwitchConfig = messageSwitchConfigMap.get(templateGroup.getMessageTemplateGroupId());
            if(messageSwitchConfig != null) {
                switchItem.setOpen(messageSwitchConfig.isOpen());
            }

            switchItemList.add(switchItem);
        }

        return switchItemList;
    }

}

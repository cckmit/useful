package me.andpay.ac.wpn.srv.dao;

import me.andpay.ac.wpn.api.model.db.MessageSwitchConfig;
import me.andpay.ac.wpn.api.model.db.cond.QueryMessageSwitchConfigCond;
import me.andpay.ti.daf.dao.hibernate.HibernateBatchDao;

/**
 * 消息开关dao
 * Created by cen on 2017/7/6.
 */
public class MessageSwitchConfigDao extends
        HibernateBatchDao<MessageSwitchConfig,Long,QueryMessageSwitchConfigCond>{

}

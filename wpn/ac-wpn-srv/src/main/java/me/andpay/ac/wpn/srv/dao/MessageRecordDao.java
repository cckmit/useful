package me.andpay.ac.wpn.srv.dao;

import me.andpay.ac.wpn.api.model.db.MessageRecord;
import me.andpay.ac.wpn.api.model.db.cond.QueryMessageRecordCond;
import me.andpay.ti.daf.dao.hibernate.HibernateGenericDao;

/**
 * Created by cen on 2016/11/16.
 * 消息记录dao
 */
public class MessageRecordDao extends HibernateGenericDao<MessageRecord,Long,QueryMessageRecordCond> {
}

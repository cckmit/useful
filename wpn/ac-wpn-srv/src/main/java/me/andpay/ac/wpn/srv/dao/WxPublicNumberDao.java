package me.andpay.ac.wpn.srv.dao;


import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberCond;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ti.daf.dao.hibernate.HibernateGenericDao;

/**
 * Created by cen on 16/11/2.
 * 微信公众号dao
 */
public class WxPublicNumberDao  extends HibernateGenericDao<WxPublicNumber, Long, QueryWxPublicNumberCond> {
}

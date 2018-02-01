package me.andpay.ac.wpn.srv.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;

/**
 * 提现工具类
 * 
 * @author yan.li
 */
public class WpnUtils {

	/**
	 * 时间格式
	 */
	public static final String TIME_HHMM = "HH:mm";

	/**
	 * 时间格式
	 */
	public static final String YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";
	
	/**
	 * 时间格式
	 */
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 月份和日期格式
	 */
	public static final String MM_DD = "MM月dd日";

	/**
	 * 作为乐观锁条件，更新调整时间参数
	 * 
	 * @param newTime
	 * @param origTime
	 * @return
	 */
	public static Date adjustTimeAsOptimisticLock(Date newTime, Date origTime) {
		if (origTime != null && newTime.getTime() - origTime.getTime() < 1000L) {
			// 使用时间作为乐观锁字段，避免新时间与原时间值在同一秒
			return new Date(origTime.getTime() + 1000L);
		}

		return newTime;
	}

	/**
	 * 判断参数是否为true
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isTrue(String value) {
		if (StringUtil.isBlank(value)) {
			return false;
		}

		if ("TRUE".equals(StringUtil.toUpperCase(StringUtil.trim(value), 0))) {
			return true;
		} else if ("1".equals(StringUtil.trim(value))) {
			return true;
		}

		return false;
	}

	/**
	 * 格式化金额为两位小数
	 * 
	 * @param field
	 * @return
	 */
	public static String formatAMTForStr(BigDecimal field) {
		return new DecimalFormat("0.00").format(field.doubleValue());
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static Date firstDayOfCurMonth() {
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天

		return DateUtil.roundDate(cal_1.getTime(), Calendar.DATE);
	}

	/**
	 * 获取当前时间(Mysql数据库会针对时间四舍五入，因此需要去掉毫秒)
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return DateUtil.roundDate(new Date(), Calendar.SECOND);
	}

}

package com.fwk.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @ClassName: DateTimeUtil
 * @Description: 日期时间工具类
 * @author YuanXu
 * @date 2016-4-27 上午11:07:04
 * 
 */
public class DateTimeUtil {

	public static long MILLIONSECOND_OF_SECOND = 1000;

	public static long MILLIONSECOND_OF_MINUTE = MILLIONSECOND_OF_SECOND * 60;

	public static long MILLIONSECOND_OF_HOUR = MILLIONSECOND_OF_MINUTE * 60;

	public static long MILLIONSECOND_OF_DAY = MILLIONSECOND_OF_HOUR * 24;

	/** yyyy-MM-dd HH:mm:ss.SSS */
	public static final String WHOLE_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/** yyyy-MM-dd'T'HH:mm:ss.SSSZ */
	public static final String WHOLE_T_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	/** yyyy-MM-dd HH:mm:ss */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** yyyy-MM-dd HH:mm */
	public static final String DEFAULT_SHORT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

	/** yyyy-MM-dd HH */
	public static final String DEFAULT_SHORT_DATETIME_HOUR_FORMAT = "yyyy-MM-dd HH";
	
	/** yyyy */
	public static final String DEFAULT_DATETIME_YEAR_FORMAT = "yyyy";

	/** 格式化只包含日期和时间的数字字符串 */
	public static final String DATETIME_FORMAT_ONLY_NUMBER = "yyyyMMddHHmmss";

	/** yyyy-MM-dd */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/** yyyy-MM-dd */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

	/** yyyy-MM-dd HH:mm:ss */
	private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

	/** yyyy-MM-dd HH:mm */
	private static final SimpleDateFormat SHORT_DATETIME_FORMAT = new SimpleDateFormat(DEFAULT_SHORT_DATETIME_FORMAT);

	/** yyyy-MM-dd HH */
	private static final SimpleDateFormat SHORT_DATETIME_HOUR_FORMAT = new SimpleDateFormat(
			DEFAULT_SHORT_DATETIME_HOUR_FORMAT);

	public static long TIMEZONE_OFFSET = Calendar.getInstance().get(Calendar.ZONE_OFFSET)
			+ Calendar.getInstance().get(Calendar.DST_OFFSET);

	/** 简体中文星期的定义 */
	public static final String[] WEEK_ZH_CN = new String[] { "", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };

	/**
	 * 根据日期获取是星期几
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String getWeek(String date, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

		return (WEEK_ZH_CN[localDate.getDayOfWeek().getValue()]);
	}

	/**
	 * 获取当前的日期字符串
	 * 
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String getNow(String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDate = LocalDateTime.now();

		return dateTimeFormatter.format(localDate);
	}

	private DateTimeUtil() {
		// Nothing
	}

	// 根据时间获取long格式时间
	public static Long getLongByDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		if (date != null) {
			return Long.parseLong(sdf.format(date));
		} else {
			return Long.parseLong(sdf.format(new Date()));
		}
	}

	/**
	 * 
	 * @Title: parse
	 * @Description: 用缺省的时间、日期格式(yyyy-MM-dd HH:mm:ss)去解析一个字符串
	 * @author YuanXu
	 * @param datestr
	 *            时间字符串
	 * @return
	 */
	public static Date parse(String datestr) {
		if (datestr == null) {
			return null;
		} else {
			// 去掉两端的空格
			datestr = datestr.trim();
		}

		Date date = null;

		if (datestr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
			try {
				// yyyy-MM-dd HH:mm:ss
				date = DATETIME_FORMAT.parse(datestr);
			} catch (ParseException ex) {
				date = null;
			}
		} else if (datestr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
			try {
				// yyyy-MM-dd HH:mm
				date = (null == date ? SHORT_DATETIME_FORMAT.parse(datestr) : date);
			} catch (ParseException ex) {
				date = null;
			}
		} else if (datestr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}")) {
			try {
				// yyyy-MM-dd HH
				date = (null == date ? SHORT_DATETIME_HOUR_FORMAT.parse(datestr) : date);
			} catch (ParseException ex) {
				date = null;
			}
		} else if (datestr.matches("\\d{4}-\\d{2}-\\d{2}")) {
			try {
				// yyyy-MM-dd
				date = (null == date ? DATE_FORMAT.parse(datestr) : date);
			} catch (ParseException ex) {
				date = null;
			}
		}

		return date;
	}

	/**
	 * 
	 * @Title: parse
	 * @Description: 使用给出的时间格式去解析一个字符串
	 * @author YuanXu
	 * @param datestr
	 *            时间字符串
	 * @param formatstr
	 *            时间格式字符串
	 * @return
	 */
	public static Date parse(String datestr, String formatstr) {
		SimpleDateFormat dateformat = new SimpleDateFormat(formatstr);
		Date date = null;
		try {
			date = dateformat.parse(datestr);
		} catch (ParseException ex) {
			date = null;
		}

		dateformat = null;
		return date;
	}

	/**
	 * 
	 * @Title: parse
	 * @Description: 将毫秒数转成时间格式
	 * @author YuanXu
	 * @param times
	 *            毫秒数
	 * @return
	 */
	public static Date parse(Long times) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(times);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Title: formatDate
	 * @Description: 用缺省的日期格式来将时间转化为字符串，结果字符串中只包含日期部分
	 * @author YuanXu
	 * @param date
	 *            时间对象
	 * @return String 如果传入的日期为null，则返回null
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}

		return DATE_FORMAT.format(date);
	}

	/**
	 * 
	 * @Title: formatDateTime
	 * @Description: 用缺省的日期时间格式来将时间转化为字符串，结果字符串中包含完整的日期、时间格式
	 * @author YuanXu
	 * @param date
	 *            时间对象
	 * @return String 如果传入的日期为null，则返回null
	 */
	public static String formatDateTime(final Date date) {
		if (date == null) {
			return null;
		}

		return DATETIME_FORMAT.format(date);
	}

	/**
	 * 
	 * @Title: format
	 * @Description: 用给定日期时间格式来将时间转化为字符串
	 * @author YuanXu
	 * @param date
	 *            时间对象
	 * @param formatstr
	 *            时间格式字符串
	 * @return String 如果传入的时间对象或时间格式字符串为空，则返回空
	 */
	public static String format(final Date date, final String formatstr) {
		if (date == null || formatstr == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(formatstr);
		String datestr = format.format(date);
		format = null;
		return datestr;
	}

	/**
	 * 
	 * @Title: getNow
	 * @Description: 获取系统当前的时间
	 * @author YuanXu
	 * @return
	 */
	public static Date getNow() {
		Calendar now = Calendar.getInstance();
		return now.getTime();
	}

	/**
	 * 
	 * @Title: getNowDateString
	 * @Description: 获取当前日期的缺省字符串形式
	 * @author YuanXu
	 * @return
	 */
	public static String getNowDateString() {
		return formatDate(getNow());
	}

	/**
	 * 
	 * @Title: getNowDateTimeString
	 * @Description: 获取当前日期及时间的缺省字符串形式
	 * @author YuanXu
	 * @return
	 */
	public static String getNowDateTimeString() {
		return formatDateTime(getNow());
	}

	/**
	 * 
	 * @Title: getNowWholeTimeString
	 * @Description: 获取全时间(包括毫秒)的时间字符串
	 * @author YuanXu
	 * @return
	 */
	public static String getNowWholeTimeString() {
		return DateTimeUtil.format(getNow(), WHOLE_DATETIME_FORMAT);
	}

	/**
	 * 
	 * @Title: getNowTimeString
	 * @Description: 获取当前时间的缺省字符串形式
	 * @author YuanXu
	 * @return
	 */
	public static String getNowTimeString() {
		return format(getNow(), DEFAULT_TIME_FORMAT);
	}

	/**
	 * 
	 * @Title: getThisYearStr
	 * @Description: 得到当前的年度（四位数字字符串）
	 * @author YuanXu
	 * @return
	 */
	public static String getThisYearStr() {
		Calendar now = Calendar.getInstance();
		return String.format("%s", now.get(Calendar.YEAR));
	}

	/**
	 * 
	 * @Title: getAdjustTime
	 * @Description: 时间调整
	 * @author YuanXu
	 * @param date
	 *            原始时间对象(为null时自动获取当前时间)
	 * @param day
	 *            int 时间偏移天数
	 * @param hour
	 *            int 时间偏移小时数
	 * @param minute
	 *            int 时间偏移分钟数
	 * @param second
	 *            int 时间偏移秒数
	 * @param microsecond
	 *            long 时间偏移微妙数
	 * @return Date 返回一个调整后的时间
	 */
	public static Date getAdjustTime(Date date, int day, int hour, int minute, int second, long microsecond) {
		Date d = date == null ? getNow() : date;
		return new Date(d.getTime() + MILLIONSECOND_OF_DAY * day + MILLIONSECOND_OF_HOUR * hour
				+ MILLIONSECOND_OF_MINUTE * minute + MILLIONSECOND_OF_SECOND * second + microsecond);
	}

	/**
	 * 
	 * @Title: setDayFirstTime
	 * @Description: 将时间设置为当天的第一个时间
	 * @author YuanXu
	 * @param date
	 */
	public static void setDayFirstTime(Date date) {
		if (date == null) {
			return;
		}

		long l = date.getTime();
		l = l - (l + TIMEZONE_OFFSET) % MILLIONSECOND_OF_DAY;
		date.setTime(l);
	}

	/**
	 * 
	 * @Title: setDayLastTime
	 * @Description: 将时间设置为当天的最后一个时间
	 * @author YuanXu
	 * @param date
	 */
	public static void setDayLastTime(Date date) {
		if (date == null) {
			return;
		}

		long l = date.getTime();
		l = l - (l + TIMEZONE_OFFSET) % MILLIONSECOND_OF_DAY + MILLIONSECOND_OF_DAY - 1;
		date.setTime(l);
	}

	/**
	 * 
	 * @Title: getStartOfDay
	 * @Description: 返回当天第一个时间
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static Date getStartOfDay(Date date) {
		setDayFirstTime(date);
		return date;
	}

	/**
	 * 
	 * @Title: getEndOfDay
	 * @Description: 返回当天最后一个时间
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay(Date date) {
		setDayLastTime(date);
		return date;
	}

	/**
	 * 
	 * @Title: getStartOfWeek
	 * @Description: 得到星期的第一个时间
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static Date getStartOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - 2));
		return getStartOfDay(cal.getTime());
	}

	/**
	 * 
	 * @Title: getEndOfWeek
	 * @Description: 得到星期的最后一个时间
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static Date getEndOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - 8));
		return getEndOfDay(cal.getTime());
	}

	/**
	 * 
	 * @Title: getStartOfMonth
	 * @Description: 得到一个月的第一个时间
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static Date getStartOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		return getStartOfDay(cal.getTime());
	}

	/**
	 * 
	 * @Title: getEndOfMonth
	 * @Description: 得到一个月的最后一个时间
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static Date getEndOfMonth(Date date) {
		int year, month;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		if (month > 11) {
			year++;
			month = 0;
		}
		cal.set(year, month, 0);
		return getEndOfDay(cal.getTime());
	}

	/**
	 * 
	 * @Title: getYear
	 * @Description: 获取年份
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @Title: getMonth
	 * @Description: 获取月份
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @Title: getDay
	 * @Description: 获取日期
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 
	 * @Title: getBeforeYearDate
	 * @Description: 获取给定时间往前推一年的时间
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static Date getBeforeYearDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -1);
		date = c.getTime();
		return date;
	}

	/**
	 * 
	 * @Title: getWeekOfDate
	 * @Description: 获取给定时间星期中文字符串
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static String getWeekStrOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 
	 * @Title: getWeekOfDate
	 * @Description: 获取给定时间星期索引 ( 0:星期天; 1:星期一; 2:星期二; 3:星期三; 4:星期四; 5:星期五;
	 *               6:星期六;)
	 * @author YuanXu
	 * @param date
	 * @return
	 */
	public static int getWeekIndexOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return w;
	}

	/**
	 * 重置不符合规范的起始时间
	 * 
	 * @author wangjunwen
	 * @date 2017年9月19日 下午5:00:28
	 *
	 * @param startDate
	 *            起始时间
	 * @return
	 */
	public static String resetDateRangeStart(String startDate) {
		if ((null == startDate) || (startDate = startDate.trim()).isEmpty()) {
			return null;
		}

		final String regexShortDate = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
		final String regexLongTime = "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d";
		final String regexLongDate = (regexShortDate + " " + regexLongTime);

		if (startDate.matches(regexShortDate)) {
			startDate += " 00:00:00";
		} else if (!(startDate.matches(regexLongDate))) {
			return null;
		}

		return startDate;
	}

	/**
	 * 重置不符合规范的结束时间
	 * 
	 * @author wangjunwen
	 * @date 2017年9月19日 下午5:05:35
	 *
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static String resetDateRangeEnd(String endDate) {
		if ((null == endDate) || (endDate = endDate.trim()).isEmpty()) {
			return null;
		}

		final String regexShortDate = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
		final String regexLongTime = "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d";
		final String regexLongDate = (regexShortDate + " " + regexLongTime);

		if (endDate.matches(regexShortDate)) {
			endDate += " 23:59:59";
		} else if (endDate.matches(regexLongDate)) {
			if (endDate.endsWith("00:00:00")) {
				endDate = endDate.replaceAll("00:00:00", "23:59:59");
			} else {
				return null;
			}
		} else {
			return null;
		}

		return endDate;
	}

	/**
	 * 将日期转换为 yyyyMMddHHmmss 的日期字符串
	 * 
	 * @param date
	 *            待转换的日期
	 * @return
	 */
	public static String toOnlyNumberStringDatetime(Date date) throws Exception {
		if (null == date) {
			return null;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT_ONLY_NUMBER);

		return simpleDateFormat.format(date);
	}

	/**
	 * 计算出实际年龄（周岁：精确到年、月、日）
	 * 
	 * @author wangjunwen
	 * @date 2018年1月23日 下午2:47:10
	 *
	 * @param pBirthday
	 *            生日
	 * @return
	 */
	public static Integer computeRealAge(Date pBirthday) {
		if (null == pBirthday) {
			return null;
		}

		// 生日
		LocalDate birthday = LocalDate.parse(format(pBirthday, DEFAULT_DATE_FORMAT));

		// 当前日期
		LocalDate now = LocalDate.now();

		if (now.isBefore(birthday)) {
			return 0;
		} else {

			// 计算出年龄
			AtomicInteger age = new AtomicInteger(now.getYear() - birthday.getYear());

			if ((now.getMonthValue() - birthday.getMonthValue() <= 0)) {

				if ((now.getMonthValue() - birthday.getMonthValue() == 0)) {

					// 月份相同但是生日小于当前日期，年龄要减去 1 岁
					if ((now.getDayOfMonth() - birthday.getDayOfMonth() < 0)) {
						return age.decrementAndGet();
					}
				} else {
					// 生日的月份小于当前的月份要减去 1 岁
					return age.decrementAndGet();
				}
			}

			// 出生日期的年份与系统日期的年份相同，并且系统日期的月份大于生日的月份直接返回年龄即可
			return age.get();

		}
	}

	/**
	 * @Description: 获取两个日期之间的日期列表(包含起止日期)
	 *
	 * @param beginStr
	 * @param endStr
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年6月26日 下午1:59:45
	 */
	public static List<String> getBetweenDateList(String beginStr, String endStr){
		Date begin = parse(beginStr);
		Date end = parse(endStr);
		return getBetweenDateList(begin, end);
	}
	
	/**
	 * @Description: 获取两个日期之间的日期列表(包含起止日期)
	 *
	 * @param begin
	 * @param end
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年6月26日 下午1:56:16
	 */
	public static List<String> getBetweenDateList(Date begin, Date end){
		return getBetweenDateList(begin, end, true, DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * @Description: 获取两个日期之间的日期列表
	 *
	 * @param begin 开始日期
	 * @param end  结束日期
	 * @param isContainBeginAndEnd 是否包含起止日期
	 * @param formatStr 返回结果的日期格式
	 * @return
	 * @return：返回结果描述
	 * @throws：异常描述     
	 * @author: zhangyq
	 * @date: 2018年6月26日 下午1:51:26
	 */
	public static List<String> getBetweenDateList(Date begin, Date end, boolean isContainBeginAndEnd, String formatStr) {
		List<String> resultList = new ArrayList<String>();
		if (begin == null || end == null) {
			return resultList;
		}
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(begin);
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		if (isContainBeginAndEnd) {
			while (begin.getTime() <= end.getTime()) {
				resultList.add(format(tempStart.getTime(), formatStr));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
				begin = tempStart.getTime();
			}
		} else {
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
			while (tempStart.before(tempEnd)) {
				resultList.add(format(tempStart.getTime(), formatStr));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		return resultList;
	}
}

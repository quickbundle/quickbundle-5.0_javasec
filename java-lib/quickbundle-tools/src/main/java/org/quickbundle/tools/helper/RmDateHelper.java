package org.quickbundle.tools.helper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quickbundle.tools.support.log.RmLogHelper;

/**
 * @author   
 * 帮助实现一些通用的日期,时间处理
 */
/**
 * @author Administrator
 *
 */
public class RmDateHelper {
	/**  一天的毫秒数  */
	public final static long ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;
	/**  本地化为简体中文  */
	public final static Locale DEFAULT_CHINA_LOCALE = Locale.SIMPLIFIED_CHINESE;
	/**  时区设置为北京时间  */
	public final static TimeZone DEFAULT_CHINA_TIMEZONE = TimeZone.getTimeZone("GMT+8:00");

	public final static String[] DEFAULT_WEEK_ARRAY_DESC = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
	
	/**
	 * 取Java虚拟机系统时间, 返回当前时间戳
	 * 
	 * @return Timestamp类型的时间
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	// case 1: // 'y' - YEAR
	// case 2: // 'M' - MONTH
	// case 3: // 'd' - DATE
	// case 4: // 'k' - HOUR_OF_DAY: 1-based.  eg, 23:59 + 1 hour =>> 24:59
	// case 5: // 'H' - HOUR_OF_DAY:0-based.  eg, 23:59 + 1 hour =>> 00:59
	// case 6: // 'm' - MINUTE
	// case 7: // 's' - SECOND
	// case 8: // 'S' - MILLISECOND
	// case 9: // 'E' - DAY_OF_WEEK
	// case 10: // 'D' - DAY_OF_YEAR
	// case 11: // 'F' - DAY_OF_WEEK_IN_MONTH
	// case 12: // 'w' - WEEK_OF_YEAR
	// case 13: // 'W' - WEEK_OF_MONTH
	// case 14: // 'a' - AM_PM
	// case 15: // 'h' - HOUR:1-based.  eg, 11PM + 1 hour =>> 12 AM
	// case 16: // 'K' - HOUR: 0-based.  eg, 11PM + 1 hour =>> 0 AM
	// case 17: // 'z' - ZONE_OFFSET
	// case 18: // 'Z' - ZONE_OFFSET ("-/+hhmm" form)
	
	/**
	 * 取Java虚拟机系统时间, 返回当前日期
	 * 
	 * @return 只返回String格式的日期，yyyy-MM-dd， 长10位
	 */
	public static String getSysDate() {
		return new Timestamp(System.currentTimeMillis()).toString().substring(0,10);
	}
	
	/**
	 * 取Java虚拟机系统时间, 返回当前日期和时间
	 * 
	 * @return 返回String格式的日期和时间, yyyy-MM-dd HH:mm:ss， 长19位
	 */
	public static String getSysDateTime() {
		return new Timestamp(System.currentTimeMillis()).toString().substring(0,19);
	}

	/**
	 * 取Java虚拟机系统时间, 返回当前日期和时间+毫秒
	 * 
	 * @return 返回String格式的日期和时间, yyyy-MM-dd HH:mm:ss.SSS， 长23位
	 */
	public static String getSysDateTimeMillis() {
		String str = new Timestamp(System.currentTimeMillis()).toString();
		if(str.length() >= 23) {
			return str.substring(0, 23);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(str);
			for(int i=0; i<23-str.length(); i++) {
				sb.append("0");
			}
			return sb.toString();
		}
	}
	
	/**
	 * 获得时间戳
	 * 
	 * @param strDate YYYY-MM-DD HH24:MI:SS格式的字符串
	 * @return 时间戳
	 */
	public static Timestamp getTimestamp(String strDate) {
		if(strDate == null || strDate.trim().length()==0) {
			return null;
		}
		return Timestamp.valueOf(formatDateStr(strDate));
	}
	

	/**
	 * 获得日期戳
	 * 
	 * @param yearMonthDay
	 * @return
	 */
	public static java.sql.Date getSqlDate(String yearMonthDay) {
		if(yearMonthDay == null || yearMonthDay.trim().length()==0 ) {
			return null;
		}
		Timestamp ts = getTimestamp(yearMonthDay);
		if(ts == null) {
			return null;
		} else {
			return new java.sql.Date(ts.getTime());			
		}
	}
	
	/**
	 * 转化为yyyy-mm-dd hh:mm:ss[.f...]
	 * 
	 * @param str
	 * @return
	 */
	public static String formatDateStr(String str) {
		if(str == null) {
			return str;
		} if(str.trim().length() == 0 || str.trim().matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$")) {
			return str.trim();
		}
		//处理2010/03/01
		String strDate = str.trim().replaceAll("/", "-");
		//处理2010-01-01T00:00:00
		strDate = str.trim().replaceAll("T", " ");
		//处理'2010-03-01
		strDate = strDate.replaceAll("'", "");
        if(strDate.indexOf("-") < 0) {
        	if(strDate.length() >= 16) { //处理20100301235959
        		strDate = strDate.substring(0,4) + "-" + strDate.substring(4,6) + "-" + strDate.substring(6,8)
        			+ " " + strDate.substring(8,10) + ":" + strDate.substring(10,12) + ":" + strDate.substring(12,14);   
        	} else if(strDate.length() >= 14) { //处理201003012359
        		strDate = strDate.substring(0,4) + "-" + strDate.substring(4,6) + "-" + strDate.substring(6,8)
    				+ " " + strDate.substring(8,10) + ":" + strDate.substring(10,12) + ":00";   
    	} else if(strDate.length() >= 8) { //处理20100301
                strDate = strDate.substring(0,4) + "-" + strDate.substring(4,6) + "-" + strDate.substring(6,8);        		
        	}
        }
        //处理2010-03
        if(strDate.indexOf("-") == strDate.lastIndexOf("-")) {
            strDate = strDate + "-01";
        }
        //处理03-01-2010
        if(strDate.length() - strDate.lastIndexOf("-") == 4) {
            strDate = strDate.substring(strDate.lastIndexOf("-")+1) + "-" + strDate.substring(0, strDate.indexOf("-")) + "-" + strDate.substring(strDate.indexOf("-") + 1, strDate.lastIndexOf("-"));
        }
        //处理9999-03-01
        if(strDate.startsWith("9999")) {
            strDate = "1900-01-01 00:00:00";
        }
		if(strDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			return strDate + " 00:00:00";
		} else if(strDate.matches("^\\d{4}-(\\d{1,2})-(\\d{1,2})$")) {
			Matcher ma = Pattern.compile("^(\\d{4}-)(\\d{1,2})(-)(\\d{1,2})$").matcher(strDate);
			StringBuilder sb = new StringBuilder();
			  if(ma.find()) {
				  sb.append(ma.group(1));
				  sb.append(ma.group(2).length() == 1 ? "0" + ma.group(2) : ma.group(2));
				  sb.append(ma.group(3));
				  sb.append(ma.group(4).length() == 1 ? "0" + ma.group(4) : ma.group(4));
			  }
			  sb.append(" 00:00:00");
			return sb.toString();
		} else if(strDate.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d*)?$")) {
			Matcher ma = Pattern.compile("^(\\d{4}-)(\\d{1,2})(-)(\\d{1,2})( )(\\d{1,2})(:)(\\d{1,2})(:)(\\d{1,2})(\\.\\d*)?$").matcher(strDate);
			StringBuilder sb = new StringBuilder();
			  if(ma.find()) {
				  sb.append(ma.group(1));
				  sb.append(ma.group(2).length() == 1 ? "0" + ma.group(2) : ma.group(2));
				  sb.append(ma.group(3));
				  sb.append(ma.group(4).length() == 1 ? "0" + ma.group(4) : ma.group(4));
				  sb.append(ma.group(5));
				  sb.append(ma.group(6).length() == 1 ? "0" + ma.group(6) : ma.group(6));
				  sb.append(ma.group(7));
				  sb.append(ma.group(8).length() == 1 ? "0" + ma.group(8) : ma.group(8));
				  sb.append(ma.group(9));
				  sb.append(ma.group(10).length() == 1 ? "0" + ma.group(10) : ma.group(10));
				  sb.append(ma.group(11) != null && ma.group(11).length() > 1 ? ma.group(11) : "");
			  }
			return sb.toString();
		} else {
			RmLogHelper.error(RmDateHelper.class, "formatDateStr(" + str + ") failed");
			return str;
		}
		
	}
	
	/**
	 * 取Java虚拟机系统时间, 返回当前日历
	 * 
	 * @return 返回String格式的日期和时间, YYYY-MM-DD HH24:MI:SS， 长19位
	 */
	static String getSysDateTimeByCalendar() {
		StringBuffer str = new StringBuffer();
		Calendar rightNow = Calendar.getInstance(DEFAULT_CHINA_TIMEZONE, DEFAULT_CHINA_LOCALE);
		int iYear = rightNow.get(Calendar.YEAR);
		int iMonth = rightNow.get(Calendar.MONTH) + 1;
		int iDate = rightNow.get(Calendar.DATE);
		int iHour = rightNow.get(Calendar.HOUR_OF_DAY);
		int iMinute = rightNow.get(Calendar.MINUTE);
		int iSecond = rightNow.get(Calendar.SECOND);
		str.append(iYear);
		str.append("-");
		if(iMonth<10)
			str.append("0");
		str.append(iMonth);
		str.append("-");
		if(iDate<10)
			str.append("0");
		str.append(iDate);
		str.append(" ");
		str.append(iHour);
		if(iHour<10)
			str.append("0");
		str.append(":");
		str.append(iMinute);
		if(iMinute<0)
			str.append("0");
		str.append(":");
		if(iSecond<0)
			str.append("0");
		str.append(iSecond);
		return str.toString();
	}

	/**
	 * 取Java虚拟机系统时间, 返回当前日历
	 * 
	 * @return 只返回String格式的日期，YYYY-MM-DD， 长10位
	 */
	static String getSysDateByCalendar() {
		StringBuffer str = new StringBuffer();
		Calendar rightNow = Calendar.getInstance(DEFAULT_CHINA_TIMEZONE,DEFAULT_CHINA_LOCALE);
		int iYear = rightNow.get(Calendar.YEAR);
		int iMonth = rightNow.get(Calendar.MONTH) + 1;
		int iDate = rightNow.get(Calendar.DATE);
		str.append(iYear);
		str.append("-");
		if (iMonth < 10) {
			str.append("0");
		}
		str.append(iMonth);
		str.append("-");
		if (iDate < 10) {
			str.append("0");
		}
		str.append(iDate);
		return str.toString();
	}
	
    /**
     * 功能: 获得本地化的时间
     *
     * @param dateDesc YYYY-MM-DD HH24:MI:SS 格式的字符串
     * @return
     */
    public static Calendar getCalendar(String dateDesc) {
        Calendar c = Calendar.getInstance(RmDateHelper.DEFAULT_CHINA_TIMEZONE, RmDateHelper.DEFAULT_CHINA_LOCALE);
        c.setTime(RmDateHelper.getTimestamp(dateDesc));
        return c;
    }
    
    /**
     * 功能: 获得本地化的时间
     *
     * @param longDate 时间的长整数
     * @return
     */
    public static Calendar getCalendar(long longDate) {
        Calendar c = Calendar.getInstance(RmDateHelper.DEFAULT_CHINA_TIMEZONE, RmDateHelper.DEFAULT_CHINA_LOCALE);
        c.setTimeInMillis(longDate);
        return c;
    }
    
    /**
     * 功能: 获得格式化的日期和时间描述
     *
     * @param longDate 时间的长整数
     * @return YYYY-MM-DD HH24:MI:SS 格式的字符串
     */
    public static String getFormatDateTimeDesc(long longDate) {
        return new Timestamp(longDate).toString().substring(0, 19);
    }
    
    /**
     * 功能: 由毫秒数得到小时数
     *
     * @param longDate
     * @return
     */
    public static double getHourNumberByLong(long longDate) {
        return longDate / 60 * 60 * 1000;
    }
    
    /**
     * 功能: 判断thisDate是否是今天
     *
     * @param thisDate
     * @return
     */
    public static boolean isToday(Date thisDate) {
        String today = getFormatDateTimeDesc(System.currentTimeMillis());
        String thisDateCal = getFormatDateTimeDesc(thisDate.getTime());
        if(today.substring(0,10).endsWith(thisDateCal.substring(0,10))) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 功能: 判断long1-long2和long3-long4区域是否重叠
     *
     * @param long1
     * @param long2
     * @param long3
     * @param long4
     * @return
     */
    public static boolean isOverlap(long long1, long long2, long long3, long long4) {
        boolean returnValue = false;
        if(long3 >= long1 && long3 < long2) {
            returnValue = true;
        } else if(long4 <= long2 && long4 > long1) {
            returnValue = true;
        } else if(long3 < long1 && long4 > long2 ) {
            returnValue = true;
        }
        return returnValue;
    }

    /**
     * 功能: 转换时间为字符串，精确到分钟
     *
     * @param time1 Timestamp
     * @return String
     */
    public static String getTimePrecMinute(Timestamp time1){
        if(time1==null)
            return "";
        Calendar cal = Calendar.getInstance(RmDateHelper.DEFAULT_CHINA_TIMEZONE, RmDateHelper.DEFAULT_CHINA_LOCALE);
        cal.setTimeInMillis(time1.getTime());
        //年
        String strYear = String.valueOf(cal.get(Calendar.YEAR));
        //月
        String strMon;
        if(cal.get(Calendar.MONTH)+1<10){
            strMon = "0"+String.valueOf(cal.get(Calendar.MONTH)+1);
        }else{
            strMon = String.valueOf(cal.get(Calendar.MONTH)+1);
        }
        //日
        String strDay ;
        if(cal.get(Calendar.DATE)<10){
            strDay = "0"+String.valueOf(cal.get(Calendar.DATE));
        }else{
            strDay = String.valueOf(cal.get(Calendar.DATE));
        }
        //时
        String strHour;
        if(cal.get(Calendar.HOUR_OF_DAY)<10){
            strHour = "0" + String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        }else{
            strHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        }
        
        //分
        String strMin ;
        if(cal.get(Calendar.MINUTE)<10){
            strMin = "0" + String.valueOf(cal.get(Calendar.MINUTE));
        }else{
            strMin = String.valueOf(cal.get(Calendar.MINUTE));
        }
        return strYear+"-"+strMon+"-"+strDay+" "+strHour+":"+strMin;
    }
    
    /**
     * 功能: 转换时间为字符串，精确到天
     *
     * @param time1 Timestamp
     * @return String
     */
    public static String getStrDatePrecDay(Object otime){
        if(otime==null||otime.getClass().getName().equals("java.lang.String"))
            return "";
        Timestamp time1 = (Timestamp)otime;
        
        Calendar cal = Calendar.getInstance(RmDateHelper.DEFAULT_CHINA_TIMEZONE, RmDateHelper.DEFAULT_CHINA_LOCALE);
        cal.setTimeInMillis(time1.getTime());
        //年
        String strYear = String.valueOf(cal.get(Calendar.YEAR));
        //月
        String strMon;
        if(cal.get(Calendar.MONTH)+1<10){
            strMon = "0"+String.valueOf(cal.get(Calendar.MONTH)+1);
        }else{
            strMon = String.valueOf(cal.get(Calendar.MONTH)+1);
        }
        //日
        String strDay ;
        if(cal.get(Calendar.DATE)<10){
            strDay = "0"+String.valueOf(cal.get(Calendar.DATE));
        }else{
            strDay = String.valueOf(cal.get(Calendar.DATE));
        }
        return strYear+"-"+strMon+"-"+strDay;
    }
    
    /**
     * 功能:  比较compareDate是否比当前时间早minTime，默认三天内
     *
     * @param compareDate 要比较的时间
     * @return
     */
    public static boolean isNew (long compareDate) {
        return isNew(compareDate, 3 * ONE_DAY_MILLIS);
    }
    
    /**
     * 功能: 比较compareDate是否比当前时间早minTime
     *
     * @param compareDate 要比较的时间
     * @param minTime 最小差距
     * @return
     */
    public static boolean isNew (long compareDate, long minTime) {
        if(System.currentTimeMillis() - compareDate > minTime) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 功能: 得到系统时间如20060101010101
     *
     * @return
     */
    public static String getJoinedSysDateTime() {
        String str = getSysDateTime();
        str = str.substring(0,4) + str.substring(5,7) + str.substring(8,10) + str.substring(11,13) + str.substring(14,16) + str.substring(17,19); 
        return str;
    }
    
    public static String parseToTimeDesciption(long timeL) {
    	StringBuilder sb = new StringBuilder();
    	if(timeL / (365L * 24 * 60 * 60 * 1000) > 0) {
    		sb.append(timeL / (365L * 24 * 60 * 60 * 1000) + "年");
    		timeL = timeL % (365L * 24 * 60 * 60 * 1000);
    	}
    	if(timeL / (24L * 60 * 60 * 1000) > 0) {
    		sb.append(timeL / (24L * 60 * 60 * 1000) + "天");
    		timeL = timeL % (24L * 60 * 60 * 1000);
    	}
    	if(timeL / (60L * 60 * 1000) > 0) {
    		sb.append(timeL / (60L * 60 * 1000) + "小时");
    		timeL = timeL % (60L * 60 * 1000);
    	}
    	if(timeL / (60L * 1000) > 0) {
    		sb.append(timeL / (60L * 1000) + "分钟");
    		timeL = timeL % (60L * 1000);
    	}
    	if(sb.length() == 0) {
    		sb.append(timeL / 1000L + "秒");
    	}
    	return sb.toString();
    }
}
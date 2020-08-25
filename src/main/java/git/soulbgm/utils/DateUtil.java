package git.soulbgm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间的处理工具类
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2020-08-11 15:02:41
 */
public class DateUtil {

    /**
     * 年
     */
    public final static int YEAR = Calendar.YEAR;

    /**
     * 月
     */
    public final static int MONTH = Calendar.MONTH;

    /**
     *
     */
    public final static int WEEK = Calendar.DAY_OF_WEEK;

    /**
     * 日
     */
    public final static int DAY = Calendar.DATE;

    /**
     * 时
     */
    public final static int HOUR = Calendar.HOUR_OF_DAY;

    /**
     * 分
     */
    public final static int MINUTE = Calendar.MINUTE;

    /**
     * 秒
     */
    public final static int SECOND = Calendar.SECOND;

    /**
     * 毫秒
     */
    public final static int MILLISECOND = Calendar.MILLISECOND;

    /**
     * 年累积天
     */
    public final static int DAY_OF_YEAR = Calendar.DAY_OF_YEAR;

    /**
     * 月累积天
     */
    public final static int DAY_OF_MONTH = Calendar.DAY_OF_MONTH;

    protected final static ThreadLocal<SimpleDateFormat> LOCAL_YMDHMSS = new ThreadLocal<SimpleDateFormat>();
    protected final static ThreadLocal<SimpleDateFormat> LOCAL_YMDHMS = new ThreadLocal<SimpleDateFormat>();
    protected final static ThreadLocal<SimpleDateFormat> LOCAL_YMD = new ThreadLocal<SimpleDateFormat>();
    protected final static ThreadLocal<SimpleDateFormat> LOCAL_YM = new ThreadLocal<SimpleDateFormat>();
    protected final static ThreadLocal<SimpleDateFormat> LOCAL_HMS = new ThreadLocal<SimpleDateFormat>();

    /**
     * 年:月:日 时:分:秒:毫秒
     */
    public final static String FORMAT_YMD_HMSS = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * 年:月:日 时:分:秒
     */
    public final static String FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年:月:日
     */
    public final static String FORMAT_YMD = "yyyy-MM-dd";

    /**
     * 年:月
     */
    public final static String FORMAT_YM = "yyyy-MM";

    /**
     * 时:分:秒
     */
    public final static String FORMAT_HMS = "HH:mm:ss";

    /**
     * 每个月最大的天数
     */
    protected final static Map<Integer, Integer> MAX_DAY_MAP;

    /**
     * UTC时区
     */
    public static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");

    /**
     * DateUtil 使用的默认时区
     */
    private static TimeZone defaultTimeZone;

    static {
        defaultTimeZone = TimeZone.getDefault();
        MAX_DAY_MAP = new HashMap<>(12);
        MAX_DAY_MAP.put(1, 31);
        MAX_DAY_MAP.put(2, 29);
        MAX_DAY_MAP.put(3, 31);
        MAX_DAY_MAP.put(4, 30);
        MAX_DAY_MAP.put(5, 31);
        MAX_DAY_MAP.put(6, 30);
        MAX_DAY_MAP.put(7, 31);
        MAX_DAY_MAP.put(8, 31);
        MAX_DAY_MAP.put(9, 30);
        MAX_DAY_MAP.put(10, 31);
        MAX_DAY_MAP.put(11, 30);
        MAX_DAY_MAP.put(12, 31);
    }

    /**
     * 2月
     */
    protected final static int TWO_MONTH = 2;

    /**
     * 最大的小时
     */
    protected final static int MAX_HOUR = 23;

    /**
     * 最大的分钟
     */
    protected final static int MAX_MINUTE = 59;

    /**
     * 最大的秒
     */
    protected final static int MAX_SECOND = 59;

    /**
     * 最大的毫秒
     */
    protected final static int MAX_MILLISECOND = 999;

    /**
     * 一周的毫秒数
     */
    protected final static int MILLISECONDS_PER_WEEK = 7 * 24 * 60 * 60 * 1000;

    /**
     * 一日的毫秒数
     */
    protected final static int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    /**
     * 一小时的毫秒数
     */
    protected final static int MILLISECONDS_PER_HOUR = 60 * 60 * 1000;

    /**
     * 一分钟的毫秒数
     */
    protected final static int MILLISECONDS_PER_MINUTE = 60 * 1000;

    /**
     * 一秒钟的毫秒数
     */
    protected final static int MILLISECONDS_PER_SECOND = 1000;

    /**
     * 判断日期是否合法
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 合法返回true 不合法false
     */
    public static boolean isDateRational(int year, int month, int day) {
        return isDateRational(year, month, day, 0, 0, 0, 0);
    }

    /**
     * 判断日期是否合法
     *
     * @param year        年
     * @param month       月
     * @param day         日
     * @param hour        时
     * @param minute      分
     * @param second      秒
     * @param millisecond 毫秒
     * @return 合法返回true 不合法false
     */
    public static boolean isDateRational(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        if (year == 0 || month == 0 || day == 0) {
            return false;
        }
        boolean flag = hour >= 0 && hour <= MAX_HOUR && minute >= 0 && minute <= MAX_MINUTE && second >= 0 && second <= MAX_SECOND && millisecond >= 0 && millisecond <= MAX_MILLISECOND;
        if (!flag) {
            return false;
        }
        Integer maxDay = MAX_DAY_MAP.get(month);
        if (maxDay == null) {
            return false;
        }
        if (month == TWO_MONTH && isLeapYear(year)) {
            return day <= maxDay;
        } else {
            return day <= maxDay - 1;
        }
    }

    /**
     * 判断是否为闰年
     *
     * @param year 年
     * @return 是闰年返回true 不是返回false
     */
    public static boolean isLeapYear(int year) {
        boolean flag = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        if (flag) {
            return true;
        }
        return false;
    }

    /**
     * 获得现在时间的时间戳
     * 性能低 System.currentTimeMillis();
     *
     * @return 时间戳
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获得现在时间 Date
     *
     * @return Date
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * 设置DateUtil类使用的时区
     *
     * @param timeZone 时区
     */
    public static void setTimeZone(TimeZone timeZone) {
        if (timeZone != null) {
            defaultTimeZone = timeZone;
        }
    }

    /**
     * 获得现在时间 Calendar
     *
     * @param timeZone 时区
     * @return
     */
    public static Calendar getCalendar(TimeZone timeZone) {
        return Calendar.getInstance(timeZone);
    }

    /**
     * 获得现在时间 Calendar
     *
     * @return Calendar
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 获得现在时间 String
     * 格式：yyyy-MM-dd hh:mm:ss.SSS
     *
     * @return 字符串显示的时间
     */
    public static String getNowStr() {
        return getNowStr(FORMAT_YMD_HMS);
    }

    /**
     * 获得现在时间 String
     *
     * @param pattern 时间格式
     * @return 字符串显示的时间
     */
    public static String getNowStr(String pattern) {
        return format(getNow(), pattern);
    }

    /**
     * 获得Calendar对象的年或月等
     *
     * @param calendar Calendar对象
     * @param field    获取的field
     * @return filed对应的值
     */
    public static int getTime(Calendar calendar, int field) {
        if (calendar == null) {
            return 0;
        }
        return calendar.get(field);
    }

    /**
     * 获得一年中的年积
     *
     * @param calendar Calendar对象
     * @param ofYear   年积标识
     * @return 返回年积某某
     */
    public static int getOfYear(Calendar calendar, double ofYear) {
        if (calendar == null) {
            return 0;
        }
        Calendar startYear = getStartCalendar(calendar, YEAR);
        double difference = (double) (calendar.getTimeInMillis() - startYear.getTimeInMillis()) / ofYear;
        return (int) Math.ceil(difference);
    }

    /**
     * 获得Calendar的年
     *
     * @param calendar Calendar对象
     * @return 年
     */
    public static int getYear(Calendar calendar) {
        return getTime(calendar, YEAR);
    }

    /**
     * 获得Calendar的月
     *
     * @param calendar Calendar对象
     * @return 月
     */
    public static int getMonth(Calendar calendar) {
        return getTime(calendar, MONTH) + 1;
    }

    /**
     * 获得Calendar的属于周几
     * 1为周一  7为周日
     *
     * @return 周几
     */
    public static int getWeek(Calendar calendar) {
        int week = getTime(calendar, WEEK) - 1;
        return week == 0 ? 7 : week;
    }

    /**
     * 获得Calendar的日
     *
     * @return 日
     */
    public static int getDay(Calendar calendar) {
        return getTime(calendar, DAY);
    }

    /**
     * 获得Calendar的小时
     *
     * @return 小时
     */
    public static int getHour(Calendar calendar) {
        return getTime(calendar, HOUR);
    }

    /**
     * 获得Calendar的分钟
     *
     * @return 分钟
     */
    public static int getMinute(Calendar calendar) {
        return getTime(calendar, MINUTE);
    }

    /**
     * 获得Calendar的秒
     *
     * @return 秒
     */
    public static int getSecond(Calendar calendar) {
        return getTime(calendar, SECOND);
    }

    /**
     * 获得Calendar的毫秒
     *
     * @return 毫秒
     */
    public static int getMillisecond(Calendar calendar) {
        return getTime(calendar, MILLISECOND);
    }

    /**
     * 获得年累积天
     *
     * @return 年累积天
     */
    public static int getDayOfYear(Calendar calendar) {
        return getTime(calendar, DAY_OF_YEAR);
    }

    /**
     * 获得月累积天
     *
     * @return 月累积天
     */
    public static int getDayOfMonth(Calendar calendar) {
        return getTime(calendar, DAY_OF_MONTH);
    }

    /**
     * 获取起始日期
     * 跟现实世界一样的计时
     *
     * @param calendar Calendar对象
     * @param field    获取的field
     * @return 返回起始日期
     */
    public static Calendar getStartCalendar(Calendar calendar, int field) {
        Calendar startCalendar = null;
        if (field == YEAR) {
            startCalendar = getCalendar(getYear(calendar), 1, 1);
        } else if (field == MONTH) {
            startCalendar = getCalendar(getYear(calendar), getMonth(calendar), 1);
        } else if (field == DAY) {
            startCalendar = getCalendar(getYear(calendar), getMonth(calendar), getDay(calendar));
        } else if (field == HOUR) {
            startCalendar = getCalendar(getYear(calendar), getMonth(calendar), getDay(calendar), getHour(calendar), 0, 0);
        } else if (field == MINUTE) {
            startCalendar = getCalendar(getYear(calendar), getMonth(calendar), getDay(calendar), getHour(calendar), getMinute(calendar), 0);
        } else if (field == SECOND) {
            startCalendar = getCalendar(getYear(calendar), getMonth(calendar), getDay(calendar), getHour(calendar), getMinute(calendar), getSecond(calendar));
        } else if (field == MILLISECOND) {
            startCalendar = getCalendar(getYear(calendar), getMonth(calendar), getDay(calendar), getHour(calendar), getMinute(calendar), getSecond(calendar), getMillisecond(calendar));
        } else if (field == WEEK) {
            startCalendar = getCalendar(getYear(calendar), getMonth(calendar), getDay(calendar));
            startCalendar = offsetDate(startCalendar, DAY, 1 - getWeek(calendar));
        }
        return startCalendar;
    }

    /**
     * 设置时间（年月日时分秒毫秒）
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return Calendar
     */
    public static Calendar setCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = getCalendar();
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar;
    }

    /**
     * 设置时间（年月日）
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return Calendar
     */
    public static Calendar setCalendar(int year, int month, int day) {
        Calendar calendar = getCalendar();
        calendar.set(year, month - 1, day);
        return calendar;
    }

    /**
     * 通过时间戳获得Calendar对象
     *
     * @param timeStamp 时间戳
     * @return Calendar对象
     */
    public static Calendar getCalendar(long timeStamp) {
        return getCalendar(new Date(timeStamp));
    }

    /**
     * 通过Date对象获得Calendar对象
     *
     * @param date Date对象
     * @return Calendar对象
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 设置时间（年月日时分秒毫秒）
     *
     * @param year        年
     * @param month       月
     * @param day         日
     * @param hour        时
     * @param minute      分
     * @param second      秒
     * @param millisecond 毫秒
     * @return Calendar
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar calendar = getCalendar();
        calendar.set(year, month - 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar;
    }

    /**
     * 设置时间（年月日）时分秒毫秒归零
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return Calendar
     */
    public static Calendar getCalendar(int year, int month, int day) {
        return getCalendar(year, month, day, 0, 0, 0, 0);
    }

    /**
     * 设置时间（年月日时分秒）毫秒归零
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return Calendar
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second) {
        return getCalendar(year, month, day, hour, minute, second, 0);
    }

    /**
     * 偏移日期
     * 整数就增加日期 负数就减少日期
     *
     * @param calendar Calendar对象
     * @param field    偏移的对象 YEAR MONTH ...
     * @param amount   偏移的数值
     * @return Calendar对象
     */
    public static Calendar offsetDate(Calendar calendar, int field, int amount) {
        if (calendar == null) {
            return null;
        }
        calendar.add(field, amount);
        return calendar;
    }

    /**
     * 获得时间轴列表
     * 例:假如需要获得最近24小时的时间轴列表则如下给参数
     * getTimeAxis(DateUtil.getCalendar(), DateUtil.HOUR, 24, true);
     *
     * @param endCalendar 结束时间
     * @param filed       需要偏移的字段 取值在DateUtil中 如DateUtil.HOUR
     * @param amount      需要的偏移多长时间
     * @param flag        是否保留结束时间
     * @return 时间轴列表
     */
    public static List<Calendar> getTimeAxis(Calendar endCalendar, int filed, int amount, boolean flag) {
        if (isNull(endCalendar)) {
            return null;
        }
        List<Calendar> list = new ArrayList<>(amount);
        Calendar newEndCalender = getStartCalendar(endCalendar, filed);
        int offset = amount;
        int stepSize = 1;
        if (filed == WEEK) {
            filed = DAY;
            stepSize = 7;
        }
        offset = stepSize * amount;
        if (flag) {
            offset = offset - stepSize;
        }
        Calendar startCalender = DateUtil.offsetDate(newEndCalender, filed, -offset);

        long time = startCalender.getTimeInMillis();
        for (int i = 0; i < amount; i++) {
            Calendar calendar = getCalendar(time);
            offsetDate(calendar, filed, stepSize * i);
            list.add(calendar);
        }
        return list;
    }

    public static void main(String[] args) {
        List<Calendar> list = getTimeAxis(DateUtil.getCalendar(), DateUtil.WEEK, 24, true);
        for (Calendar c : list) {
            System.out.println(format(c.getTime(), FORMAT_YMD_HMS));
        }
    }

    public static boolean isNull(Calendar... calendars) {
        for (Calendar calendar : calendars) {
            if (calendar == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Calendar... calendars) {
        for (Calendar calendar : calendars) {
            if (calendar == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 日期比较 大于并且小于
     * comparisonCalendar > startCalendar && comparisonCalendar < endCalendar
     *
     * @param comparisonCalendar 比较的日期
     * @param startCalendar      开始日期
     * @param endCalendar        结束日期
     * @return 符合条件为true 否则 false
     */
    public static boolean whetherExceedAndLessThan(Calendar comparisonCalendar, Calendar startCalendar, Calendar endCalendar) {
        if (isNull(comparisonCalendar, startCalendar, endCalendar)) {
            return false;
        }
        return comparisonCalendar.after(startCalendar) && comparisonCalendar.before(endCalendar);
    }

    /**
     * 日期比较 大于等于并且小于等于
     * comparisonCalendar > startCalendar && comparisonCalendar < endCalendar
     *
     * @param comparisonCalendar 比较的日期
     * @param startCalendar      开始日期
     * @param endCalendar        结束日期
     * @return 符合条件为true 否则 false
     */
    public static boolean whetherExceedEqualsAndLessThanEquals(Calendar comparisonCalendar, Calendar startCalendar, Calendar endCalendar) {
        if (isNull(comparisonCalendar, startCalendar, endCalendar)) {
            return false;
        }
        return (comparisonCalendar.after(startCalendar) || comparisonCalendar.equals(startCalendar)) && (comparisonCalendar.before(endCalendar) || comparisonCalendar.equals(endCalendar));
    }

    /**
     * 日期比较 大于
     * comparisonCalendar > startCalendar
     *
     * @param comparisonCalendar 比较的日期
     * @param calendar           日期
     * @return 符合条件为true 否则 false
     */
    public static boolean whetherExceed(Calendar comparisonCalendar, Calendar calendar) {
        if (isNull(comparisonCalendar, calendar)) {
            return false;
        }
        return comparisonCalendar.after(calendar);
    }

    /**
     * 日期比较 大于等于
     * comparisonCalendar > startCalendar
     *
     * @param comparisonCalendar 比较的日期
     * @param calendar           日期
     * @return 符合条件为true 否则 false
     */
    public static boolean whetherExceedEquals(Calendar comparisonCalendar, Calendar calendar) {
        if (isNull(comparisonCalendar, calendar)) {
            return false;
        }
        return comparisonCalendar.after(calendar) || comparisonCalendar.equals(calendar);
    }

    /**
     * 日期比较 小于
     * comparisonCalendar < endCalendar
     *
     * @param comparisonCalendar 比较的日期
     * @param calendar           日期
     * @return 符合条件为true 否则 false
     */
    public static boolean whetherLessThan(Calendar comparisonCalendar, Calendar calendar) {
        if (isNull(comparisonCalendar, calendar)) {
            return false;
        }
        return comparisonCalendar.before(calendar);
    }

    /**
     * 日期比较 小于等于
     * comparisonCalendar < endCalendar
     *
     * @param comparisonCalendar 比较的日期
     * @param calendar           日期
     * @return 符合条件为true 否则 false
     */
    public static boolean whetherLessThanEquals(Calendar comparisonCalendar, Calendar calendar) {
        if (isNull(comparisonCalendar, calendar)) {
            return false;
        }
        return comparisonCalendar.before(calendar) || comparisonCalendar.equals(calendar);
    }

    /**
     * 格式化时间
     * 线程安全
     *
     * @param dateStr 时间字符串
     * @param pattern 时间格式
     * @return Date
     */
    public static Date parse(String dateStr, String pattern) {
        if (dateStr == null || "".equals(dateStr) || pattern == null || "".equals(pattern)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期字符串与匹配字符串不相符", e);
        }
    }

    /**
     * 格式化时间 设置格式
     * 线程安全
     *
     * @param date    Date
     * @param pattern 时间格式
     * @return 以时间格式显示的时间字符串
     */
    public static String format(Date date, String pattern) {
        if (pattern == null || "".equals(pattern) || date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取存入ThreadLocal中的SimpleDateFormat对象 如果没有配置的则直接新建一个
     *
     * @param pattern 时间格式
     * @return SimpleDateFormat对象
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        ThreadLocal<SimpleDateFormat> local = null;
        if (FORMAT_YMD_HMSS.equals(pattern)) {
            local = LOCAL_YMDHMSS;
        } else if (FORMAT_YMD_HMS.equals(pattern)) {
            local = LOCAL_YMDHMS;
        } else if (FORMAT_YMD.equals(pattern)) {
            local = LOCAL_YMD;
        } else if (FORMAT_YM.equals(pattern)) {
            local = LOCAL_YM;
        } else if (FORMAT_HMS.equals(pattern)) {
            local = LOCAL_HMS;
        }
        SimpleDateFormat simpleDateFormat = null;
        if (local != null) {
            simpleDateFormat = local.get();
            if (simpleDateFormat == null) {
                simpleDateFormat = new SimpleDateFormat(pattern);
                local.set(simpleDateFormat);
            }
        } else {
            simpleDateFormat = new SimpleDateFormat(pattern);
        }
        simpleDateFormat.setTimeZone(defaultTimeZone);
        return simpleDateFormat;
    }

}

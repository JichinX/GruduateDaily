package me.djc.common.util;


import java.util.Calendar;
import java.util.Date;

/**
 * @author xujichang
 * @date 2019/05/16
 **/
public class CalenderUtil {
    public static long getTimeOfMonthStart() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return ca.getTimeInMillis();
    }

    public static long getTimeOfMonthStart(Date eDate) {

        Calendar ca = Calendar.getInstance();
        ca.setTime(eDate);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return ca.getTimeInMillis();
    }

    public static long getTimeOfMonthEnd() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 999);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTimeInMillis();
    }

    public static long getTimeOfMonthEnd(Date eDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(eDate);
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND, 999);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTimeInMillis();
    }

    /**
     * 获取这个月的天数
     *
     * @param eTimeInMillis
     * @return
     */
    public static int getMonthDays(long eTimeInMillis) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date(eTimeInMillis));
        return ca.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当天12点的时间点
     *
     * @param eCurrent
     * @return
     */
    public static long getMidDayMs(long eCurrent) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date(eCurrent));
        ca.set(Calendar.HOUR_OF_DAY, 12);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        return ca.getTimeInMillis();
    }
}

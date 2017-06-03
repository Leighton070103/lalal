package com.mad.goodgoodstudy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tong on 2017/5/28.
 */

public class TimeUtil {
    //android gradle joda time
    public final static String DEFAULT_YMD_FORMAT = "yyyy-MM-dd";
    public final static String DEFAULT_HM_FORMAT = "HH:mm";

    public static Date addTimeToDate(Date date, int duration){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, duration);
        return cal.getTime();

    }

    public static String durationToString(int duration){

        return getHourFromDuration(duration) + " Hour" + getMinFromDuration(duration) + " Min";
    }

    public static int getHourFromDuration(int duration){
        return duration/60;
    }

    public static int getMinFromDuration(int duration){
        return duration - getHourFromDuration(duration)*60;
    }

    public static String getTimeStrFromDate(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_HM_FORMAT);
        return sdf.format(date);

    }

    public static int getDurationFromHourMin(int hour, int min){
        return hour * 60 + min;
    }

    public static String getTimeStrFromDuration(int duration){
        return getTimeStringFromHourMin( getHourFromDuration(duration), getMinFromDuration(duration) );
    }

    public static String getTimeStringFromHourMin(int hour, int min){
        String str = "";
        if( hour < 10 ) str += "0" ;
        str += hour + ": ";
        if( min < 10 ) str += "0";
        str += min;
        return str;
    }

    public static Date getDateFromTimeStr( String timeStr ){
        Calendar cal = Calendar.getInstance();
        String[] timeStrs = timeStr.split(": ");
        cal.set( Calendar.HOUR, Integer.parseInt(timeStrs[0]) );
        cal.set( Calendar.MINUTE, Integer.parseInt(timeStrs[1]) );
        return cal.getTime();
    }

    public static String getCurrentDateStr(){
        Date d = getCurrentTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_YMD_FORMAT);
        return sdf.format(d);
    }

    public static Date getCurrentTime(){
        return new Date( System.currentTimeMillis() );
    }

    public static Calendar getCurrentCal(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentTime());
        return cal;
    }
}

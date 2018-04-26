package com.example.yiming.hotelmanagment.view.calendar;

import java.util.Calendar;

public class DateUtil {
    public static int getCurrentMonthLastDay(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,1); //goto first day of month
        calendar.roll(Calendar.DATE,-1); // go one day before
        int maxDate=calendar.get(Calendar.DATE);
        return maxDate;
    }

    public static String getCurrentYearAndMonth(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        return year+"-"+month;
    }

    public static int getFirstDayOfMonth(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,1); //goto first day of month
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}

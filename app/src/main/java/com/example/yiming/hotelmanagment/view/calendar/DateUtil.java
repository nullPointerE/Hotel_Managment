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
        int month=calendar.get(Calendar.MONTH);
        return year+"-"+month;
    }

    public static int getFirstDayOfMonth(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,1); //goto first day of month
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }

    public static int getMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

}

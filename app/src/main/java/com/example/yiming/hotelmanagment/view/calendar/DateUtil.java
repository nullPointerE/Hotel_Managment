package com.example.yiming.hotelmanagment.view.calendar;

import android.util.Log;

import java.util.Calendar;

public class DateUtil {
    public static int getCurrentMonthLastDay(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DATE,1); //goto first day of month
        calendar.roll(Calendar.DATE,-1); // go one day before
        int maxDate=calendar.get(Calendar.DATE);
        return maxDate;
    }

    public static int[]  getCurrentYearAndMonth(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        return new int[]{year,month};
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

    public static int[] positionToDate(int position, int startY, int startM) {
        int year = position / 12 + startY;
        int month = position % 12 + startM;

        if (month > 12) {
            month = month % 12;
            year = year + 1;
        }

        return new int[]{year, month};
    }

    public static int[] getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)};
    }
}

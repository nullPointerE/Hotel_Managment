package com.example.yiming.hotelmanagment.common;

import com.example.yiming.hotelmanagment.R;

public class Constants {
    public static final String KEY_NAME = "KEY_NAME";

    public static final String GUEST_INFO_BUNDLE_KEY="GUEST_INFO";


    //   room status
    public static final int isVacant =0;
    public static final int isBooked = 1;
    public static final int isCheckIn = 2;
    public static final long autoCancellTime=86400000; // one day milliSecond

    //  customer status
    public static final int roomIsGuaranteed =1;
    public static final int roomNotGuaranteed =0;

    // food status
    public static final int foodIsPayed=1;
    public static final int foodNotPayed=0;

    //constant lable name
    public static final String ROOMS="ROOMS";
    public static final String BOOKED="BOOKED";

    //fake information for room
    public static final Double[]  PRRICE={300.0,330.0,540.0,720.0};
    public static final int[]  BEDS={1,2,3,5};
    public static final int[]  PIC={R.drawable.r1,R.drawable.r2,R.drawable.r3,R.drawable.r4};


    //date click flag
    public static final int notClick =0;
    public static final int clicked = 1;
    public static final int betweenClick=2;
    public static final String EMPLOYEE_END_URL ="" ;
    public static final String EPLOYEE_BASE_URL ="" ;
}

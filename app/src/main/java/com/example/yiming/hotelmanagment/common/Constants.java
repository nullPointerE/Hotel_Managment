package com.example.yiming.hotelmanagment.common;

import com.example.yiming.hotelmanagment.R;

public class Constants {
    public static final String KEY_NAME = "KEY_NAME";
    public static final String GUEST_INFO_BUNDLE_KEY="GUEST_INFO";
    // boolean for sqlite
    public static final int isVacant =1;
    public static final int roomIsGuaranteed =1;

    //constant lable name
    public static final String ROOMS="ROOMS";
    public static final String BOOKED="BOOKED";

    //fake information for room
    public static final int[]  PRRICE={330,540,1000};
    public static final int[]  BEDS={2,3,5};
    public static final int[]  PIC={R.drawable.r1,R.drawable.r2,R.drawable.r3};

    // room status on calendar
    public static final int EMPTY=0;
    public static final int ORDER=1;
    public static final int STAY=2;
}

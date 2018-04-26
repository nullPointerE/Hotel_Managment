package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CalendarPagerAdapter extends PagerAdapter {
    Context context;
    CalendarPagerAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SignDate signDate=new SignDate(context);
        container.addView(signDate);
        return signDate;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
    }

}

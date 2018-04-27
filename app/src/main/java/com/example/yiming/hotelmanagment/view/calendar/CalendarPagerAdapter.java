package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.yiming.hotelmanagment.R;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CalendarPagerAdapter extends PagerAdapter {
    private Context context;
    private LinkedList<SignDate> cache = new LinkedList<>();
    private int[] currentYearAndMonth;
    private SparseArray<SignDate> mViews = new SparseArray<>();

    private Date[] clickDate;
    CalendarPagerAdapter(Context context){
        this.context=context;
        currentYearAndMonth=DateUtil.getCurrentYearAndMonth();
    }

    @Override
    public int getCount() {
        return 24;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SignDate signDate;
        if (!cache.isEmpty()) {
            signDate = cache.removeFirst();
        }else {
            signDate = new SignDate(container.getContext());
        }
        int[] yearAndMonth=DateUtil.positionToDate(position,currentYearAndMonth[0],currentYearAndMonth[1]);
        signDate.init(yearAndMonth[0],yearAndMonth[1]);

        signDate.setOnSignedSuccess(new ChangeStatusListener() {
            @Override
            public void onOrderSuccess(Date[] dateList) {
                clickDate=dateList;
            }
        });
        container.addView(signDate);
        return signDate;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((SignDate) object);
        cache.addLast((SignDate) object);
        mViews.remove(position);
    }

    public SparseArray<SignDate> getViews() {
        return mViews;
    }

    public Date[] getClickDateList(){
        return clickDate;
    }
}

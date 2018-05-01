package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.RoomHist;
import com.example.yiming.hotelmanagment.data.livedata.module.RoomTrans;

import junit.framework.TestCase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CalendarPagerAdapter extends PagerAdapter {
    private Context context;
    private int[] currentYearAndMonth;
    private LinkedList<SignDate> mViews = new LinkedList<>();
    private TextView totalPrice;
    double singlePrice;
    private Date[] clickDate;


    SignDate signDate;

    CalendarPagerAdapter(Context context, TextView totalprice, double singlPrice) {
        this.context = context;
        currentYearAndMonth = DateUtil.getCurrentYearAndMonth();
        totalPrice = totalprice;
        singlePrice = singlPrice;
    }

    @Override
    public int getCount() {
        return 24;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        signDate = new SignDate(container.getContext());
        mViews.add(signDate);
        int[] yearAndMonth = DateUtil.positionToDate(position, currentYearAndMonth[0], currentYearAndMonth[1]);
        signDate.init(yearAndMonth[0], yearAndMonth[1]);

        signDate.setOnSignedSuccess(new ChangeStatusListener() {
            @Override
            public void onOrderSuccess(Date[] dateList) {
                clickDate = dateList;
                Double total;
                if (clickDate[0] != null && clickDate[1] != null) {
                    total = singlePrice * (Math.ceil((clickDate[1].getTime() - clickDate[0].getTime()) / (1000 * 3600 * 24)) + 1);
                } else {
                    total = singlePrice;
                }
                totalPrice.setText(String.valueOf(total));
            }
        });
        container.addView(signDate);
        return signDate;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((SignDate) object);
        mViews.remove(position);
    }

    public Date[] getClickDateList() {
        return clickDate;
    }


    public void updateCalendar(List<RoomTrans> historyForCalendar) {
        Log.i("mViews ", mViews.size() + "");
        for (int i = 0; i < mViews.size(); i++) {
            mViews.get(i).updateCalendar(historyForCalendar);
        }
    }
}

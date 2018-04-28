package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.RoomHist;

import java.util.List;

public class SignDate extends LinearLayout {
    private TextView tvYear;
    private InnerGridView gvWeek;
    private InnerGridView gvDate;
    private AdapterDate adapterDate;

    public SignDate(Context context){
        super(context);
    }

    public void init(int year, int month){
        View view = View.inflate(getContext(), R.layout.calendar_signdate,this);
        tvYear = view.findViewById(R.id.tvYear);
        gvWeek = view.findViewById(R.id.gvWeek);
        gvDate = view.findViewById(R.id.gvDate);


        tvYear.setText(year+"-"+month);
        gvWeek.setAdapter(new AdapterWeek(getContext()));


        adapterDate = new AdapterDate(getContext(),year,month);
        gvDate.setAdapter(adapterDate);
    }
    /**
     * 签到成功的回调
     * @param
     */
    public void setOnSignedSuccess(ChangeStatusListener changeStatusListener){
        adapterDate.setChangeStatusListener(changeStatusListener);
    }

    public void updateCalendar(List<RoomHist> historyForCalendar) {
        adapterDate.updateDateCalendar(historyForCalendar);
    }
}

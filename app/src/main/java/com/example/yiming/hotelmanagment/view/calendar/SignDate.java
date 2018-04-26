package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;

public class SignDate extends LinearLayout {
    private TextView tvYear;
    private InnerGridView gvWeek;
    private InnerGridView gvDate;
    private AdapterDate adapterDate;

    public SignDate(Context context){
        super(context);
        init();
    }

    public SignDate(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public SignDate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SignDate(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        View view = View.inflate(getContext(), R.layout.calendar_signdate,this);
        tvYear = view.findViewById(R.id.tvYear);
        gvWeek = view.findViewById(R.id.gvWeek);
        gvDate = view.findViewById(R.id.gvDate);
        tvYear.setText(DateUtil.getCurrentYearAndMonth());
        gvWeek.setAdapter(new AdapterWeek(getContext()));
        adapterDate = new AdapterDate(getContext());
        gvDate.setAdapter(adapterDate);
    }
    /**
     * 签到成功的回调
     * @param
     */
    public void setOnSignedSuccess(ChangeStatusListener changeStatusListener){
        adapterDate.setChangeStatusListener(changeStatusListener);
    }

}

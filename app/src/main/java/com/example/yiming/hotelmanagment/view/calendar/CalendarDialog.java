package com.example.yiming.hotelmanagment.view.calendar;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yiming.hotelmanagment.R;

public class CalendarDialog extends DialogFragment  {
    private SignDate signDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.calendar_dialog,container,false);
        signDate =v.findViewById(R.id.signDate);
        signDate.setOnSignedSuccess(new ChangeStatusListener() {
            @Override
            public void onOrderSuccess() {
                Log.e("wqf","Success");
            }
            @Override
            public void onCheckInSuccess() {

            }
            @Override
            public void onChechOutSuccess() {

            }
        });

        return v;
    }
}

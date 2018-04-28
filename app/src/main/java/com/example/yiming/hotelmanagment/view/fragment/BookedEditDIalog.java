package com.example.yiming.hotelmanagment.view.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract;

import java.util.Calendar;

public class BookedEditDIalog extends DialogFragment {
    Button checkIn;
    Button checkOut;
    Button cancel;
    int transactionId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.booked_room_edit,container,false);
        checkIn=v.findViewById(R.id.checkIn);
        checkOut=v.findViewById(R.id.checkOut);
        cancel=v.findViewById(R.id.cancel);
        transactionId =getArguments().getInt(TasksPersistenceContract.RoomTransaction.transactionId);

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksLocalDataSource.getInstance(getActivity()).roomCheckIn(transactionId, Calendar.getInstance().getTime());
            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksLocalDataSource.getInstance(getActivity()).roomCheckOut(transactionId, Calendar.getInstance().getTime());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksLocalDataSource.getInstance(getActivity()).cancelRoom(transactionId);
            }
        });

        return v;
    }
}

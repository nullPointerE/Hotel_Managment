package com.example.yiming.hotelmanagment.view.fragment;


import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.FoodOrder;
import com.example.yiming.hotelmanagment.data.livedata.RoomDatabase.RoomViewModel;
import com.example.yiming.hotelmanagment.data.livedata.module.RoomTrans;
import com.example.yiming.hotelmanagment.util.adapter.BookFragmentAdapter;
import com.example.yiming.hotelmanagment.util.firebase.FireBaseMessage;
import com.example.yiming.hotelmanagment.util.firebase.FireBaseRealTime;
import com.example.yiming.hotelmanagment.view.MainActivity;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class BookedEditDIalog extends DialogFragment implements View.OnClickListener {
    private TextView degreeOfLightsTV, editMealsTV, breakfastTimeTv, lunchTimeTv, dinnerTimeTv;
    private SeekBar seekBar;
    private String format="";
    private int hour, minute;
    private Button checkIn, checkOut;
    Button orderFood;
    //int transactionId;
    BookFragmentAdapter bookFragmentAdapter;
    RoomViewModel roomViewModel;
    RoomTrans roomTrans;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.booked_room_edit,container,false);
        seekBar = v.findViewById(R.id.degreeOfLightsSeekBar);
        degreeOfLightsTV = v.findViewById(R.id.degreeOfLightsTV);
        checkIn= v.findViewById(R.id.checkinButton);
        checkOut= v.findViewById(R.id.checkOutButton);
        orderFood=v.findViewById(R.id.orderFood);
        roomViewModel= ViewModelProviders.of(this).get(RoomViewModel.class);
        //status = getArguments().getInt(TasksPersistenceContract.RoomTransaction.status);
        //transactionId =getArguments().getInt(TasksPersistenceContract.RoomTransaction.transactionId);
        roomTrans=getArguments().getParcelable(Constants.RoomTrans);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                degreeOfLightsTV.setText(""+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        editMealsTV = v.findViewById(R.id.editMealsTV);
        breakfastTimeTv = v.findViewById(R.id.breakfastTimeTv);
        lunchTimeTv = v.findViewById(R.id.lunchTimeTv);
        dinnerTimeTv = v.findViewById(R.id.dinnerTimeTv);
        if(roomTrans.getStatus()==Constants.isCheckIn) {
            checkIn.setEnabled(false);
            checkIn.setTextColor(getResources().getColor(R.color.white_transparent));
        }
        if(roomTrans.getStatus()!=Constants.isCheckIn){
            checkOut.setEnabled(false);
            checkOut.setTextColor(getResources().getColor(R.color.white_transparent));
        }
        checkIn.setOnClickListener(this);
        checkOut.setOnClickListener(this::onClick);
        breakfastTimeTv.setText("6:30");
        lunchTimeTv.setText("1:30");
        dinnerTimeTv.setText("7:30");
        lunchTimeTv.setEnabled(false);
        dinnerTimeTv.setEnabled(false);
        breakfastTimeTv.setEnabled(false);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
            breakfastTimeTv.setText(i+":"+i1);
            }
        }, hour, minute, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.setCanceledOnTouchOutside(true);
        TimePickerDialog timePickerDialogLunch = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                lunchTimeTv.setText(i+":"+i1);
            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.setCanceledOnTouchOutside(true);
        TimePickerDialog timePickerDialogDinner = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                dinnerTimeTv.setText(i+":"+i1);
            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.setCanceledOnTouchOutside(true);
        editMealsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editMealsTV.getText().toString().equals("Edit")){
            lunchTimeTv.setEnabled(true);
            dinnerTimeTv.setEnabled(true);
            breakfastTimeTv.setEnabled(true);
            editMealsTV.setText("Save");
                }
                else if(editMealsTV.getText().toString().equals("Save")){
                    breakfastTimeTv.setEnabled(false);
                    lunchTimeTv.setEnabled(false);
                    dinnerTimeTv.setEnabled(false);
                    editMealsTV.setText("Edit");
                }
            }
        });
        breakfastTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
        lunchTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialogLunch.show();
            }
        });
        dinnerTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialogDinner.show();
            }
        });


        orderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FireBaseRealTime fireBaseRealTime= new FireBaseRealTime();
                FoodOrder foodOrder=new FoodOrder();
                foodOrder.setCustomerId(10);
                foodOrder.setOrderId(10);
                foodOrder.setRoomNumber(101);
                foodOrder.setStatus(2);
                foodOrder.setTotalPrice(200);
                foodOrder.setFoodName("name");
                fireBaseRealTime.start(foodOrder);
            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checkinButton:
                roomTrans.setStatus(Constants.isCheckIn);
                roomTrans.setActualCheckInDate(Calendar.getInstance().getTime().getTime());
                roomViewModel.update(roomTrans);
                Toast.makeText(getActivity(), "Status"+roomTrans.getStatus(), Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.checkOutButton:
                roomViewModel.checkOut(roomTrans.getTransactionId());
                dismiss();
                break;
        }
    }


}


package com.example.yiming.hotelmanagment.view.calendar;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.common.RoomHist;
import com.example.yiming.hotelmanagment.data.livedata.RoomDatabase.RoomViewModel;
import com.example.yiming.hotelmanagment.data.livedata.module.RoomTrans;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarDialog extends DialogFragment {
    Spinner avaliable_roomNumber;
    Spinner book_customer;
    Button commit_book;
    Date[] clickDateList;
    int beds;
    double singlePrice;
    TextView totalPrice;
    ArrayAdapter<String> roomAdapter;
    ArrayAdapter<String> custAdapter;
    CalendarPagerAdapter calendarPagerAdapter;
    ArrayList<Customer> customerList;
    List<RoomTrans> historyForCalendar;

    RoomViewModel roomViewModel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.calendar_viewpager,container,false);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        avaliable_roomNumber=v.findViewById(R.id.avaliable_roomNumber);
        book_customer=v.findViewById(R.id.book_customer);
        commit_book=v.findViewById(R.id.commit_book);
        totalPrice=v.findViewById(R.id.totalPrice);

        beds=getArguments().getInt(Constants.ROOMS);
        singlePrice=getArguments().getDouble("singlePrice");
        calendarPagerAdapter=new CalendarPagerAdapter(getActivity(),totalPrice,singlePrice);
        viewPager.setAdapter(calendarPagerAdapter);

        roomViewModel=ViewModelProviders.of(this).get(RoomViewModel.class);


        ArrayList<String> aval_roomNumber=new ArrayList<>();
        ArrayList<String> boo_customer=new ArrayList<>();
        ArrayList<Room> roomlist= (ArrayList<Room>) TasksLocalDataSource.getInstance(getActivity()).getRoomListByBeds(beds);
        for(int i=0;i<roomlist.size();i++){
            aval_roomNumber.add(roomlist.get(i).getRoomNumber()+"");
        }
        customerList= (ArrayList<Customer>) TasksLocalDataSource.getInstance(getActivity()).getCustomerList();
        for(int i=0;i<customerList.size();i++){
            Customer customer=customerList.get(i);
            boo_customer.add(customer.getFirstName()+" "+customer.getLastName());
        }

        roomAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,aval_roomNumber);
        custAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,boo_customer);

        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        custAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        avaliable_roomNumber.setAdapter(roomAdapter);
        avaliable_roomNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedNumber= Integer.valueOf((String) parent.getSelectedItem());
                historyForCalendar=roomViewModel.getRoomTransByRoomNumber(selectedNumber);
                if(historyForCalendar!=null){
                    calendarPagerAdapter.updateCalendar(historyForCalendar);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        book_customer.setAdapter(custAdapter);

        commit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //book room
                clickDateList=calendarPagerAdapter.getClickDateList();
                if(clickDateList[0]==null || clickDateList[1]==null ){
                    Toast.makeText(getActivity(),"not set date",Toast.LENGTH_LONG).show();
                }else{
                    if(avaliable_roomNumber.getSelectedItem()!=null && book_customer.getSelectedItem()!=null ){
                        int roomNumber=Integer.valueOf(avaliable_roomNumber.getSelectedItem().toString());
                        int custNumber=customerList.get(book_customer.getSelectedItemPosition()).getCustomerId();
                        Log.i("commit_book ", roomNumber+" "+custNumber);
                        RoomTrans roomTrans=new RoomTrans();
                        roomTrans.setRoomNumber(roomNumber);
                        roomTrans.setOwedByCustomer(custNumber);
                        roomTrans.setExpectCheckInDate(clickDateList[0].getTime());
                        roomTrans.setExpectCheckOutDate(clickDateList[1].getTime());
                        roomTrans.setTotalPrice(Double.valueOf(totalPrice.getText().toString()));

                        roomViewModel.bookRoom(roomTrans);
                       // TasksLocalDataSource.getInstance(getActivity()).bookRoom(roomNumber,custNumber,clickDateList[0],clickDateList[1],Double.valueOf(totalPrice.getText().toString()));
                        dismiss();
                    }
                }
            }
        });

        return v;
    }
}



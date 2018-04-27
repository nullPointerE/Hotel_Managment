package com.example.yiming.hotelmanagment.view.calendar;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarDialog extends DialogFragment  {
    Spinner avaliable_roomNumber;
    Spinner book_customer;
    Button commit_book;
    Date[] clickDateList;
    boolean room_choosed=false;
    boolean customer_choosed=false;
    int beds;

    ArrayAdapter<String> roomAdapter;
    ArrayAdapter<String> custAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.calendar_viewpager,container,false);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        avaliable_roomNumber=v.findViewById(R.id.avaliable_roomNumber);
        book_customer=v.findViewById(R.id.book_customer);
        commit_book=v.findViewById(R.id.commit_book);
        commit_book.setEnabled(false);

        beds=getArguments().getInt(Constants.ROOMS);
        final CalendarPagerAdapter calendarPagerAdapter=new CalendarPagerAdapter(getActivity());
        viewPager.setAdapter(calendarPagerAdapter);
        ArrayList<String> aval_roomNumber=new ArrayList<>();
        ArrayList<String> boo_customer=new ArrayList<>();
        ArrayList<Room> roomlist= (ArrayList<Room>) TasksLocalDataSource.getInstance(getActivity()).getRoomListByBeds(beds);
        for(int i=0;i<roomlist.size();i++){
            aval_roomNumber.add(roomlist.get(i).getRoomNumber()+"");
            room_choosed=true;
            if(customer_choosed){
                commit_book.setEnabled(true);
            }
        }
        ArrayList<Customer> customerList= (ArrayList<Customer>) TasksLocalDataSource.getInstance(getActivity()).getCustomerList();
        for(int i=0;i<customerList.size();i++){
            Customer customer=customerList.get(i);
            boo_customer.add(customer.getFirstName()+" "+customer.getLastName());
            customer_choosed=true;
            if(room_choosed){
                commit_book.setEnabled(true);
            }
        }

        roomAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,aval_roomNumber);
        custAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,boo_customer);

        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        custAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        avaliable_roomNumber.setAdapter(roomAdapter);
        book_customer.setAdapter(custAdapter);

        commit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //book room
                clickDateList=calendarPagerAdapter.getClickDateList();
                if(clickDateList[0]==null || clickDateList[1]==null){
                    Toast.makeText(getActivity(),"not set date",Toast.LENGTH_LONG).show();
                }else{
                    int roomNumber=Integer.valueOf(avaliable_roomNumber.getSelectedItem().toString());
                    int custNumber=Integer.valueOf(book_customer.getSelectedItem().toString());
                    Log.i("commit_book ", roomNumber+" "+custNumber);
                    TasksLocalDataSource.getInstance(getActivity()).bookRoom(roomNumber,custNumber,clickDateList[0],clickDateList[1]);
                    dismiss();
                }
            }
        });

        return v;
    }
}



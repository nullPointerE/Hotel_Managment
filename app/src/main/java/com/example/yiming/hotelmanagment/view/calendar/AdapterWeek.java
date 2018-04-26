package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;

public class AdapterWeek extends BaseAdapter {
    private String[] week = {"Su","Mo","Tu","We","Th","Fr","Sa"};
    private Context context;

    public AdapterWeek(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return week.length;
    }

    @Override
    public Object getItem(int position) {
        return week[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.calendar_item,null);
        }
        tv=convertView.findViewById(R.id.tvWeek);
        tv.setText(week[position]);
        return convertView;
    }
}

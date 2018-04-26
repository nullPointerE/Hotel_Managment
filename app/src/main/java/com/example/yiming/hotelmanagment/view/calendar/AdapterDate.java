package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;

import java.util.ArrayList;
import java.util.List;

public class AdapterDate extends BaseAdapter {
    private Context context;
    private List<Integer> days;

    //set the day is order, occupy, empty
    private List<Integer> status;

    //签到成功的回调方法，相应的可自行添加签到失败时的回调方法
    private ChangeStatusListener changeStatusListener;

    AdapterDate(Context context){
        this.context=context;
        int maxDay=DateUtil.getCurrentMonthLastDay();
        days=new ArrayList<>();
        status=new ArrayList<>();
        //DateUtil.getFirstDayOfMonth()获取当月第一天是星期几，星期日是第一天，依次类推
        for(int i=0;i<DateUtil.getFirstDayOfMonth()-1;i++){
            //0代表需要隐藏的item
            days.add(0);
            //代表为签到状态
            status.add(Constants.EMPTY);
        }
        for(int i=0;i<maxDay;i++){
            //初始化日历数据
            days.add(i+1);
            //初始化日历签到状态
            status.add(Constants.EMPTY);
        }
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.calendar_item,parent,false);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv = convertView.findViewById(R.id.tvWeek);
        viewHolder.rlItem = convertView.findViewById(R.id.rlItem);
        viewHolder.ivStatus = convertView.findViewById(R.id.ivStatus);

        viewHolder.tv.setText(days.get(position)+"");
        if(days.get(position)==0){
            viewHolder.rlItem.setVisibility(View.GONE);
        }
        if(status.get(position)!=0){
            viewHolder.tv.setTextColor(Color.parseColor("#FD0000"));
            viewHolder.ivStatus.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tv.setTextColor(Color.parseColor("#666666"));
            viewHolder.ivStatus.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.get(position)!=0){
                    Toast.makeText(context,"Already sign in!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Sign in success!",Toast.LENGTH_SHORT).show();
                    status.set(position,1);
                    notifyDataSetChanged();
                    if(changeStatusListener!=null){
                        changeStatusListener.onOrderSuccess();
                    }
                }
            }
        });


        return convertView;
    }
    class ViewHolder{
        RelativeLayout rlItem;
        TextView tv;
        ImageView ivStatus;
    }

    //call back method
    public void setChangeStatusListener(ChangeStatusListener changeStatusListener){
        this.changeStatusListener = changeStatusListener;
    }
}

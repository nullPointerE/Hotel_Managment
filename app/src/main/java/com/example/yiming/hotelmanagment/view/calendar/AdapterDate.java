package com.example.yiming.hotelmanagment.view.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterDate extends BaseAdapter {
    private Context context;
    private List<Integer> days;

    //track what we have clicked;
    private Date[] clickDate;
    private List<ViewHolder> viewHolderList;
    //set the day is vacant, ordered, checkin
    private List<Integer> status;
    int start=-1;
    int end=-1;
    int year;
    int month;
    //签到成功的回调方法，相应的可自行添加签到失败时的回调方法
    private ChangeStatusListener changeStatusListener;

    AdapterDate(Context context, int year, int month){
        Log.i("AdapterDate ",year+" "+month);
        this.context=context;
        int maxDay=DateUtil.getMonthDays(year,month);

        this.year=year;
        this.month=month;
        days=new ArrayList<>();
        status=new ArrayList<>();
        clickDate=new Date[2];
        viewHolderList=new ArrayList<>();

        //DateUtil.getFirstDayOfMonth()获取当月第一天是星期几，星期日是第一天，依次类推
        for(int i=0;i<getFirstDayOfMonth(year,month);i++){
            //0代表需要隐藏的item
            days.add(0);
            //代表为签到状态
            status.add(Constants.notClick);
        }
        for(int i=0;i<maxDay;i++){
            //初始化日历数据
            days.add(i+1);
            //初始化日历签到状态
            status.add(Constants.notClick);
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
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.calendar_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolderList.add(viewHolder);
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
        changeColor(viewHolder,position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.get(position)==Constants.clicked){
                    Toast.makeText(context,"cancel!",Toast.LENGTH_SHORT).show();
                    for(int i=start+1;i<end;i++){
                        status.set(i,Constants.notClick);
                    }
                    if(position==start){
                        // disable start
                        start=end;
                        clickDate[0]=null;
                    }else{
                        //disable end
                        end=start;
                        clickDate[1]=null;
                    }
                    status.set(position,Constants.notClick);
                    notifyDataSetChanged();
                    if(changeStatusListener!=null){
                        changeStatusListener.onOrderSuccess(clickDate);
                    }
                }else if(status.get(position) == Constants.notClick){
                    Toast.makeText(context,"Sign in success!",Toast.LENGTH_SHORT).show();
                    if(start==end && start==-1){
                        start=end=position;
                        clickDate[0]=getDateByDay(days.get(start));
                        clickDate[1]=getDateByDay(days.get(end));
                    }else if(start>position && start<end){
                        start=position;
                        clickDate[0]=getDateByDay(days.get(start));
                    }else if(end<position && start<end){
                        end=position;
                        clickDate[1]=getDateByDay(days.get(end));
                    }else{
                        //start==end
                        if(start>position){
                            start=position;
                            clickDate[0]=getDateByDay(days.get(start));
                        }else{
                            end=position;
                            clickDate[1]=getDateByDay(days.get(end));
                        }
                    }
                    for(int i=start+1;i<end;i++){
                        status.set(i,Constants.betweenClick);
                    }
                    status.set(position,Constants.clicked);
                    notifyDataSetChanged();
                    if(changeStatusListener!=null){
                        changeStatusListener.onOrderSuccess(clickDate);
                    }
                }else{
                    //status ==Constants.betweenSelected
                    Toast.makeText(context,"already selected!",Toast.LENGTH_SHORT).show();
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

    private int getFirstDayOfMonth(int year, int month){
        Log.i("yearmonth ", year+" "+month);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1); //goto first day of month
        Log.i("getDatebyYear ",calendar.getTime().toString());
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }

    private void changeColor(ViewHolder viewHolder,int position){
        if(status.get(position)==Constants.clicked){
            // pink background with mark
            viewHolder.tv.setTextColor(Color.parseColor("#FF4081"));
            viewHolder.ivStatus.setVisibility(View.VISIBLE);
        }else if(status.get(position)==Constants.notClick){
            // black background
            viewHolder.tv.setTextColor(Color.parseColor("#000000"));
            viewHolder.ivStatus.setVisibility(View.GONE);
        }else{
            //grey background
            viewHolder.tv.setTextColor(Color.parseColor("#633e3d3d"));
            viewHolder.ivStatus.setVisibility(View.GONE);
        }
    }
    private Date getDateByDay(int day){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        return calendar.getTime();
    }
}

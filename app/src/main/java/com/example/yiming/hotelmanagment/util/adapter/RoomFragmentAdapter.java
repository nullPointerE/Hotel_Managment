package com.example.yiming.hotelmanagment.util.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.view.MainActivity;
import com.example.yiming.hotelmanagment.view.calendar.CalendarDialog;

public class RoomFragmentAdapter extends RecyclerView.Adapter<RoomFragmentAdapter.Holder> {
    Context context;
    MainActivity mainActivity;
    public RoomFragmentAdapter(Context context){
        mainActivity= (MainActivity) context;
        this.context=context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.room_recycler_item,parent,false);
        Holder holder=new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.imageView.setImageResource(Constants.PIC[position]);
        holder.beds.setText(Constants.BEDS[position]+"");
        holder.price.setText(Constants.PRRICE[position]+"");
        holder.bed=Constants.BEDS[position];
        holder.singlePrice=Constants.PRRICE[position];
    }

    @Override
    public int getItemCount() {
        return Constants.PIC.length;
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView price;
        TextView beds;
        TextView book;
        int bed;
        double singlePrice;
        public Holder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.roomPic);
            beds=itemView.findViewById(R.id.beds);
            price=itemView.findViewById(R.id.price);
            book=itemView.findViewById(R.id.book);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CalendarDialog calendarDialog=new CalendarDialog();
                    Bundle bundle=new Bundle();
                    bundle.putInt(Constants.ROOMS,bed);
                    bundle.putDouble("singlePrice",singlePrice);
                    calendarDialog.setArguments(bundle);
                    calendarDialog.show(mainActivity.getFragmentManager(),"calendar");
                }
            });
        }
    }
}

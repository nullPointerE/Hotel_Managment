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
import com.example.yiming.hotelmanagment.view.fragment.BookedEditDIalog;

public class BookFragmentAdapter extends RecyclerView.Adapter<BookFragmentAdapter.Holder> {
    Context context;
    MainActivity mainActivity;
    public BookFragmentAdapter(Context context){
        this.context=context;
        mainActivity= (MainActivity) context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.book_recycler_item,parent,false);
        Holder holder=new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.imageView.setImageResource(Constants.PIC[position]);
        holder.beds.setText(Constants.BEDS[position]+"");
        holder.booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView beds;
        ImageView booking;

        public Holder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.roomPic);
            beds=itemView.findViewById(R.id.beds);
            booking=itemView.findViewById(R.id.booking);
            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookedEditDIalog bookedEditDIalog=new BookedEditDIalog();
                    Bundle bundle=new Bundle();
                    bookedEditDIalog.setArguments(bundle);
                    bookedEditDIalog.show(mainActivity.getFragmentManager(),"book edit");
                }
            });
        }
    }
}

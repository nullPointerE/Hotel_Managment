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
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.common.RoomHist;
import com.example.yiming.hotelmanagment.data.livedata.module.RoomTrans;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract;
import com.example.yiming.hotelmanagment.view.MainActivity;
import com.example.yiming.hotelmanagment.view.fragment.BookedEditDIalog;

import java.util.List;

public class BookFragmentAdapter extends RecyclerView.Adapter<BookFragmentAdapter.Holder> {
    Context context;
    MainActivity mainActivity;
    List<RoomTrans> bookedRoom;
    public BookFragmentAdapter(Context context){
        this.context=context;
        mainActivity= (MainActivity) context;
    }

    public void setList(List<RoomTrans> roomTrans){
        bookedRoom=roomTrans;
        notifyDataSetChanged();
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
        holder.imageView.setImageResource(Constants.PIC[position%4]);
        RoomTrans temp=bookedRoom.get(position);
        holder.custN.setText("Customer Name: "+temp.getOwedByCustomer()+"");
        holder.roomP.setText("Total Price: "+temp.getTotalPrice());
        holder.roomN.setText("Room Number: "+temp.getRoomNumber());
        holder.thisRoom=bookedRoom.get(position);
    }

    @Override
    public int getItemCount() {
        if(bookedRoom==null){
            return 0;
        }
        return bookedRoom.size();
    }

    public void updateList() {
       // bookedRoom= TasksLocalDataSource.getInstance(context).getRoomTrasactionsList();
      //  notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView custN;
        TextView roomP;
        TextView roomN;
        RoomTrans thisRoom;
        public Holder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.roomPic);
            custN=itemView.findViewById(R.id.custN);
            roomP=itemView.findViewById(R.id.roomP);
            roomN=itemView.findViewById(R.id.roomN);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookedEditDIalog bookedEditDIalog=new BookedEditDIalog();
                    Bundle bundle=new Bundle();
                    //bundle.putInt(TasksPersistenceContract.RoomTransaction.transactionId,thisRoom.getTransactionId());
                    //bundle.putInt(TasksPersistenceContract.RoomTransaction.status, thisRoom.getStatus());
                    bundle.putParcelable(Constants.RoomTrans,thisRoom);
                    bookedEditDIalog.setArguments(bundle);
                    bookedEditDIalog.show(mainActivity.getSupportFragmentManager(),"book edit");
                }
            });
        }
    }
}

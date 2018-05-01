package com.example.yiming.hotelmanagment.data.livedata.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.yiming.hotelmanagment.data.livedata.module.RoomDao;
import com.example.yiming.hotelmanagment.data.livedata.module.RoomTrans;

@Database(entities = {RoomTrans.class}, version = 1)
public abstract class MyRoomDataBase extends RoomDatabase{
    public abstract RoomDao roomDao();

    private static MyRoomDataBase INSTANCE;

    public static MyRoomDataBase getDataBase(final Context context){
        if(INSTANCE==null){
            synchronized (MyRoomDataBase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),MyRoomDataBase.class,"MyRoomDataBase")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

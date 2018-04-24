package com.example.yiming.hotelmanagment.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract.RoomTable;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract.Food;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract.CustomerTable;

public class TasksDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Hotel.db";




    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CustomerTable.TABLE_NAME+" (" +
                "CustomerId Integer primary key autoincrement," +
                "firstName varchar," +
                "lastName varchar," +
                "numberOfCustomers Integer not null," +
                "assignedRoom Integer," +
                "phoneNumber Integer," +
                "roomIsGuaranteed Integer not null default 0" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

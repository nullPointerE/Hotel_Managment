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
                CustomerTable.CustomerId+" Integer primary key autoincrement," +
                CustomerTable.TITLE+" varchar,"+
                CustomerTable.firstName+" varchar," +
                CustomerTable.lastName+" varchar," +
                CustomerTable.EMAILADDRESS+" varchar,"+
                CustomerTable.GENDER+" varchar,"+
                CustomerTable.COMPANYNAME+" varchar,"+
                CustomerTable.ADDRESS+" varchar,"+
                CustomerTable.CITY+" varchar,"+
                CustomerTable.POSTALCODE+" varchar,"+
                CustomerTable.COUNTRY+" varchar,"+
                CustomerTable.phoneNumber+" varchar," +
                CustomerTable.PURPOSEOFVISIT+" varchar,"+
                CustomerTable.COMMENTS+" varchar,"+
                CustomerTable.numberOfCustomers+" Integer," +
                CustomerTable.assignedRoom+" Integer," +
                CustomerTable.roomIsGuaranteed+" Integer default 0" +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

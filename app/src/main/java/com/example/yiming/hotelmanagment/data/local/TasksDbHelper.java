package com.example.yiming.hotelmanagment.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract.RoomTable;
import com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract.CustomerTable;
import static com.example.yiming.hotelmanagment.data.local.TasksPersistenceContract.*;

public class TasksDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Hotel.db";


    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CustomerTable.TABLE_NAME+" (" +
                CustomerTable.customerId+" Integer primary key autoincrement," +
                CustomerTable.firstName+" varchar(20)," +
                CustomerTable.lastName+" varchar(20)," +
                CustomerTable.numberOfCustomers+" Integer," +
                CustomerTable.assignedRoom+" Integer," +
                CustomerTable.mobilePhone+" varchar(15)," +
                CustomerTable.title+" varchar(15)," +
                CustomerTable.gender+" varchar(15)," +
                CustomerTable.creditCard+" varchar(15)," +
                CustomerTable.comments+" Text," +
                CustomerTable.roomIsGuaranteed+" Integer," +
                CustomerTable.middleName+" varchar(20)," +
                CustomerTable.emailAddress+" varchar(30)," +
                CustomerTable.companyName+" varchar(20)," +
                CustomerTable.address+" varchar(30)," +
                CustomerTable.city+" varchar(20)," +
                CustomerTable.postalCode+" varchar(10)," +
                CustomerTable.country+" varchar(10)," +
                CustomerTable.daytimePhone+" varchar(10)," +
                CustomerTable.purposeOfVisit+" varchar(50))");

        db.execSQL("create table "+RoomTable.TABLE_NAME+"(" +
                RoomTable.roomNumber+" Integer primary key not null," +
                RoomTable.PRICE+" Double not null," +
                RoomTable.BEDS+" Integer not null)");

        db.execSQL("create table "+RoomTransaction.TABLE_NAME+"("+
                RoomTransaction.transactionId+" Integer primary key autoincrement," +
                RoomTransaction.roomNumber +" Integer not null," +
                RoomTransaction.status +" Integer not null," +
                RoomTransaction.owedByCustomer+" Integer not null," +
                RoomTransaction.totalPrice+" Double not null,"+
                RoomTransaction.expectCheckInDate+" INTEGER,"+
                RoomTransaction.expectCheckoOutDate+" INTEGER," +
                RoomTransaction.actualCheckInDate+" INTEGER," +
                RoomTransaction.actualCheckOutDate+" INTEGER," +
                RoomTransaction.autoCancelDate+" INTEGER)");

        db.execSQL("create table "+ FoodTable.TABLE_NAME+"(" +
                FoodTable.foodId+" Integer primary key autoincrement," +
                FoodTable.foodType+" varchar(20)," +
                FoodTable.foodName+" varchar(20)," +
                FoodTable.PRICE+" Double," +
                FoodTable.status+" Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.yiming.hotelmanagment.data.local;

import android.provider.BaseColumns;

import java.util.Date;

public class TasksPersistenceContract {
    private TasksPersistenceContract() {}


    /* Inner class that defines the table contents */
    public static abstract class CustomerTable implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String CustomerId = "CustomerId";
        public static final String firstName = "firstName";
        public static final String lastName = "lastName";
        public static final String numberOfCustomers = "numberOfCustomers";
        public static final String assignedRoom = "assignedRoom";
        public static final String phoneNumber = "phoneNumber";
        public static final String roomIsGuaranteed = "roomIsGuaranteed";
        public static final String creditCard = "creditCard";
        public static final String autoCancelDate = "autoCancelDate";
        public static final String expectCheckInDate = "expectCheckInDate";
        public static final String actualCheckInDate = "actualCheckInDate";
        public static final String expectCheckoOutDate = "expectCheckoOutDate";
        public static final String actualCheckOutDate = "actualCheckOutDate";
        public static final String feedBack = "feedBack";
    }


    public static abstract class RoomTable implements BaseColumns {
        public static final String TABLE_NAME = "room";
        public static final String roomNumber = "roomNumber";
        public static final String isVacant = "isVacant";
        public static final String owedByCustomer = "owedByCustomer";

    }

    public static abstract class Food implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String foodId = "foodId";
        public static final String foodType = "foodType";
        public static final String foodName = "foodName";
    }
}

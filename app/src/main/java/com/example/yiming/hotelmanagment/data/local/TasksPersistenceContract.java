package com.example.yiming.hotelmanagment.data.local;

import android.provider.BaseColumns;


public class TasksPersistenceContract {
    private TasksPersistenceContract() {}


    /* Inner class that defines the table contents */
    public static abstract class CustomerTable implements BaseColumns {
        // important infor for database
        public static final String TABLE_NAME = "customer";
        public static final String customerId = "customerId";
        public static final String assignedRoom = "assignedRoom";
        public static final String creditCard = "creditCard";
        public static final String roomIsGuaranteed="roomIsGuaranteed";

        // extra information
        public static final String numberOfCustomers = "numberOfCustomers";
        public static final String title = "title";
        public static final String firstName = "firstName";
        public static final String middleName = "middleName";
        public static final String lastName = "lastName";
        public static final String emailAddress = "emailAddress";
        public static final String mobilePhone = "mobilePhone";
        public static final String comments = "comments";
        public static final String gender = "gender";
        public static final String companyName = "companyName";
        public static final String address = "address";
        public static final String city = "city";
        public static final String postalCode = "postalCode";
        public static final String country = "country";
        public static final String daytimePhone = "daytimePhone";
        public static final String purposeOfVisit = "purposeOfVisit";

    }


    public static abstract class RoomTable implements BaseColumns {
        public static final String TABLE_NAME = "room";
        public static final String roomNumber = "roomNumber";
        public static final String PRICE="price";
        public static final String BEDS="beds";


    }
    public static abstract class RoomTransaction implements BaseColumns{
        public static final String TABLE_NAME = "roomBook";
        public static final String transactionId="transactionId";
        public static final String roomNumber = "roomNumber";
        public static final String status = "status";
        public static final String owedByCustomer = "owedByCustomer";
        public static final String totalPrice="totalPrice";

        public static final String autoCancelDate = "autoCancelDate";
        public static final String expectCheckInDate = "expectCheckInDate";
        public static final String actualCheckInDate = "actualCheckInDate";
        public static final String expectCheckoOutDate = "expectCheckoOutDate";
        public static final String actualCheckOutDate = "actualCheckOutDate";
    }

    public static abstract class FoodTable implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String foodId = "foodId";
        public static final String foodType = "foodType";
        public static final String foodName = "foodName";
        public static final String PRICE="price";
        public static final String status="status";
    }

//    public static abstract class FoodOrder implements BaseColumns {
//        public static final String TABLE_NAME = "foodOrder";
//        public static final String orderId = "orderId";
//        public static final String roomNumber = "roomNumber";
//        public static final String customerId = "customerId";
//        public static final String totalPrice="totalPrice";
//        public static final String status="status";
//    }
}

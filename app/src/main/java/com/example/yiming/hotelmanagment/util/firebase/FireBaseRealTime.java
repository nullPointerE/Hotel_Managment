package com.example.yiming.hotelmanagment.util.firebase;

import android.util.Log;

import com.example.yiming.hotelmanagment.common.FoodOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseRealTime {

    FirebaseDatabase database;
    DatabaseReference myRef;
    String TAG="null";

//    int orderId;
//    int roomNumber;
//    int customerId;
//    double totalPrice;
//    int status;

    public FireBaseRealTime(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FoodOrder value = dataSnapshot.child("foodOrder").getValue(FoodOrder.class);
                Log.d(TAG, "foodOrder Value is: " + value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "foodOrder Failed to read value.", error.toException());
            }
        });
    }

    public void start(FoodOrder foodOrder){
        myRef.child("foodOrder").setValue(foodOrder);
        Log.i("foodOrder ",foodOrder.getFoodName());
    }


}

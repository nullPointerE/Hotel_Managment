package com.example.yiming.hotelmanagment.data.livedata.module;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RoomDao {
    @Insert
    void bookRoom(RoomTrans roomTrans);

    @Query("DELETE FROM roomTrans")
    void deleteAll();

    @Query("SELECT * FROM roomTrans ")
    LiveData<List<RoomTrans>> getAllRoomTrans();

    @Query("SELECT * FROM roomTrans where roomNumber=:roomNumber")
    List<RoomTrans> getRoomTransByRoomNumber(int roomNumber);


    @Query("UPDATE roomTrans SET status=1,actualCheckInDate=:today WHERE transactionId=:transactionId")
    void checkIn(int transactionId, long today);

    @Query("DELETE FROM roomTrans WHERE transactionId=:transactionId")
    void checkOut(int transactionId);

    @Update
    void update(RoomTrans roomTrans);
}



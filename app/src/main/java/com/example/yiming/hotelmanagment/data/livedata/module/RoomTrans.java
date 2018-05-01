package com.example.yiming.hotelmanagment.data.livedata.module;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "roomTrans")
public class RoomTrans implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="transactionId")
    int transactionId;

    @ColumnInfo(name="roomNumber")
    int roomNumber;

    @ColumnInfo(name="totalPrice")
    double totalPrice;

    @ColumnInfo(name="status")
    int status;

    @ColumnInfo(name="owedByCustomer")
    int owedByCustomer;

    @ColumnInfo(name="actualCheckInDate")
    long actualCheckInDate;

    @ColumnInfo(name="actualCheckOutDate")
    long actualCheckOutDate;


    @ColumnInfo(name="expectCheckInDate")
    long expectCheckInDate;

    @ColumnInfo(name="expectCheckOutDate")
    long expectCheckOutDate;

    public RoomTrans(Parcel in) {
        transactionId = in.readInt();
        roomNumber = in.readInt();
        totalPrice = in.readDouble();
        status = in.readInt();
        owedByCustomer = in.readInt();
        actualCheckInDate = in.readLong();
        actualCheckOutDate = in.readLong();
        expectCheckInDate = in.readLong();
        expectCheckOutDate = in.readLong();
    }
    public RoomTrans(){

    };

    public static final Creator<RoomTrans> CREATOR = new Creator<RoomTrans>() {
        @Override
        public RoomTrans createFromParcel(Parcel in) {
            return new RoomTrans(in);
        }

        @Override
        public RoomTrans[] newArray(int size) {
            return new RoomTrans[size];
        }
    };

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOwedByCustomer() {
        return owedByCustomer;
    }

    public void setOwedByCustomer(int owedByCustomer) {
        this.owedByCustomer = owedByCustomer;
    }
    public long getActualCheckInDate() {
        return actualCheckInDate;
    }

    public void setActualCheckInDate(long actualCheckInDate) {
        this.actualCheckInDate = actualCheckInDate;
    }


    public long getActualCheckOutDate() {
        return actualCheckOutDate;
    }

    public void setActualCheckOutDate(long actualCheckOutDate) {
        this.actualCheckOutDate = actualCheckOutDate;
    }

    public long getExpectCheckInDate() {
        return expectCheckInDate;
    }

    public void setExpectCheckInDate(long expectCheckInDate) {
        this.expectCheckInDate = expectCheckInDate;
    }

    public long getExpectCheckOutDate() {
        return expectCheckOutDate;
    }

    public void setExpectCheckOutDate(long expectCheckOutDate) {
        this.expectCheckOutDate = expectCheckOutDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(transactionId);
        dest.writeInt(roomNumber);
        dest.writeDouble(totalPrice);
        dest.writeInt(status);
        dest.writeInt(owedByCustomer);
        dest.writeLong(actualCheckInDate);
        dest.writeLong(actualCheckOutDate);
        dest.writeLong(expectCheckInDate);
        dest.writeLong(expectCheckOutDate);
    }
}

package com.example.yiming.hotelmanagment.util.authentication.finger;

public interface IFingerPrintAuthManager {
   public interface OnDataListener{
        public void onSuccess();
        public void onFailure();
    }
}

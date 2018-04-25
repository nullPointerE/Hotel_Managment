package com.example.yiming.hotelmanagment.util.authentication.finger;

public interface IFingerPrintHelper {
    public interface IFingerPrintDataListener{
        public void onFingerPrintAuthSuccess();
        public void onFingerPrintAuthFailure();
    }
}

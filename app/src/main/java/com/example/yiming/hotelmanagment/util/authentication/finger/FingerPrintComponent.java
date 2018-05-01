package com.example.yiming.hotelmanagment.util.authentication.finger;

import com.example.yiming.hotelmanagment.view.LoginActivity;

import javax.inject.Singleton;

@dagger.Component(modules = {FingerPrintHelperModule.class})
@Singleton
public interface FingerPrintComponent {
    void inject(LoginActivity loginActivity);
}

package com.example.yiming.hotelmanagment.util.authentication.finger;

import android.content.Context;

import com.example.yiming.hotelmanagment.util.authentication.finger.FingerPrintHelper;
import com.example.yiming.hotelmanagment.view.IViewActivityLogin;

import dagger.Module;
import dagger.Provides;

@Module
public class FingerPrintHelperModule {
    private Context context;
    private IViewActivityLogin iViewActivityLogin;

    public FingerPrintHelperModule(Context context,IViewActivityLogin iViewActivityLogin){
        this.context=context;
        this.iViewActivityLogin=iViewActivityLogin;
    }
    @Provides
    public FingerPrintHelper getFingerHelper(){
        return new FingerPrintHelper(context,iViewActivityLogin);
    }

}

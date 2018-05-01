package com.example.yiming.hotelmanagment.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.util.authentication.finger.DaggerFingerPrintComponent;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerPrintComponent;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerPrintHelperModule;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerPrintHelper;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements IViewActivityLogin{
    private TextView fingerPrintInstructions;

    @Inject
    FingerPrintHelper fingerPrintHelper;

    FingerPrintComponent fingerPrintComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fingerPrintComponent= DaggerFingerPrintComponent.builder()
                .fingerPrintHelperModule(new FingerPrintHelperModule(this,this))
                .build();
        fingerPrintComponent.inject(this);
//        fingerPrintHelper = new FingerPrintHelper(this, this);
        fingerPrintHelper.onFingerPrintFunc();
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Authorization Failed", Toast.LENGTH_LONG).show();
    }
}

package com.example.yiming.hotelmanagment.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.R;

public class LoginActivity extends AppCompatActivity implements IViewActivityLogin{
    private TextView fingerPrintInstructions;
    private IPresenterActivityLogin iPresenterActivityLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iPresenterActivityLogin = new PresenterActivityLogin(LoginActivity.this);
        fingerPrintFun();
    }

    @Override
    public void fingerPrintFun() {
        iPresenterActivityLogin.onFingerPrintFunc();
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

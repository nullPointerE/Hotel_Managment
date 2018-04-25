package com.example.yiming.hotelmanagment.view;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.util.adapter.ViewPagerAdapter;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerHelper;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerprintException;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerprintHandler;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {
    TextView message;
    ViewPager viewPager;
    Toolbar toolbar;

    FingerprintManager.CryptoObject cryptoObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewPager);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPagerAdapter myAdapter=new ViewPagerAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(myAdapter);


//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  //透明导航栏

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.add(1, 1, 1, "menu_1");
        return super.onCreateOptionsMenu(menu);
    }



}

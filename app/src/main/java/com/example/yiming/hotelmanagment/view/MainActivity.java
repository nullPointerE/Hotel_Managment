package com.example.yiming.hotelmanagment.view;

import android.content.Intent;
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
import com.example.yiming.hotelmanagment.view.fragment.AddRoomDialog;


import javax.crypto.Cipher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);   // disable tool bar title

        ViewPagerAdapter myAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(myAdapter);


//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //set titlebar opic
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  //set navigationbar opic

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.customer:
                startActivity(new Intent(this, GuestInformationActivity.class));
                break;
            case R.id.manager:
                startActivity(new Intent(this, ManagerScreenActivity.class));
                break;
            case R.id.addRoom:
                AddRoomDialog addRoomDialog=new AddRoomDialog();
                addRoomDialog.show(getFragmentManager(),"addRoom");
        }
        return super.onOptionsItemSelected(item);
    }
}

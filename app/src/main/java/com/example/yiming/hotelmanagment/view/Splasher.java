package com.example.yiming.hotelmanagment.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.yiming.hotelmanagment.R;
import com.example.yiming.hotelmanagment.common.Customer;
import com.example.yiming.hotelmanagment.common.Food;
import com.example.yiming.hotelmanagment.common.Room;
import com.example.yiming.hotelmanagment.data.TasksDataSource;
import com.example.yiming.hotelmanagment.data.local.TasksLocalDataSource;

public class Splasher extends AppCompatActivity {
    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splasher);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splasher.this, LoginActivity.class));
                finish();
            }
        },500);
        video =findViewById(R.id.video);
        String uri = "android.resource://" + getPackageName() + "/" +R.raw.top10;
        video.setVideoURI(Uri.parse(uri));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        video.setLayoutParams(layoutParams);
        video.start();
    }
}

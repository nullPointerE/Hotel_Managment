package com.example.yiming.hotelmanagment.view;

import android.hardware.fingerprint.FingerprintManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    FingerHelper fingerHelper;
    Toolbar toolbar;

    FingerprintManager.CryptoObject cryptoObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewPager);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ViewPagerAdapter myAdapter=new ViewPagerAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(myAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.add(1, 1, 1, "menu_1");
        return super.onCreateOptionsMenu(menu);
    }

    public void authon(){
        fingerHelper=new FingerHelper(this);
        final FingerprintHandler fph = new FingerprintHandler(message);

        if (fingerHelper.checkFinger(message)) {
            // We are ready to set up the cipher and the key
            try {
                fingerHelper.generateKey();
                Cipher cipher = fingerHelper.generateCipher();
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
                message.setText("Swipe your finger");
                fph.doAuth((FingerprintManager) getSystemService(MainActivity.FINGERPRINT_SERVICE), cryptoObject);
            }
            catch(FingerprintException fpe) {
                // Handle exception
            }
        }
    }


}

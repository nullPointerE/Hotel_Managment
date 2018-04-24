package com.example.yiming.hotelmanagment.util.authentication.finger;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.TextView;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private TextView tv;
    public FingerprintHandler(TextView tv) {
        this.tv = tv;
    }

//    KeyGenerator产生密钥
//    KeyStore存放获取密钥
//    Cipher,是一个按照一定的加密规则，将数据进行加密后的一个对象

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        tv.setText("Auth error");
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        tv.setText("auth ok");
        tv.setTextColor(tv.getContext().getResources().
                getColor(android.R.color.holo_green_light));
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }

    public void doAuth(FingerprintManager manager,
                              FingerprintManager.CryptoObject obj) {
        CancellationSignal signal = new CancellationSignal();

        try {
            manager.authenticate(obj, signal, 0, this, null);
        } catch (SecurityException sce) {
        }
    }
}


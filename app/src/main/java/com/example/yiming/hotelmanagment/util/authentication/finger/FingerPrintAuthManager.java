package com.example.yiming.hotelmanagment.util.authentication.finger;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;

public class FingerPrintAuthManager extends FingerprintManager.AuthenticationCallback implements IFingerPrintAuthManager {


    private Context context;
    OnDataListener onDataListener;
    public FingerPrintAuthManager(Context context, OnDataListener onDataListener) {
        this.context = context;
        this.onDataListener = onDataListener;
    }

    public void startAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cenCancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            return;
        fingerprintManager.authenticate(cryptoObject, cenCancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        onDataListener.onFailure();

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        onDataListener.onFailure();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        onDataListener.onSuccess();
        //context.startActivity(new Intent(context, HomeActivity.class));

    }
}
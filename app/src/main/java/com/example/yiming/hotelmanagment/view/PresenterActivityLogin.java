package com.example.yiming.hotelmanagment.view;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerPrintAuthManager;
import com.example.yiming.hotelmanagment.util.authentication.finger.FingerPrintHelper;
import com.example.yiming.hotelmanagment.util.authentication.finger.IFingerPrintAuthManager;
import com.example.yiming.hotelmanagment.util.authentication.finger.IFingerPrintHelper;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

public class PresenterActivityLogin implements IPresenterActivityLogin, IFingerPrintHelper.IFingerPrintDataListener {
    private Context context;
    private IViewActivityLogin iViewActivityLogin;
    FingerPrintHelper fingerPrintHelper;
    public PresenterActivityLogin(LoginActivity loginActivity) {
        this.context = loginActivity;
        iViewActivityLogin = loginActivity;
        fingerPrintHelper = new FingerPrintHelper(context, this);
    }

    @Override
    public void onFingerPrintAuthSuccess() {
        iViewActivityLogin.onSuccess();
    }
    @Override
    public void onFingerPrintAuthFailure() {
        iViewActivityLogin.onFailure();
    }


    @Override
    public void onFingerPrintFunc() {
        fingerPrintHelper.onFingerPrintFunc();

    }
}

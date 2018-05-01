package com.example.yiming.hotelmanagment.util.authentication.finger;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.view.IViewActivityLogin;

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

public class FingerPrintHelper extends FingerprintManager.AuthenticationCallback{
    private Context context;
    private KeyStore keyStore;
    private Cipher cipher;
    private IViewActivityLogin iViewActivityLogin;
    public FingerPrintHelper(Context context, IViewActivityLogin iViewActivityLogin){
        this.context = context;
        this.iViewActivityLogin = iViewActivityLogin;
    }

    public void onFingerPrintFunc() {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (!fingerprintManager.isHardwareDetected())
            Toast.makeText(context, "Fingerprint authentication permission not enable", Toast.LENGTH_SHORT).show();
        else {
            if (!fingerprintManager.hasEnrolledFingerprints())
                Toast.makeText(context, "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show();
            else {
                if (!keyguardManager.isKeyguardSecure())
                    Toast.makeText(context, "Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();
                else
                    genKey();

                if (cipherInit()) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    CancellationSignal cenCancellationSignal = new CancellationSignal();
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
                        return;
                    fingerprintManager.authenticate(cryptoObject, cenCancellationSignal, 0, this, null);


                }
            }
        }
    }

    private boolean cipherInit() {

        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey)keyStore.getKey(Constants.KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return true;
        } catch (IOException e1) {

            e1.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e1) {

            e1.printStackTrace();
            return false;
        } catch (CertificateException e1) {

            e1.printStackTrace();
            return false;
        } catch (UnrecoverableKeyException e1) {

            e1.printStackTrace();
            return false;
        } catch (KeyStoreException e1) {

            e1.printStackTrace();
            return false;
        } catch (InvalidKeyException e1) {

            e1.printStackTrace();
            return false;
        }

    }

    private void genKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(Constants.KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            );
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        iViewActivityLogin.onFailure();

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        iViewActivityLogin.onFailure();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        iViewActivityLogin.onSuccess();

    }

}

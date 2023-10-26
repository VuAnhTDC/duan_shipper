package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.duangiaohang.Models.Shipper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class VerifyPhoneNumberActivity extends AppCompatActivity {
    private TextView tvPhoneNumber;
    private Button btnVeryPhoneNumber;
    private TextView tvSendOTPAgain;
    private FirebaseAuth mAuth;
    private Shipper infoshipper;

    private static final String TAG = VerifyPhoneNumberActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verify_phone_number);
        setControl();
        Intent intent  = getIntent();
     //   infoshipper = intent.getSerializableExtra("InfoShipperRegeter");

        mAuth = FirebaseAuth.getInstance();

        btnVeryPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhoneNumber = tvPhoneNumber.getText().toString().trim();
                onClickVerifyNumberPhone(strPhoneNumber);
            }
        });
        tvSendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void setControl() {
        tvPhoneNumber = findViewById(R.id.tvVerifyPHoneNumber);
        btnVeryPhoneNumber = findViewById(R.id.btnVerifyPhoneNumber);
        tvSendOTPAgain = findViewById(R.id.tvAgainOTP);
    }

    private void onClickVerifyNumberPhone(String strPhoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyPhoneNumberActivity.this , "verification Failed ", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String vericationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(vericationId, forceResendingToken);
                                gotoEnterPhoneNumberActivity(strPhoneNumber, vericationId);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            gotoMainShipperActivity(user.getPhoneNumber());
                            // Update UI
                    }

                 else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void gotoMainShipperActivity(String phoneNumber) {
        Intent intent  = new Intent(this,MainActivity.class);
        intent.putExtra("Phone Number",phoneNumber);
        startActivity(intent);
    }
    private void gotoEnterPhoneNumberActivity(String strPhoneNumber, String vericationId) {
        Intent intent  = new Intent(this,SendOTPNumberPhone.class);
        intent.putExtra("Verication Id",strPhoneNumber);
        startActivity(intent);
    }
}


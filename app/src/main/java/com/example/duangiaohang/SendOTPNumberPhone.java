package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.concurrent.TimeUnit;

public class SendOTPNumberPhone extends AppCompatActivity {
    private static final String TAG = SendOTPNumberPhone.class.getName();
    private EditText edtSendPhoneNumber;
    private Button btnSendVerifyPhoneNumber;
    private String mPhoneNumber;
    private String mVericationId;
    private FirebaseAuth mAuth;
    private  PhoneAuthProvider.ForceResendingToken mForceResendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verify_phone_number);
        setControl();
        mAuth = FirebaseAuth.getInstance();
        btnSendVerifyPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOTP = edtSendPhoneNumber.getText().toString().trim();
                onClickSendOTPCode(strOTP);
            }
        });
    }

    private void getdSendDataIntent() {
        mPhoneNumber = getIntent().getStringExtra("Phone Number");
        mVericationId = getIntent().getStringExtra("Verication Id");

    }


    private void setControl() {
        edtSendPhoneNumber = findViewById(R.id.edtSendOTP);
        btnSendVerifyPhoneNumber = findViewById(R.id.btnSendVerifyPhoneNumber);

    }

    private void onClickSendOTPCode(String strOTP) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVericationId, strOTP);
        signInWithPhoneAuthCredential(credential);

    }
    private  void onClickSendOTPAgain (){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setForceResendingToken(mForceResendingToken)// (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(SendOTPNumberPhone.this , "verification Failed ", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String vericationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(vericationId, forceResendingToken);
                               mVericationId  = vericationId;
                               mForceResendingToken = forceResendingToken;

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }





    private void signInWithPhoneAuthCredential (PhoneAuthCredential credential){
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
                        } else {
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Phone Number", phoneNumber);
        startActivity(intent);
    }

}


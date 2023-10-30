package com.example.duangiaohang;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duangiaohang.Models.Shipper;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    Button btnXacMinh;
    TextView tvGuiLaiMaXacMinh;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Shipper inforshipper = new Shipper();

    String UriStrImg1, UriStrImg2;
    EditText edtCho1, edtCho2, edtCho3, edtCho4, edtCho5, edtCho6;


    String verificationCode = "", codesms = "";
    String PhoneNumber;

    Uri uriFront, uriBack;

    PhoneAuthProvider.ForceResendingToken resendingToken;

    Context context;

  //  private static final String TAG = "VerifyPhoneNumberActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verify_phone_number);
        setControl();
        // Nhận dữ liệu từ RegistrationActivity
        Intent intent = getIntent();
        inforshipper = (Shipper) intent.getSerializableExtra("inforShipper");
        UriStrImg1 = intent.getParcelableExtra("urifront");
        UriStrImg2 = intent.getParcelableExtra("uriback");
        SendOTPSMS(PhoneNumber);

    }

    private void setControl() {
        btnXacMinh = findViewById(R.id.btnXacMinh);
        tvGuiLaiMaXacMinh = findViewById(R.id.tvNhapLaiMaXacMinh);
        edtCho1 = findViewById(R.id.edtChotrong1);
        edtCho2 = findViewById(R.id.edtChotrong2);
        edtCho3 = findViewById(R.id.edtChotrong3);
        edtCho4 = findViewById(R.id.edtChotrong4);
        edtCho5 = findViewById(R.id.edtChotrong5);
        edtCho6 = findViewById(R.id.edtChotrong6);

        btnXacMinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCho1.getText().toString().trim().isEmpty() ||
                        edtCho2.getText().toString().trim().isEmpty() ||
                        edtCho3.getText().toString().trim().isEmpty() ||
                        edtCho4.getText().toString().trim().isEmpty() ||
                        edtCho5.getText().toString().trim().isEmpty() ||
                        edtCho6.getText().toString().trim().isEmpty()) {
                    String Code = edtCho1.getText().toString() +
                            edtCho2.getText().toString() +
                            edtCho3.getText().toString() +
                            edtCho4.getText().toString() +
                            edtCho5.getText().toString() +
                            edtCho6.getText().toString();

                    String mVericationId = null;
                    if (mVericationId != null) {
                        btnXacMinh.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVericationId, Code);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                            btnXacMinh.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });
    }

    void SendOTPSMS(String PhoneNumber) {
        System.out.println("lay ma otp");
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
          //    .setPhoneNumber("+84" + inforshipper.getSdtS().substring(1))
                 .setPhoneNumber("+84346008801")
                .setTimeout(60L, SECONDS)
                .setActivity(VerifyPhoneNumberActivity.this)

                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        verificationCode = phoneAuthCredential.getSmsCode();
                        System.out.println("otp: " + phoneAuthCredential.getSmsCode());
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(VerifyPhoneNumberActivity.this);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Xảy ra lỗi trong quá trình gửi mã OTP");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }



                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        System.out.println("s: " + s);
                        codesms = s;
                    }

                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.e(TAG, "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
//                            gotoMainShipperActivity(user.getPhoneNumber());
//                            // Update UI
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
//
//    private void gotoMainShipperActivity(String phoneNumber) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("Phone Number", phoneNumber);
//        startActivity(intent);
//    }
//
//    private void gotoEnterPhoneNumberActivity(String strPhoneNumber, String vericationId) {
//        Intent intent = new Intent(this, VerifyPhoneNumberActivity.class);
//        intent.putExtra("Verication Id", strPhoneNumber);
//        startActivity(intent);
//    }
//}
}





package com.example.duangiaohang;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity {


    private static final int REQUEST_SMS_RECIVE = 2;
    private static final int REQUEST_SMS_SEND = 1;
    Button btnXacMinh;
    TextView tvGuiLaiMaXacMinh;

    private Shipper inforshipper = new Shipper();

    String UriStrImg1, UriStrImg2;
    EditText edtCho1, edtCho2, edtCho3, edtCho4, edtCho5, edtCho6;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String verificationCode = "", codesms = "";
    String PhoneNumber;

    Uri uriFront, uriBack;

    PhoneAuthProvider.ForceResendingToken resendingToken;
    //PhoneAuthCredential phoneAuthCredential = null;
    private String EnterCode = "";
    Context context;

    //  private static final String TAG = "VerifyPhoneNumberActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verify_phone_number);
        setControl();
        setevent();
        // Nhận dữ liệu từ RegistrationActivity
        Intent intent = getIntent();
        inforshipper = (Shipper) intent.getSerializableExtra("inforShipper");
        UriStrImg1 = intent.getParcelableExtra("urifront");
        UriStrImg2 = intent.getParcelableExtra("uriback");


        if (ContextCompat.checkSelfPermission(VerifyPhoneNumberActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VerifyPhoneNumberActivity.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_SEND);

        }
//        if (ContextCompat.checkSelfPermission(VerifyPhoneNumberActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(VerifyPhoneNumberActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_RECIVE);
//
//        }
        else {
            System.out.println("khong gui duoc ma ");
            SendOTPSMS();
            EnterCode = edtCho1.getText().toString() +
                    edtCho2.getText().toString() +
                    edtCho3.getText().toString() +
                    edtCho4.getText().toString() +
                    edtCho5.getText().toString() +
                    edtCho6.getText().toString();

            System.out.println(EnterCode);

        }


    }


    private void setevent() {
        btnXacMinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential phoneAuthCredential = null;
                if (edtCho1.getText().toString().trim().isEmpty() ||
                        edtCho2.getText().toString().trim().isEmpty() ||
                        edtCho3.getText().toString().trim().isEmpty() ||
                        edtCho4.getText().toString().trim().isEmpty() ||
                        edtCho5.getText().toString().trim().isEmpty() ||
                        edtCho6.getText().toString().trim().isEmpty()) {

                } else {
                    System.out.println("Nhap otp: " + EnterCode);
                    System.out.println("send otp: " + verificationCode);
                    EnterCode =
                            edtCho1.getText().toString() +
                            edtCho2.getText().toString() +
                            edtCho3.getText().toString() +
                            edtCho4.getText().toString() +
                            edtCho5.getText().toString() +
                            edtCho6.getText().toString();

                    System.out.println(EnterCode);

                }
                try {
                    phoneAuthCredential = PhoneAuthProvider.getCredential(codesms, EnterCode);

                } catch (Exception e) {
                    System.out.println("Loi xác minh otp: " + e.getMessage());
                }
                if (phoneAuthCredential != null) {
                    dangkyThanhCong(phoneAuthCredential);
                }
            }

        });
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


    }

    void SendOTPSMS() {
        System.out.println("lay ma otp");
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + inforshipper.getSdtS().substring(1))
                //   .setPhoneNumber("+84346008801")
                .setTimeout(60L, TimeUnit.SECONDS)
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
                        builder.setMessage("Đã xảy ra lỗi trong quá trình gửi mã OTP: " + e.getMessage());
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

    /////////////////////ĐĂNG KÝ THÀNH CÔNG///////////////////////////

    void dangkyThanhCong(PhoneAuthCredential phoneAuthCredential){
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {if (task.isSuccessful()) {
                        Toast.makeText(context, "Xác minh tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        //////////////////////// CHUYỂN SANG MÀN HÌNH LOGIN ////////////////////////
                        Intent intent = new Intent(context, DangNhapShipperActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(VerifyPhoneNumberActivity.this, "Sai mã OTP. Vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
    }

    //
    private void gotoMainShipperActivity(String phoneNumber) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Phone Number", phoneNumber);
        startActivity(intent);
    }

//    private void gotoEnterPhoneNumberActivity(String strPhoneNumber, String vericationId) {
//        Intent intent = new Intent(this, VerifyPhoneNumberActivity.class);
//        intent.putExtra("Verication Id", strPhoneNumber);
//        startActivity(intent);
//    }
//}
}





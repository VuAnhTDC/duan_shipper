package com.example.duangiaohang;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.duangiaohang.Class.ShowMessage;
import com.example.duangiaohang.Models.ShipperData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity {


    private static final int REQUEST_SMS_RECIVE = 2;
    private static final int REQUEST_SMS_SEND = 1;
    Button btnXacMinh;
    TextView tvGuiLaiMaXacMinh;


    Uri UriStrImage1T = null;
    Uri UriStrImage2S = null;
    EditText edtCho1, edtCho2, edtCho3, edtCho4, edtCho5, edtCho6;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String verificationCode = "", codesms = "";
    String PhoneNumber;

    Uri uriFront, uriBack;

    //PhoneAuthCredential phoneAuthCredential = null;
    private String EnterCode = "";
    Context context;
    private ShipperData shipperData = new ShipperData();


    //  private static final String TAG = "VerifyPhoneNumberActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verify_phone_number);
        context = this;
        setControl();
        setevent();
        // Nhận dữ liệu từ RegistrationActivity
        Intent intent = getIntent();
        shipperData = (ShipperData) intent.getSerializableExtra("shipperData");
        UriStrImage1T = intent.getParcelableExtra("urifront");
        UriStrImage2S = intent.getParcelableExtra("uriback");

        SendOTPSMS();



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

    void registration() {
        shipperData.setUrlImgShopAvatar("https://firebasestorage.googleapis.com/v0/b/duandd2.appspot.com/o/imageUserKhachHang%2Favatar1.jpg?alt=media&token=922b175b-f203-44e7-985e-f0b057465e37");
        shipperData.setPassWordShipper("");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference shopReference = firebaseDatabase.getReference("Shipper");
        // Upload dữ liệu text
        shopReference.child(shipperData.getSdtShipper()).setValue(shipperData);
        Intent intent = new Intent(context, DangNhapShipperActivity.class);
        startActivity(intent);
        finish();
    }

    void UpLoadCCCDMatTruoc() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String[] part = UriStrImage1T.getLastPathSegment().split("/");
        StorageReference imgRef = storageRef.child("Imageshipper/" + shipperData.getSdtShipper() + "/" + (part[part.length - 1]));
        UploadTask uploadTask = imgRef.putFile(UriStrImage1T);
        uploadTask.addOnCompleteListener(taskSnapshot -> {
            imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                shipperData.setUrlmatTruocCCCD(uri.toString());
                UpLoadCCCDMatSau();

            }).addOnFailureListener(e -> {
                Toast.makeText(context, "Lỗi khi tải ảnh lên" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }

    void UpLoadCCCDMatSau() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String[] part = UriStrImage2S.getLastPathSegment().split("/");
        StorageReference imgRef = storageRef.child("ImageShipper/" + shipperData.getSdtShipper() + "/" + (part[part.length - 1]));
        UploadTask uploadTask = imgRef.putFile(UriStrImage2S);
        uploadTask.addOnCompleteListener(taskSnapshot -> {
            imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                shipperData.setUrlmatSauCCCD(uri.toString());
                registration();
            }).addOnFailureListener(e -> {


                ShowMessage.showMessage("Lỗi khi tải ảnh lên: " + e,VerifyPhoneNumberActivity.this);
            });
        });
    }


    void SendOTPSMS() {
        System.out.println("lay ma otp");
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//                .setPhoneNumber("+84" + inforshipper.getSdtShipper().substring(1))
//                //   .setPhoneNumber("+84346008801")
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(VerifyPhoneNumberActivity.this)
//
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                        verificationCode = phoneAuthCredential.getSmsCode();
//                        System.out.println("otp: " + phoneAuthCredential.getSmsCode());
//
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        builder.setTitle("Thông báo");
//                        builder.setMessage("Đã xảy ra lỗi trong quá trình gửi mã OTP: " + e.getMessage());
//
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
////                        System.out.println("loi "+e.getMessage());
//                    }
//
//
//                    @Override
//                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        super.onCodeSent(s, forceResendingToken);
//                        System.out.println("s: " + s);
//                        codesms = s;
//                    }
//
//                })
//                .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + shipperData.getSdtShipper().substring(1))

                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        verificationCode = phoneAuthCredential.getSmsCode();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        System.out.println("loi: " + e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Xảy ra lỗi trong quá trình gửi OTP!");
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
                        codesms = s;
                        System.out.println("s" + s);
                        Log.d("ma cua ban ", s);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    /////////////////////ĐĂNG KÝ THÀNH CÔNG///////////////////////////

    void dangkyThanhCong(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Xác minh tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            UpLoadCCCDMatTruoc();
                            //////////////////////// CHUYỂN SANG MÀN HÌNH LOGIN ////////////////////////

                        } else {
                            Toast.makeText(VerifyPhoneNumberActivity.this, "Sai mã OTP. Vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}





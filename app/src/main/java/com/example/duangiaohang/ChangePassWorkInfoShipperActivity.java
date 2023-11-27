package com.example.duangiaohang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duangiaohang.Class.RegexValiDate;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ChangePassWorkInfoShipperActivity extends AppCompatActivity {
    EditText edtSoDienThoai, edtInputOTP;
    Button btnLayMaOTP, btnTiepTuc;
    TextView tvTimeResend;
    Context context;
    String verificationCode = "", codesms = "";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Long timeoutSeconds = 60L;

    View vForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass_word_shipper_layout);
        context = this;
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtInputOTP.getText().toString().isEmpty() || !RegexValiDate.validOTP(edtInputOTP.getText().toString())) {
                    Log.i("TAG", "LOH" + edtInputOTP.toString());
                    System.out.println("hel" + edtInputOTP.toString());

                    Toast.makeText(ChangePassWorkInfoShipperActivity.this, "OTP không được bỏ trống HOẶC OTP sai định dạng. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                } else {
                    String enteredOTP = edtInputOTP.getText().toString();
                    PhoneAuthCredential phoneAuthCredential = null;
                    try {
                        phoneAuthCredential = PhoneAuthProvider.getCredential(codesms, enteredOTP);
                    } catch (Exception e) {
                        Toast.makeText(context, "Lỗi xác minh OTP" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (phoneAuthCredential != null) {
                        verifyForgotPWSuccessul(phoneAuthCredential);

                    }
                }
            }
        });


        btnLayMaOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentOTP();
                resendOTP();
            }
        });
        edtSoDienThoai.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (!RegexValiDate.validPhone(edtSoDienThoai.getText().toString())) {
                        edtSoDienThoai.selectAll();
                        edtSoDienThoai.requestFocus();
                        edtSoDienThoai.setError("Số điện thoại phải đủ 10 ký tự số và bắt đầu bằng số 0");
                    } else {
                        edtInputOTP.requestFocus();
                    }
                }
                return false;
            }
        });
        edtInputOTP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (!RegexValiDate.validOTP(edtInputOTP.getText().toString())) {
                        edtInputOTP.selectAll();
                        edtInputOTP.requestFocus();
                        edtInputOTP.setError("OTP là dãy ký tự 6 số");
                        System.out.println("edt" + edtInputOTP.toString());
                        Log.d("hello", edtInputOTP.toString());
                    } else {
                        hideKeyboard();
                    }
                }
                return false;
            }
        });
        vForgotPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    void verifyForgotPWSuccessul(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Xác minh OTP thành công!", Toast.LENGTH_SHORT).show();

                                databaseReference = firebaseDatabase.getReference("Shipper");

                                String phoneNumber = edtSoDienThoai.getText().toString();

                                Query query = databaseReference.orderByChild("sdtShipper").equalTo(phoneNumber);


                           //     Query query = databaseReference.child(edtSoDienThoai.getText().toString()).getParent();
                                System.out.println(edtSoDienThoai.toString());

                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            for (DataSnapshot item : snapshot.getChildren()) {
                                                Log.d("YourTag", "dataSnapshot value: " + snapshot.getValue());
                                                Log.d("YourTag", "dataSnapshot value: " + item);

                                                // Kiểm tra giá trị của idShipper
                                                Object idShipperValue = item.child("idShipper").getValue();
                                                Log.d("YourTag", "idShipperValue: " + idShipperValue);

                                                if (idShipperValue != null && idShipperValue.equals(edtSoDienThoai.getText().toString())) {
                                                    ShipperData shipperData = item.getValue(ShipperData.class);
                                                    Log.d("YourTag", "Found a match for idShipper: " + idShipperValue);

                                                    // Tiếp tục xử lý khi tìm thấy giá trị khớp
                                                    Intent intent1 = new Intent(ChangePassWorkInfoShipperActivity.this, NewPasswordShipperForgotActivity.class);
                                                    intent1.putExtra("idShipper", shipperData);
                                                    startActivity(intent1);

                                                }
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {
                                Toast.makeText(ChangePassWorkInfoShipperActivity.this, "Sai mã OTP. Vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            ShowMessage.showMessage("Lỗi: " + e, ChangePassWorkInfoShipperActivity.this);
                        }
                    }
                });
    }

    void sentOTP() {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + edtSoDienThoai.getText().toString().substring(1))
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        verificationCode = phoneAuthCredential.getSmsCode();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        System.out.println("Lỗi: " + e.getMessage());
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
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //Đếm ngược thời gian gửi lại mã
    void resendOTP() {
        btnLayMaOTP.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                tvTimeResend.setText(timeoutSeconds + " giây");
                if (timeoutSeconds <= 0) {
                    timeoutSeconds = 60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        btnLayMaOTP.setEnabled(true);
                    });
                }
            }
        }, 0, 1000);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setControl() {
        vForgotPassword = findViewById(R.id.vForgotPassword);
        tvTimeResend = findViewById(R.id.tvTimeResendOTP);
        edtInputOTP = findViewById(R.id.edtOtpInput);
        btnLayMaOTP = findViewById(R.id.btnGetOTP);
        edtSoDienThoai = findViewById(R.id.edtPhoneNumberChanger);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);

    }
}
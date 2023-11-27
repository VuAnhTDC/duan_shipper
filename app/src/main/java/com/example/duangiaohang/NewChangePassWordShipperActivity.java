package com.example.duangiaohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duangiaohang.Class.RegexValiDate;
import com.example.duangiaohang.Models.ShipperData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewChangePassWordShipperActivity extends AppCompatActivity {

     // CHUYỂN VỀINFO
    ShipperData shipperData = new ShipperData();
    TextInputEditText edtMatKhauMoi, edtNhapLaiMatKhauMoi;
    Button btnHoanThanh;
    View vNewPasswordForgot;
    public static final String SHARED_PREF_NAME = "MyPrefs";
    public static final String KEY_PASSWORD = "passwordShipper";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password_change_shipper_forgot_layout);
        context = this;
        setControl();
        setEvent();
        Intent intent = getIntent();
        shipperData = (ShipperData) intent.getSerializableExtra("idShipper");
        System.out.println("Shipper1111111:" + shipperData);
        /// Rtruyền Sang Inffo
    }
    private void setEvent() {
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
        edtMatKhauMoi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    if(!RegexValiDate.validShipperPassword(edtMatKhauMoi.getText().toString())) {
                        edtMatKhauMoi.selectAll();
                        edtMatKhauMoi.requestFocus();
                        edtMatKhauMoi.setError("Mật khẩu tối thiểu 8 ký tự, tối đa 40 ký tự. Bao gồm tối thiểu 1 chữ HOA, 1 chữ thường, 1 chữ số và 1 ký tự đặc biệt");
                    }  else {
                        edtNhapLaiMatKhauMoi.requestFocus();
                    }
                }
                return false;
            }
        });

        edtNhapLaiMatKhauMoi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    if(!RegexValiDate.validShipperPassword(edtMatKhauMoi.getText().toString()) || !edtMatKhauMoi.getText().toString().equals(edtNhapLaiMatKhauMoi.getText().toString())) {
                        edtNhapLaiMatKhauMoi.selectAll();
                        edtNhapLaiMatKhauMoi.requestFocus();
                        edtNhapLaiMatKhauMoi.setError("Mật khẩu và xác nhận mật khẩu không khớp!");
                    }else {
                        hideKeyboard();
                    }
                }
                return false;
            }
        });
        vNewPasswordForgot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    private void updatePassword() {
        String matKhauMoi = edtMatKhauMoi.getText().toString();
        shipperData.setPassWordShipper(matKhauMoi);

        if (checkNewPassword()) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference shipperReference = firebaseDatabase.getReference("Shipper");

            // Thực hiện cập nhật mật khẩu lên Firebase
            shipperReference.child(shipperData.getIdShipper()).child("passWordShipper").setValue(matKhauMoi);

            // Thông báo cho người dùng về việc cập nhật mật khẩu thành công
            Toast.makeText(this, "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            savePasswordToSharedPreferences(matKhauMoi);
            // Chuyển về màn hình thông tin tài khoản hoặc màn hình khác nếu cần
            Intent intent = new Intent(NewChangePassWordShipperActivity.this, AccountInformationActivity.class);
            startActivity(intent);

            // Lưu mật khẩu vào SharedPreferences

        }
    }

    private void savePasswordToSharedPreferences(String password) {
        // Lưu mật khẩu vào SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }



    private boolean checkNewPassword() {
        String matKhauMoi = edtMatKhauMoi.getText().toString();
        String nhapLaiMatKhau = edtNhapLaiMatKhauMoi.getText().toString();

        if (matKhauMoi.isEmpty() || nhapLaiMatKhau.isEmpty() || !RegexValiDate.validShipperPassword(matKhauMoi) || !RegexValiDate.validShipperPassword(nhapLaiMatKhau)) {
            // Hiển thị thông báo nếu một trong hai trường mật khẩu trống
            Toast.makeText(this, "Mật khẩu không được để trống HOẶC sai yêu cầu độ mạnh!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!matKhauMoi.equals(nhapLaiMatKhau) || !RegexValiDate.validShipperPassword(matKhauMoi) || !RegexValiDate.validShipperPassword(nhapLaiMatKhau)) {
            // Hiển thị thông báo nếu mật khẩu và xác nhận mật khẩu không khớp
            Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu không khớp HOẶC sai yêu cầu độ mạnh!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }



    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void setControl() {
        vNewPasswordForgot = findViewById(R.id.vNewPasswordForgot);
        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi);
        edtNhapLaiMatKhauMoi = findViewById(R.id.edtNhapLaiMatKhauMoi);
        btnHoanThanh = findViewById(R.id.btnHoanThanh);
    }

}
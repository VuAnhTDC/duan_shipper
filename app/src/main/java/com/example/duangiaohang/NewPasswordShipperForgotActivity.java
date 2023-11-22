package com.example.duangiaohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duangiaohang.Models.ShipperData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPasswordShipperForgotActivity extends AppCompatActivity {
ShipperData shipperData = new ShipperData();
    TextInputEditText edtMatKhauMoi, edtNhapLaiMatKhauMoi;
    Button btnHoanThanh;
    View vNewPasswordForgot;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password_shipper_forgot_layout);
        setControl();
        setEvent();
        Intent intent = getIntent();
        shipperData = (ShipperData) intent.getSerializableExtra("idShipper");

        assert shipperData != null;
        System.out.println("Shipper1111111:" + shipperData);
        context = this;

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

    private void updatePassword(){
        String matKhauMoi = edtMatKhauMoi.getText().toString();
        shipperData.setPassWordShipper(matKhauMoi);

        System.out.println("abcd: " + shipperData.toString());

        if(checkNewPassword()){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference shipperReference = firebaseDatabase.getReference("Shipper");
            if(shipperReference != null){
                shipperReference.child(shipperData.getIdShipper()).setValue(shipperData);
                Toast.makeText(this, "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewPasswordShipperForgotActivity.this, DangNhapShipperActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Thay đổi mật khẩu thất bại!!!", Toast.LENGTH_SHORT).show();
            }
        }
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
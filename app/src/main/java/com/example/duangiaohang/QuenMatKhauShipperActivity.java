package com.example.duangiaohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuenMatKhauShipperActivity extends AppCompatActivity {
    TextView tvDangKiShipper, tvQuenMkShipper;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quen_mk_shipper);
        setControl();
        setEvent();


    }

    private void setEvent() {
    }

    private void setControl() {

    }
}
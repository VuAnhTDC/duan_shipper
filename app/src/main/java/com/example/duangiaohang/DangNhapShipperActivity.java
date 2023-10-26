package com.example.duangiaohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DangNhapShipperActivity extends AppCompatActivity {
    TextView tvDangKiShipper,tvDangnhapShipper ,tvQuenMkShipper;
    EditText edtEmailShipper,edtMatKhauShipper;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_nhap_shipper);
        setControl();
        setEvent();


    }

    private void setEvent() {
        tvDangKiShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapShipperActivity.this,DangKiShipperActivity.class);
             //   intent.putExtra(E,edtTitle.getText().toString());
             //   intent.putExtra(DESCRIPTION,edtDescription.getText().toString());
                startActivity(intent);

            }
        });
        tvQuenMkShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapShipperActivity.this,QuenMatKhauShipperActivity.class);
                //   intent.putExtra(E,edtTitle.getText().toString());
                //   intent.putExtra(DESCRIPTION,edtDescription.getText().toString());
                startActivity(intent);

            }
        });
    }

    private void setControl() {
    tvDangnhapShipper  = findViewById(R.id.tvDangNhapShipper);
    edtEmailShipper = findViewById(R.id.edtEmailNguoiShipper);
    edtMatKhauShipper  = findViewById(R.id.edtMatKhauShipper);
    tvDangKiShipper = findViewById(R.id.tvDangKiShipper);
    tvQuenMkShipper  = findViewById(R.id.tvQuenMkShipper);
    btnDangNhap  = findViewById(R.id.btnDangNhap);

    }
}
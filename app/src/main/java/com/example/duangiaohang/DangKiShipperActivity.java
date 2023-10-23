package com.example.duangiaohang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duangiaohang.Models.ImageUtils;
import com.example.duangiaohang.Models.Shipper;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DangKiShipperActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICKER = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;


    private Bitmap selectedImageBitmap;

    EditText edtHoTen, edtEmail, edtSdt, edtDiaChi, edtNguyenQuan;
    Spinner spKhuVucGiao;
    Button btnTiepTuc;
    private ImageView imgLoadMatTruoc;
    private ImageView imgLoadMatSau;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_ki_shipper);
        Intent intent = new Intent(context, VerifyPhoneNumberActivity.class);
        Shipper infoShipper = new Shipper(edtHoTen.getText().toString(), edtEmail.getText().toString(), edtSdt.getText().toString(), edtDiaChi.getText().toString(), edtNguyenQuan.getText().toString());
        setControl();
        // Chuyển sang màn hình thứ hai
        startActivity(intent);


        // Khởi tạo danh sách các lựa chọn
        List<String> options = new ArrayList<>();
        options.add("HCM");
        options.add("Hà Nội");
        options.add("Hải Phòng");


        // Khởi tạo Adapter và thiết lập cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKhuVucGiao.setAdapter(adapter);
        imgLoadMatTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICKER);
                ImageUtils.openImagePicker(this,);
            }

        });
        imgLoadMatSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICKER);
                ImageUtils.openImagePicker(this);
            }
        });
    }


            private void setControl() {
                imgLoadMatTruoc = findViewById(R.id.imgloadMatTruoc);
                imgLoadMatSau = findViewById(R.id.imgloadMatSau);
                TextView tvDiaChi = findViewById(R.id.tvDiaChi);
                Spinner spinner = findViewById(R.id.spDiaChi);

            }

        }















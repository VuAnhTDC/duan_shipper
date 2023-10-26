package com.example.duangiaohang;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duangiaohang.Models.Shipper;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DangKiShipperActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICKER = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

  

    private static final int REQUEST_PICK_IMAGE = 2;


    TextView tvDangKiGiaoHangShipper;

    EditText edtHoVaTen, edtEmailNguoiShipper, edtDiaChiThuongTruShipper, edtNguyenQuanShipper, edtSoDienThoaiShipper;
    Spinner spKhuVucGiaoShipper;
    Button btnTiepTuc;
    private ImageView imgLoadMatTruoc;
    private ImageView imgLoadMatSau;
    private static Uri uriImageSelectionOnDeviceCCCDFront = null;
    private static Uri uriImageSelectionOnDeviceCCCDBack = null;

    private Context context;
    private int requestCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_ki_shipper);
        setControl();
        setEvent();
//        Intent intent = new Intent(context, VerifyPhoneNumberActivity.class);

        //   Shipper infoShipper = new Shipper(edtidS.getText().toString(), edthoTenS.getText().toString(), edtEmailS.getText().toString(), edtsdtS.getText().toString(),
        //   edtdiaChiS.getText().toString(), edtNguyenQuanS.getText().toString(), spKhuVucGiao.toString(), strImg1, strImg2);

        //  intent.putExtra("infoshipper", infoShipper);
        // Chuyển sang màn hình thứ hai
        //   startActivity(intent);

        // Khởi tạo danh sách của spiner các lựa chọn
        List<String> options = new ArrayList<>();
        options.add("HCM");
        options.add("Hà Nội");
        options.add("Hải Phòng");


        // Khởi tạo Adapter và thiết lập cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKhuVucGiaoShipper.setAdapter(adapter);


    }

    private void setEvent() {
        edtEmailNguoiShipper.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (RegexValiDate.validPhone(edtEmailNguoiShipper.getText().toString()) == false) {
                        edtEmailNguoiShipper.selectAll();
                        edtEmailNguoiShipper.requestFocus();
                        edtEmailNguoiShipper.setError("Email phải có Dạng xxx.@gmail.com");
                    } else {
                        edtEmailNguoiShipper.requestFocus();
                    }
                }
                return false;
            }
        });
        edtHoVaTen.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (RegexValiDate.validFullname(edtHoVaTen.getText().toString()) == false) {
                        edtHoVaTen.selectAll();
                        edtHoVaTen.requestFocus();
                        edtHoVaTen.setError("Ho Ten Khong Chua Ki Tu Đặt Biet");
                    } else {
                        edtEmailNguoiShipper.requestFocus();
                    }

                }
                return false;
            }
        });
        edtSoDienThoaiShipper.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (RegexValiDate.validPhone(edtSoDienThoaiShipper.getText().toString()) == false) {
                        edtSoDienThoaiShipper.selectAll();
                        edtSoDienThoaiShipper.requestFocus();
                        edtSoDienThoaiShipper.setError("Số điện thoại phải đủ 10 ký tự số và bắt đầu bằng số 0");
                    } else {
                        edtNguyenQuanShipper.requestFocus();
                    }
                }
                return false;
            }
        });
        edtDiaChiThuongTruShipper.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (RegexValiDate.validShipperAddress(edtDiaChiThuongTruShipper.getText().toString()) == false) {
                        edtDiaChiThuongTruShipper.selectAll();
                        edtDiaChiThuongTruShipper.requestFocus();
                        edtDiaChiThuongTruShipper.setError("Số điện thoại phải đủ 10 ký tự số và bắt đầu bằng số 0");
                    } else {
                        edtDiaChiThuongTruShipper.requestFocus();
                    }
                }
                return false;
            }
        });
        edtNguyenQuanShipper.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (RegexValiDate.validShipperAddress(edtNguyenQuanShipper.getText().toString()) == false) {
                        edtNguyenQuanShipper.selectAll();
                        edtNguyenQuanShipper.requestFocus();
                        edtNguyenQuanShipper.setError("Số điện thoại phải đủ 10 ký tự số và bắt đầu bằng số 0");
                    } else {
                        edtNguyenQuanShipper.requestFocus();
                    }
                }
                return false;
            }
        });
        imgLoadMatTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(DangKiShipperActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DangKiShipperActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
                if (ContextCompat.checkSelfPermission(DangKiShipperActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DangKiShipperActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGE_PICKER);
                }else {
                    showImageSourceDialog();
                }
            }
        });
        imgLoadMatSau.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DangKiShipperActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DangKiShipperActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
                if (ContextCompat.checkSelfPermission(DangKiShipperActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DangKiShipperActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGE_PICKER);
                }

                else {

                    showImageSourceDialog();

                }
            }
        });

    }
    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pickPhoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pickPhoto, REQUEST_PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgLoadMatTruoc.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imgLoadMatSau.setImageURI(imageUri);
        }
    }

    private void setControl() {
        tvDangKiGiaoHangShipper = findViewById(R.id.tvDangKiGiaoHangShipper);
        imgLoadMatTruoc = findViewById(R.id.imgloadMatTruoc);
        imgLoadMatSau = findViewById(R.id.imgloadMatSau);
        edtHoVaTen = findViewById(R.id.edtHoVaTen);
        edtEmailNguoiShipper = findViewById(R.id.edtEmailNguoiShipper);
        edtDiaChiThuongTruShipper = findViewById(R.id.edtDiaChiThuongTruShipper);
        edtNguyenQuanShipper = findViewById(R.id.edtNguyenQuanShipper);
        edtSoDienThoaiShipper = findViewById(R.id.edtSoDienThoaiShipper);
        spKhuVucGiaoShipper = findViewById(R.id.spKhuVucGiaoShipper);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);


    }

}















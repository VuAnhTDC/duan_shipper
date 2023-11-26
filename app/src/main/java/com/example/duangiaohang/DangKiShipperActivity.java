package com.example.duangiaohang;


import static android.content.Intent.ACTION_PICK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duangiaohang.Class.RegexValiDate;
import com.example.duangiaohang.Models.ShipperData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;


public class DangKiShipperActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICKER = 1;

    private static final int PICK_IMAGE_FRONT = 1;
    private static final int PICK_IMAGE_BACK = 2;
    private static final int REQUEST_CODE =1 ;

    private String mPhoneNumber;
    private String mVericationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;




    TextView tvDangKiGiaoHangShipper;

    EditText edtHoVaTen, edtEmailNguoiShipper, edtDiaChiThuongTruShipper, edtNguyenQuanShipper, edtSoDienThoaiShipper;
    Spinner spKhuVucGiaoShipper;
    Button btnTiepTuc;
    private ImageView imgLoadMatTruoc;
    private ImageView imgLoadMatSau;


    private Context context;
    private int requestCode;
    Uri UriStrImage1T = null;
    Uri  UriStrImage2S = null;
    private String Code;

    public DangKiShipperActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_ki_shipper);
        context  = this;
        setControl();

        setEvent();



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
                    if (!RegexValiDate.validPhone(edtEmailNguoiShipper.getText().toString())) {
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
                    if (!RegexValiDate.validFullname(edtHoVaTen.getText().toString())) {
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
                    if (!RegexValiDate.validPhone(edtSoDienThoaiShipper.getText().toString())) {
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
                    if (!RegexValiDate.validShipperAddress(edtDiaChiThuongTruShipper.getText().toString())) {
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
                    if (!RegexValiDate.validShipperAddress(edtNguyenQuanShipper.getText().toString())) {
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
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View view) {

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
//                }
                if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGE_PICKER);
                } else {

                    openGallery(PICK_IMAGE_FRONT);
                }
            }
        });
        imgLoadMatSau.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View view) {
//                if (checkSelfPermission( Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions( new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
//                }
                if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions( new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_IMAGE_PICKER);
                } else {

                   // showImageSourceDialog();
                    openGallery(PICK_IMAGE_BACK);

                }
            }
        });
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Tạo một đối tượng Shipper và điền dữ liệu
               ShipperData shipperData = new ShipperData();
                shipperData.setSdtShipper(edtSoDienThoaiShipper.getText().toString()); // Sử dụng số điện thoại làm ID
                shipperData.setHoTenShipper(edtHoVaTen.getText().toString());
                shipperData.setEmailShipper(edtEmailNguoiShipper.getText().toString());
                shipperData.setDiaChiShipper(edtDiaChiThuongTruShipper.getText().toString());
                shipperData.setNguyenQuanShipper(edtNguyenQuanShipper.getText().toString());
                shipperData.setKhuVucGHShipper(spKhuVucGiaoShipper.getSelectedItem().toString());


//                if (!kiemtraShipper()) {
                    // Tiếp tục đến màn hình tiếp theo
                    Intent intent = new Intent(context, VerifyPhoneNumberActivity.class);
                    intent.putExtra("shipperData", shipperData);
                    intent.putExtra("urifront",UriStrImage1T);
                    intent.putExtra("uriback", UriStrImage2S);
                    startActivity(intent);
                    finish();
//                } else {
//                    Toast.makeText(DangKiShipperActivity.this, "Thông tin không hợp lệ, vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
                }

//            }

        });
    }
    // kiểm tra shipper nhập vào nêu dung return ve true sai true flase

    private boolean kiemtraShipper() {
        if (RegexValiDate.isValidEmail(edtEmailNguoiShipper.getText().toString()) &&
                RegexValiDate.validFullname(edtHoVaTen.getText().toString()) &&
                RegexValiDate.validPhone(edtSoDienThoaiShipper.getText().toString()) &&
                RegexValiDate.validShipperAddress(edtDiaChiThuongTruShipper.getText().toString()) &&
                RegexValiDate.validShipperAddress(edtNguyenQuanShipper.getText().toString())) {
            System.out.println(edtDiaChiThuongTruShipper);
            System.out.println(edtHoVaTen);
            System.out.println(edtSoDienThoaiShipper);
            System.out.println(edtEmailNguoiShipper);
            System.out.println(edtNguyenQuanShipper);
            return true;

        }
        return false;


    }

    //Hàm chọn ảnh từ thư viện

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_FRONT) {
                // Xử lý khi chọn ảnh cho mặt trước
                UriStrImage1T = data.getData();
                imgLoadMatTruoc.setImageURI(UriStrImage1T);
            } else if (requestCode == PICK_IMAGE_BACK) {
                // Xử lý khi chọn ảnh cho mặt sau
                UriStrImage2S = data.getData();
                imgLoadMatSau.setImageURI(UriStrImage2S);
            }
        }
    }
    //Sự kiện ẩn bàn phím
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//

    @SuppressLint("QueryPermissionsNeeded")
    private void openGallery(int requestCode) {
        Intent pickPhoto = new Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
     if (null != pickPhoto.resolveActivity(getPackageManager())) {
            startActivityForResult(pickPhoto, requestCode);
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













package com.example.duangiaohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.duangiaohang.Class.LoadingDialog;
import com.example.duangiaohang.Class.RegexValiDate;
import com.example.duangiaohang.Class.ShowMessage;
import com.example.duangiaohang.Models.ShipperData;
//import com.example.duangiaohang.RecyclerView.MHTrangChuShipperAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class DangNhapShipperActivity extends AppCompatActivity {
    TextView tvDangKiShipper, tvDangnhapShipper, tvQuenMkShipper;
    EditText edtEmailShipper, edtMatKhauShipper;

    Button btnDangNhap;
    Context context;
    private LoadingDialog loadingDialog;
    ProgressBar progressBar;
    TextView textView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_nhap_shipper);
        context = this;
        loadingDialog = new LoadingDialog(DangNhapShipperActivity.this);


        setControl();
        setEvent();


    }

    private void setEvent() {
        tvDangKiShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapShipperActivity.this, DangKiShipperActivity.class);
                //   intent.putExtra(E,edtTitle.getText().toString());
                //   intent.putExtra(DESCRIPTION,edtDescription.getText().toString());
                startActivity(intent);

            }
        });
        tvQuenMkShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapShipperActivity.this, ChangePassWorkInfoShipperActivity.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra thông tin đăng nhập có bị bỏ trống không
                if (edtEmailShipper.getText().toString().isEmpty() || edtMatKhauShipper.getText().toString().isEmpty()) {
                    ShowMessage.showMessage("Thông tin đăng nhập không được bỏ trống. Vui lòng kiểm tra lại!!!", DangNhapShipperActivity.this);
                    return;
                }

                if (RegexValiDate.isValidEmail(edtEmailShipper.getText().toString()) || RegexValiDate.isValidPassword(edtMatKhauShipper.getText().toString())) {
                    // isLoading = true;
                    //  if (isLoading) {
                    hideKeyboard();
                    //progressbar_Loading_ScreenLogin.setVisibility(View.VISIBLE);
                }
                loadingDialog.startLoadingDialog();
                // Kiểm tra xem user có tồn tại không
                databaseReference = firebaseDatabase.getReference("Shipper");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean found = false;
                        for (DataSnapshot shipperItem : snapshot.getChildren()) {
                            String EmailShipper = shipperItem.child("emailShipper").getValue(String.class);
                            if (EmailShipper.equals(edtEmailShipper.getText().toString())) {
                                found = true;
                                if (shipperItem.child("passWordShipper").getValue(String.class).equals(edtMatKhauShipper.getText().toString())) {
                                    if (Integer.parseInt(shipperItem.child("status").getValue().toString()) == 0) {
                                        loadingDialog.dismissDialog();
                                        ShowMessage.showMessage("Tài khoản đang chờ ADMIN duyệt đăng ký. Vui lòng chờ thông báo!", DangNhapShipperActivity.this);
                                        return;
                                    } else if (Integer.parseInt(shipperItem.child("status").getValue().toString()) == 2) {
                                        loadingDialog.dismissDialog();
                                        ShowMessage.showMessage("Tài khoản đã bị khóa.\nLiên hệz ADMIN để biết thêm chi tiết!!!", DangNhapShipperActivity.this);
                                        return;
                                    } else if (Integer.parseInt(shipperItem.child("status").getValue().toString()) == 1) {
                                        // Đăng nhập thành công
                                        ShipperData shipperData = shipperItem.getValue(ShipperData.class);
                                        // Lưu thông tin của người dùng vào SharedPreferences
                                        SharedPreferences sharedPreferences1 = getSharedPreferences("informationShop", Context.MODE_PRIVATE);
                                        Gson gson = new Gson();
                                         String json = gson.toJson(shipperData);
                                        SharedPreferences.Editor editor = sharedPreferences1.edit();


                                        editor.putString("informationShop", json);
                                        editor.apply();
                                        Intent intent = new Intent(context, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                }
                            }
                        }
                        if (!found) {
                            loadingDialog.dismissDialog();
                            ShowMessage.showMessage("Tài khoản không tồn tại!!!", DangNhapShipperActivity.this);
                        } else {
                            loadingDialog.dismissDialog();
                            ShowMessage.showMessage("Sai mật khẩu. Vui lòng thử lại!!!", DangNhapShipperActivity.this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loadingDialog.dismissDialog();
                        ShowMessage.showMessage("Lỗi không truy vấn được dữ liệu: " + error, DangNhapShipperActivity.this);
                    }
                });


            }
        });
    }

    private void setControl() {
        tvDangnhapShipper = findViewById(R.id.tvDangNhapShipper);
        edtEmailShipper = findViewById(R.id.edtEmailNguoiShipper);
        edtMatKhauShipper = findViewById(R.id.edtMatKhauShipper);
        tvDangKiShipper = findViewById(R.id.tvDangKiShipper);
        tvQuenMkShipper = findViewById(R.id.tvQuenMkShipper);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        progressBar = findViewById(R.id.loadingProgressBar);
        textView = findViewById(R.id.loadingTextView);

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}



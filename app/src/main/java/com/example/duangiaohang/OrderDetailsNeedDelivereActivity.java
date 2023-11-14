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

import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.Models.ShipperData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class OrderDetailsNeedDelivereActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<OrderData> orderDataArrayList;
    OrderData orderData = new OrderData();
    Button btnNhanDuocHangCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_need_delivere_layout);
        //Nhận dữ liệu thông tin đơn hàng
        Intent intent = getIntent();
        if (intent.hasExtra("orderData1")) {
            orderData = (OrderData) intent.getSerializableExtra("orderData1");
            System.out.println("Dữ liệu nhận được tại : OrderDetailsNeedDelivereActivity From HomeActivity " + orderData);
        }
        setControl();
        setEvent();
    }

    private void setControl() {
        btnNhanDuocHangCT = findViewById(R.id.btnNhanDonHangCT);
    }

    private void setEvent() {
        btnNhanDuocHangCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrderStatus(2);
                Intent intent = new Intent(OrderDetailsNeedDelivereActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateOrderStatus(int newStatus) {
        databaseReference = FirebaseDatabase.getInstance().getReference("OrderProduct/" + orderData.getIdShop_Order() + "/" + orderData.getIdOrder());
        //Cập nhật trạng thái đơn hàng
        databaseReference.child("statusOrder").setValue(newStatus);
    }


}


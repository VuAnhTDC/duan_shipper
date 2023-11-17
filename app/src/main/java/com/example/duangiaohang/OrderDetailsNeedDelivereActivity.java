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
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.duangiaohang.Models.Image;
import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.Models.ProductData;
import com.example.duangiaohang.Models.ShipperData;
import com.example.duangiaohang.RecyclerView.VPOrderDetailsNeedAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OrderDetailsNeedDelivereActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ArrayList<OrderData> orderDataArrayList;
    OrderData orderData = new OrderData();
    Button btnNhanDuocHangCT;
    VPOrderDetailsNeedAdapter vpOrderDetailsNeedAdapter;
    ArrayList<String> imgArrayListURL = new ArrayList<>();
    String idProduct = "";
    ViewPager viewPagerList_NeedDetail;
    ProductData productData = new ProductData();
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_need_delivere_layout);
        setControl();
        //Nhận dữ liệu thông tin đơn hàng
        Intent intent = getIntent();
        if (intent.hasExtra("orderData1")) {
            orderData = (OrderData) intent.getSerializableExtra("orderData1");
            System.out.println("Dữ liệu nhận được tại : OrderDetailsNeedDelivereActivity From CusAdapterHomeActivity " + orderData);
        }
        vpOrderDetailsNeedAdapter = new VPOrderDetailsNeedAdapter(imgArrayListURL, context);
        viewPagerList_NeedDetail.setAdapter(vpOrderDetailsNeedAdapter);
        getImageProduct(orderData.getIdProduct_Order());
        getDataProduct();


        setEvent();
    }

    private void getDataProduct() {

        databaseReference = firebaseDatabase.getReference("Product/" + idProduct);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productData = snapshot.getValue(ProductData.class);
                if (productData != null) {
                    idProduct = productData.getIdProduct();
                    Log.i("TAG", "idProduct from Firebase: " + idProduct);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getImageProduct(String idProduct) {

        databaseReference = firebaseDatabase.getReference("ImageProducts");
        Query query = databaseReference.orderByChild("idProduct").equalTo(productData.getIdProduct());
        //System.out.println("hxcf"+idProduct);
        Log.i("TAG", "idProduct90909: " + idProduct);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot imageItem :
                            snapshot.getChildren()) {
                        Image image = imageItem.getValue(Image.class);
                        imgArrayListURL.add(image.getUrlImage());
                        Log.d("ImageURL", image.getUrlImage());
                        System.out.println("stss" + image.getUrlImage());

                        vpOrderDetailsNeedAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setControl() {
        btnNhanDuocHangCT = findViewById(R.id.btnNhanDonHangCT);
        viewPagerList_NeedDetail = findViewById(R.id.viewPagerImageProduct_DetailProduct);
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


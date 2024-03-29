package com.example.duangiaohang.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duangiaohang.Models.Image;
import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.Models.ShopData;
import com.example.duangiaohang.OrderDetailsNeedDelivereActivity;
import com.example.duangiaohang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Objects;


public class CustomAdapterHomeShipper extends RecyclerView.Adapter<HomeShipperViewHolder> {

    ArrayList<OrderData> orderDataArrayList = new ArrayList<>();
    Context context;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public CustomAdapterHomeShipper(ArrayList<OrderData> orderDataArrayList, Context context) {
        this.orderDataArrayList = orderDataArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public HomeShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeShipperViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mh_trangchu_shipper, parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeShipperViewHolder holder, int position) {
        OrderData orderData = orderDataArrayList.get(position);
        if (orderData != null) {
            holder.tvMaDonHangTC.setText("Mã Đơn: "+orderData.getIdOrder());
            getShopAddress(orderData.getIdShop_Order(), holder);
            getImageProduct(orderData.getIdProduct_Order(), holder);
            final int finalPosition = position;
            holder.card_Item_oder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderDetailsNeedDelivereActivity.class);
                    OrderData orderData1 = orderDataArrayList.get(finalPosition);
                    intent.putExtra("orderData1", orderData1);
                    System.out.println("Dữ liệu truyền đi tại OrderItem: " + orderData1);
                    context.startActivity(intent);
                }
            });
        }
    }
    private void getShopAddress(String idShop, HomeShipperViewHolder holder) {
        databaseReference = firebaseDatabase.getReference("Shop/" + idShop);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ShopData shopData = snapshot.getValue(ShopData.class);
                    if (shopData != null) {
                        Log.d("ShopData", "ShopAddress: " + shopData.getShopAddress());
                        holder.tvDiaChiNhanHangTC.setText("địa chỉ: "+shopData.getShopAddress());
                    }
                }else {
                    System.out.println("không tìm lấy dữ liệu shop ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error getting shop data: " + error.getMessage());
            }
        });
    }

    private void getImageProduct(String idProduct, HomeShipperViewHolder holder) {
        databaseReference = firebaseDatabase.getReference("ImageProducts/"+idProduct);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        if (snapshot1.exists()){
                            for (DataSnapshot dataImage: snapshot1.getChildren()){
                                String image = Objects.requireNonNull(dataImage.getValue()).toString();
                                System.out.println("đường linh ảnh sản phẩm: " + image.toString());
                                Picasso.get().load(image).placeholder(R.drawable.ic_downloading_24).into(holder.imgOtherProductItem);
                                return;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        @Override
        public int getItemCount () {
            return orderDataArrayList.size();
        }
    }


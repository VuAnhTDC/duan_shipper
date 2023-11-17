package com.example.duangiaohang.RecyclerView;

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


public class CustomAdapterHomeShipper extends RecyclerView.Adapter<HomeShipperViewHolder> {

    ImageView imgOtherProductItem;
    ArrayList<OrderData> orderDataArrayList = new ArrayList<>();
    Context context;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final boolean loadingData = false;
    TextView tvDiaChiGiaoHangTC;


    public CustomAdapterHomeShipper(ArrayList<OrderData> orderDataArrayList, Context context) {
        this.orderDataArrayList = orderDataArrayList;
        this.context = context;
        Log.d("AdapterData", "Data List: " + orderDataArrayList.toString());
//          getShopAddress("idShop");
//        getImageProduct("idShop");
    }


    @NonNull
    @Override
    public HomeShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeShipperViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mh_trangchu_shipper, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeShipperViewHolder holder, int position) {
        System.out.println("sixe" + orderDataArrayList.size());

        OrderData orderData = orderDataArrayList.get(position);
        if (orderData != null) {
            Log.e("tag", "loi" + position);

            holder.tvMaDonHangTC.setText(orderData.getIdOrder());
            holder.tvDiaChiNhanHangTC.setText(orderData.getDeliveryAddress());
            holder.tvGiaTC.setText(orderData.getPrice_Order() + "VND");
            getShopAddress(orderData.getIdShop_Order(), holder);
            getImageProduct(orderData.getIdProduct_Order(), holder);


            //Chuyển sang màn hình chi tiết đơn hàng
            final int finalPosition = position;
            holder.linearLayout_ItemOrderList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderDetailsNeedDelivereActivity.class);
                    /////////Truyền dữ liệu qua màn hình order detail///////////
                    OrderData orderData1 = orderDataArrayList.get(finalPosition);
                    intent.putExtra("orderData1", orderData1);
                    System.out.println("Dữ liệu truyền đi tại OrderItem: " + orderData1);
                    ////////////////////////////////////////////////////////////
                    context.startActivity(intent);
                }

            });
        }
    }


    private void getShopAddress(String idShop, HomeShipperViewHolder holder) {
        databaseReference = firebaseDatabase.getReference("Shop/" + idShop);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ShopData shopData = snapshot.getValue(ShopData.class);

                    if (shopData != null) {
                        // Thêm Log để kiểm tra dữ liệu của shopData
                        Log.d("ShopData", "ShopAddress: " + shopData.getShopAddress());

                        // Cập nhật TextView

                        holder.tvDiaChiGiaoHangTC.setText(shopData.getShopAddress());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
                Log.e("FirebaseError", "Error getting shop data: " + error.getMessage());
            }
        });
    }


    private void getImageProduct(String idProduct, HomeShipperViewHolder holder) {
        databaseReference = firebaseDatabase.getReference("ImageProducts");
        Log.i("TAG", "HELO" + idProduct);
        Query query = databaseReference.orderByChild("idProduct" ).equalTo(idProduct);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("TAG","MESS"+snapshot);

                // Kiểm tra xem có dữ liệu và có các nút con không
                if (snapshot.exists() && snapshot.hasChildren()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        for (DataSnapshot imageItem : snapshot1.getChildren()) {
                            Image image = imageItem.getValue(Image.class);

                            Picasso.get().load(image.getUrlImage()).placeholder(R.drawable.ic_launcher_background).into(holder.imgOtherProductItem);
                            return;
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


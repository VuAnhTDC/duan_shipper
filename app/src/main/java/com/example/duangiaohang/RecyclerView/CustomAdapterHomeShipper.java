package com.example.duangiaohang.RecyclerView;

import android.content.Context;
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
    ArrayList<OrderData> orderDataArrayList;
    Context context;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private boolean loadingData = false;
     TextView tvDiaChiGiaoHangTC;


    public CustomAdapterHomeShipper(ArrayList<OrderData> orderDataArrayList, Context context) {
        this.orderDataArrayList = orderDataArrayList;
        this.context = context;
        getShopAddress("idShop");
        getImageProduct("idShop");
    }



    @NonNull
    @Override
    public HomeShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mh_trangchu_shipper, parent, false);
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeShipperViewHolder holder, int position) {


        OrderData orderData = orderDataArrayList.get(position);
        if (orderData == null) {
            Log.e("tag", "loi" + position);
            return;

        }
        holder.tvMaDonHangTC.setText(orderData.getIdOrder());
        holder.tvDiaChiNhanHangTC.setText(orderData.getDeliveryAddress());
        holder.tvGiaTC.setText(orderData.getPrice_Order());


    }


    private void getShopAddress(String idShop) {
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

                        tvDiaChiGiaoHangTC.setText(shopData.getShopAddress());
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


    private void getImageProduct(String idProduct) {
        databaseReference = firebaseDatabase.getReference("ImageProducts");
        Query query = databaseReference.orderByChild("idProduct").equalTo(idProduct);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot imageItem : snapshot.getChildren()) {
                        Image image = imageItem.getValue(Image.class);

                        Picasso.get().load(image.getUrlImage()).placeholder(R.drawable.ic_launcher_background).into(imgOtherProductItem);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

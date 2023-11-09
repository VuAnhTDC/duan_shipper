package com.example.duangiaohang.RecyclerView;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duangiaohang.MHNhanDonHang_ChuaGiaoFragment;
import com.example.duangiaohang.ManHinhNhanHangActivity;
import com.example.duangiaohang.Models.Image;
import com.example.duangiaohang.Models.ListItemUserTC;
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

public class MHTrangChuShipperAdapter extends RecyclerView.Adapter<MHTrangChuShipperViewHolder> {


    private ArrayList<ListItemUserTC> itemUserTCArrayList;

    public MHTrangChuShipperAdapter(ArrayList<ListItemUserTC> itemUserTCArrayList) {
        this.itemUserTCArrayList = itemUserTCArrayList;
    }

    @NonNull
    @Override
    public MHTrangChuShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArrayList<Image> arrImage = new ArrayList<>();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mh_trangchu_shipper,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MHTrangChuShipperViewHolder holder, int position) {
     ListItemUserTC listItemUserTC = itemUserTCArrayList.get(position);
        if (listItemUserTC == null) {
            Log.e("Adapter", "listItemUserTC is null at position " + position);
            return;
     }
        holder.tvMaDonHangTC.setText(listItemUserTC.getMaDonHangTC());
       holder.tvDiaChiNhanHangTC.setText(listItemUserTC.getDiaChiGiaoHangTC());
       holder.tvDiaChiGiaoHangTC.setText(listItemUserTC.getDiaChiNhanHangTC());
       holder.tvGiaTC.setText(listItemUserTC.getGiaCaTC());
    }

    @Override
    public int getItemCount() {
        if (itemUserTCArrayList!= null){
            return  itemUserTCArrayList.size();
        }
        return 0;
    }

}


//
//    public MHTrangChuShipperAdapter (ArrayList<OrderData> ,Context context){
//        ArrayList<Image> arrImage = new ArrayList<>();
//        holder.tvMaDonHangTC.setText(orderData.getIdOrder());
//        holder.tvDiaChiNhanHangTC.setText(orderData.getDeliveryAddress());
//        String idShop = orderData.getIdShop_Order();
//        // Doc ShopData tu Firebase voi idShop
//        databaseReference = firebaseDatabase.getReference("Shop/"+idShop);
////        Query queryShopData = databaseReference.orderByChild(idShop);
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    ShopData shopData = snapshot.getValue(ShopData.class);
//                    if (shopData != null) {
//                        String diaChiCuaHang = shopData.getShopAddress();
//                        Log.d(TAG,"jdfvgh"+diaChiCuaHang.toString());
//                        holder.tvDiaChiGiaoHangTC.setText("Địa chỉ cửa hàng: " + diaChiCuaHang);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
//        holder.tvGiaTC.setText(orderData.getPrice_Order() + "VND");
//        databaseReference = firebaseDatabase.getReference("ImageProducts");
//        Query query = databaseReference.orderByChild("idProduct").equalTo(orderData.getIdOrder());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot imageItem : snapshot.getChildren()) {
//                        Image image = imageItem.getValue(Image.class);
//                        Picasso.get().load(image.getUrlImage()).placeholder(R.drawable.ic_launcher_background).into(holder.imgOtherProductItem);
//                        return;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//
//        });
//    }
//
//    //Hàm Lấy Sản Phẩm Theo Nhà SX
//    private void LaySanPhamTheoMaSX(MHTrangChuShipperViewHolder holder, OrderData orderData) {
//        databaseReference = firebaseDatabase.getReference("OrderPrduct");
//        Query query = databaseReference.orderByChild("idOrder").equalTo(orderData.getIdOrder());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                orderDatas.clear();
//                if (snapshot.exists()) {
//                    for (DataSnapshot ortherSnapshot : snapshot.getChildren()) {
//                        OrderData orderData = ortherSnapshot.getValue(OrderData.class);
//                        if (orderDatas.size() > 0) {
//                            orderDatas.add(orderData);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//
//            //    hàm xóa sản phẩm dựa vào id product
//            private void deleteProduct(String idProduct, MHTrangChuShipperViewHolder holder) {
//                databaseReference = firebaseDatabase.getReference("Product");
//                databaseReference.child(idProduct).removeValue();
//            }
//
//
//        });
//    }
//
//
//    @NonNull
//    @Override
//    public MHTrangChuShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MHTrangChuShipperViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
//

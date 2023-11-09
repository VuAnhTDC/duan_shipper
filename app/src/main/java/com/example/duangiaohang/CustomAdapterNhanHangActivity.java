package com.example.duangiaohang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.Models.Image;
import com.example.duangiaohang.Models.OrderData;
import com.example.duangiaohang.Models.ShipperData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapterNhanHangActivity extends RecyclerView.Adapter<CustomAdapterNhanHangActivity.UserViewHolder> {

    private List<ShipperData> mListShipper;

    public CustomAdapterNhanHangActivity(List<ShipperData> mListShipper) {
        this.mListShipper = mListShipper;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_shipper, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ShipperData shipperData = mListShipper.get(position);
        if (shipperData != null) {
            if(shipperData.getUrlImgShopAvatar().isEmpty()){
                holder.imgUser.setImageResource(Integer.parseInt(shipperData.getUrlImgShopAvatar()));
            }
            else {
                Picasso.get().load(shipperData.getUrlImgShopAvatar()).into(holder.imgUser);

            }
            holder.tvNameShipper.setText(shipperData.getHoTenShipper());
            holder.tvDiaChiShipper.setText(shipperData.getDiaChiShipper());

        }


    }

    @Override
    public int getItemCount() {
        if (mListShipper != null) {
            return mListShipper.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgUser;
        private final TextView tvNameShipper;
        private final TextView tvDiaChiShipper;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_User);
            tvNameShipper = itemView.findViewById(R.id.tvNameShipper);
            tvDiaChiShipper = itemView.findViewById(R.id.tvDiaChiShipper);

        }

    }


    public static class CustomManHinhTrangChuShipperAdapter extends RecyclerView.Adapter<MHNhanDonHang_ChuaGiaoFragment.ManHinhTrangChuShipperViewHolder> {

        ArrayList<OrderData> arrayListProduct = new ArrayList<>();
        Context context;

        public CustomManHinhTrangChuShipperAdapter(ArrayList<OrderData> arrayListProduct, Context context) {
          this.arrayListProduct = arrayListProduct;
            this.context = context;
        }

        @NonNull
        @Override
        public MHNhanDonHang_ChuaGiaoFragment.ManHinhTrangChuShipperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MHNhanDonHang_ChuaGiaoFragment.ManHinhTrangChuShipperViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mh_trangchu_shipper,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MHNhanDonHang_ChuaGiaoFragment.ManHinhTrangChuShipperViewHolder holder, int position) {
            OrderData othertData = arrayListProduct.get(position);
            holder.tvItemMaDonHangTC_RecyclerView.setText(othertData.getIdCustomer_Order());
            holder.tvItemDiaChiGiaoHangTC_RecyclerView.setText(othertData.getDeliveryAddress());
            holder.tvItemGiaCaTC_RecyclerView.setText(othertData.getPrice_Order() + "$");
            holder.tvItemDiaChiNhanHangTC_RecyclerView.setText(othertData.getDeliveryAddress());
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Shop").child(othertData.getIdOrder());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        ShipperData shipperData = snapshot.getValue(ShipperData.class);
                        holder.tvItemDiaChiGiaoHangTC_RecyclerView.setText(shipperData.getDiaChiShipper());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            isLoadingImageProductItem(othertData,holder);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ManHinhNhanHangActivity.class);
                    intent.putExtra("idProduct",othertData.getIdOrder());
                    context.startActivity(intent);
                }
            });
        }
        private void isLoadingImageProductItem(OrderData orderDataItem, MHNhanDonHang_ChuaGiaoFragment.ManHinhTrangChuShipperViewHolder holder){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("ImageProducts");
            Query query = databaseReference.orderByChild("idProduct").equalTo(orderDataItem.getIdShop_Order());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot imageItem:
                                snapshot.getChildren()) {
                            Image image = imageItem.getValue(Image.class);
                            Picasso.get().load(image.getUrlImage()).into(holder.iviitemImageSPTC);
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
            return arrayListProduct.size();
        }
    }
}

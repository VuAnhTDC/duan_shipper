package com.example.duangiaohang.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.R;

public class HomeShipperViewHolder extends RecyclerView.ViewHolder {
    ImageView imgOtherProductItem;
    CardView card_Item_oder;
//    Button btn_receive_application;
    TextView tvMaDonHangTC, tvDiaChiNhanHangTC;

    public HomeShipperViewHolder(@NonNull View itemView) {
        super(itemView);
        tvMaDonHangTC = itemView.findViewById(R.id.tvItemMaDonHang);
        tvDiaChiNhanHangTC = itemView.findViewById(R.id.tvItemDiaChiNhanHangTC);
        imgOtherProductItem = itemView.findViewById(R.id.ivItemImageSPTC);
        card_Item_oder = itemView.findViewById(R.id.card_order);
//        btn_receive_application = itemView.findViewById(R.id.btn_receive_application);
    }
}

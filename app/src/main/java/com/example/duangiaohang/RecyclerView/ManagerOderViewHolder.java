package com.example.duangiaohang.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.R;

class ManagerOderViewHolder extends RecyclerView.ViewHolder{
    ImageView imgOtherProductItem;
    CardView card_Item_oder;
    TextView tvMaDonHangTC, tvDiaChiNhanHangTC;
    public ManagerOderViewHolder(@NonNull View itemView) {
        super(itemView);
        tvMaDonHangTC = itemView.findViewById(R.id.tv_codeorders_ManagerOrder);
        tvDiaChiNhanHangTC = itemView.findViewById(R.id.tv_address_ManagerOrder);
        imgOtherProductItem = itemView.findViewById(R.id.img_product_managerOrder);
        card_Item_oder = itemView.findViewById(R.id.card_managerOder);
    }
}

package com.example.duangiaohang.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.R;

public class NotDeliveryViewHolder extends RecyclerView.ViewHolder {
    ImageView imgOtherProductItem;
    CardView card_Item_oder;
    TextView tvHoanThanhDon,tvMaDon;
    public NotDeliveryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvMaDon = itemView.findViewById(R.id.tv_codeorders_Not_Delivery_Oder);
        tvHoanThanhDon = itemView.findViewById(R.id.tv_Not_Delivery_Oder);
        imgOtherProductItem = itemView.findViewById(R.id.img_product_Not_Delivery_Oder);
        card_Item_oder = itemView.findViewById(R.id.card_Not_Delivery_Oder);
    }
}

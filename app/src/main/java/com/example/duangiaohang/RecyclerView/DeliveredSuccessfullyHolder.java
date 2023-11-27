package com.example.duangiaohang.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.R;

public class DeliveredSuccessfullyHolder extends RecyclerView.ViewHolder {
    ImageView imgOtherProductItem;
    CardView card_Item_oder;
    TextView tvHoanThanhDon,tvMaDon;
    public DeliveredSuccessfullyHolder(@NonNull View itemView) {
        super(itemView);
        tvMaDon = itemView.findViewById(R.id.tv_codeorders_delivered_successfullyOder);
        tvHoanThanhDon = itemView.findViewById(R.id.tv_delivered_successfullyOder);
        imgOtherProductItem = itemView.findViewById(R.id.img_product_delivered_successfullyOder);
        card_Item_oder = itemView.findViewById(R.id.card_delivered_successfullyOder);
    }
}

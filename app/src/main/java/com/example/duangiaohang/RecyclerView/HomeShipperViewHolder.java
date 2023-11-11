package com.example.duangiaohang.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.R;

public class HomeShipperViewHolder extends RecyclerView.ViewHolder {
    ImageView imgOtherProductItem;

    TextView tvMaDonHangTC, tvDiaChiNhanHangTC, tvDiaChiGiaoHangTC, tvGiaTC;

    public HomeShipperViewHolder(@NonNull View itemView) {
        super(itemView);
        tvMaDonHangTC = itemView.findViewById(R.id.tvItemMaDonHang);
        tvGiaTC = itemView.findViewById(R.id.tvItemGiaCaTC);
        tvDiaChiGiaoHangTC = itemView.findViewById(R.id.tvItemDiaChiGiaoHangTC);
        tvDiaChiNhanHangTC = itemView.findViewById(R.id.tvItemDiaChiNhanHangTC);
        imgOtherProductItem = itemView.findViewById(R.id.ivItemImageSPTC);
    }
}

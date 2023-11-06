package com.example.duangiaohang;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duangiaohang.Models.ShipperData;
import com.squareup.picasso.Picasso;

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


}
